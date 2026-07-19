package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigBC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaBC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.type.option.Length;

import java.util.*;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FAggregateModuleFractalDimensionBCDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionBCDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    protected FPlot getResultsOptimized(FConfigBC config, FMetaBC meta) {
        long millis = System.currentTimeMillis();

        ProcessOptimized process = new ProcessOptimized(this.factory, this.aggregate, config);

        FPlot results = process.process();

        if (meta != null) {
            meta.setExecutionTimeMillis(System.currentTimeMillis() - millis);
            meta.setRefData(results);
        }

        return results;
    }

    protected FPlot getResultsNaive(FMetaBC meta) {
        long millis = System.currentTimeMillis();

        ProcessRaw process = new ProcessRaw(this.factory, this.aggregate);

        FPlot results = process.process();

        if (meta != null) {
            meta.setExecutionTimeMillis(System.currentTimeMillis() - millis);
            meta.setRefData(results);
        }

        return results;
    }

    protected double analyze(FPlot results, FConfigBC config, FMetaBC meta) {
        double windowRatio = config.getWindowRatio();

        if (windowRatio <= 0 || windowRatio > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        results.mutateY((x, y) -> Math.log(y));
        results.mutateX((x, y) -> Math.log(x));

        results.filter((x, y) -> y > 0);

        FPoly regression = windowRatio == 1 ?
                results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * windowRatio));

        FPlot approximation = results.copy();
        approximation.setY(regression);

        double dimension = -regression.at(1);

        if (meta != null) {
            meta.setPythonRenderScript(plot(results, approximation, regression, dimension));
        }

        return dimension;
    }

    private String plot(FPlot results, FPlot approximation, FPoly regression, double dimension) {
        double r2 = results.r2(regression);

        String dimFormat = String.format(Locale.US, "%.2f", dimension);
        String r2Format = String.format(Locale.US, "%.4f", r2);

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setAnnotation("R<sup>2</sup> ≈ " + r2Format)
                .setFontSize(48)
                .setGridSize(3)
                .setNameX("ln δ")
                .setNameY("ln N<sub>δ</sub>");

        FPlotMeta metaPlotFit = factory.getFPlotMeta()
                .setLinesColor("black")
                .setLinesWidth(6)
                .setMarkersSize(21)
                .setLinesShow(true)
                .setMarkersShow(false);

        FPlotMeta metaPlotResults = factory.getFPlotMeta()
                .setMarkersColor("black")
                .setLinesWidth(6)
                .setMarkersSize(21)
                .setLinesShow(false)
                .setMarkersShow(true);

        approximation.setName("Linear fit (D<sub>BC</sub> ≈ " + dimFormat + ")")
                .setRefMeta(metaPlotFit);

        results.setName("Raw box counts")
                .setRefMeta(metaPlotResults);

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, approximation, results);
    }

    // -------------------------------------------------------------------------------------------------

    static class ProcessOptimized {
        private final ScatterFactory factory;

        private final FConfigBC config;

        private final FPlot results;

        private final FAggregate reference;
        private final FAggregate replica;

        private List<FPos3D> shifts;

        private ProcessOptimized(ScatterFactory factory, FAggregate aggregate, FConfigBC config) {
            this.factory = factory;

            this.config = config;

            this.results = factory.getFPlot();

            this.reference = aggregate.copy(false);
            this.replica = aggregate.copy(false);
        }

        FPlot process() {

            validate();
            run();

            return this.results;
        }

        private void validate() {

            if (this.config.getScalingFactor() <= 1) {
                throw new IllegalArgumentException("The scaling factor value must be greater then one");
            }

            if (this.config.getShiftsPerAxis() < 1) {
                throw new IllegalArgumentException("The shift value must be greater then zero");
            }
        }

        private void run() {

            if (this.config.isAlignedPCA()) {
                this.reference.pca();
            }

            if (this.config.isAlignedOrigin()) {
                this.reference.shiftBoundaryToZero();
            }

            this.shifts = getShifts(this.config.getShiftsPerAxis());

            double radius = this.reference.getFStatParticleRadius().mean();
            double scaleFactor = 1 / this.config.getScalingFactor();

            double cutoffInner = radius * 2;
            double cutoffOuter = this.reference.getLength(Length.MAX);

            double boxLength = cutoffOuter * scaleFactor;

            while (boxLength > cutoffInner) {
                step(boxLength);

                boxLength *= scaleFactor;
            }
        }

        private void step(double boxLength) {
            FSphereHelper sphereHelper = this.factory.getFSphereHelper();

            int countMin = Integer.MAX_VALUE;

            for (FPos3D shift : this.shifts) {
                prepareReplica(boxLength, shift);

                Queue<Shape> particles = new LinkedList<>(this.replica.getRefParticles().asList());
                List<Shape> neighbours = new ArrayList<>(this.replica.size());

                int sum = 0;
                while (!particles.isEmpty()) {
                    Shape particle = particles.poll();

                    prepareNeighbours(neighbours, particles, particle);

                    int minX = (int) Math.floor(particle.getCenterX() - particle.getRadius() + EPSILON);
                    int minY = (int) Math.floor(particle.getCenterY() - particle.getRadius() + EPSILON);
                    int minZ = (int) Math.floor(particle.getCenterZ() - particle.getRadius() + EPSILON);

                    int maxX = (int) Math.ceil(particle.getCenterX() + particle.getRadius() - EPSILON);
                    int maxY = (int) Math.ceil(particle.getCenterY() + particle.getRadius() - EPSILON);
                    int maxZ = (int) Math.ceil(particle.getCenterZ() + particle.getRadius() - EPSILON);

                    for (int x = minX; x < maxX; x++) {
                        for (int y = minY; y < maxY; y++) {

                            next:
                            for (int z = minZ; z < maxZ; z++) {
                                if (sphereHelper.intersectsCube(particle, x, y, z, 1)) {
                                    for (Shape neighbour : neighbours) {
                                        if (sphereHelper.intersectsCube(neighbour, x, y, z, 1)) {
                                            continue next;
                                        }
                                    }

                                    sum++;
                                }
                            }
                        }
                    }
                }

                if (sum < countMin) {
                    countMin = sum;
                }
            }

            this.results.add(boxLength, countMin);
        }

        private void prepareReplica(double boxLength, FPos3D offset) {
            double boxLengthInverted = 1 / boxLength;

            for (int i = 0 ; i < this.reference.size() ; i++) {
                this.replica.getRefParticles().asList().get(i).setCenter(this.reference.getRefParticles().asList().get(i).getRefCenter());
                this.replica.getRefParticles().asList().get(i).setRadius(this.reference.getRefParticles().asList().get(i).getRadius());
            }

            this.replica.forEach((particle) -> {
                particle.scalePosition(boxLengthInverted).scaleSize(boxLengthInverted);
                particle.translate(offset);
            });
        }

        private void prepareNeighbours(List<Shape> neighbours, Queue<Shape> particles, Shape candidate) {
            neighbours.clear();

            particles.forEach(e -> {
                if (e.getDistCenterP2(candidate) < Math.pow(e.getRadius() + candidate.getRadius() + 2, 2)) {
                    neighbours.add(e);
                }
            });
        }

        private List<FPos3D> getShifts(int shift) {
            List<FPos3D> results = new ArrayList<>(shift * shift * shift);

            double offset = (double) 1 / shift;

            for (int i = 0 ; i < shift ; i++) {
                for (int j = 0 ; j < shift ; j++) {
                    for (int k = 0 ; k < shift ; k++) {
                        results.add(this.factory.getFPos3D(i * offset, j * offset, k * offset));
                    }
                }
            }

            return results;
        }
    }

    static class ProcessRaw {
        private final ScatterFactory factory;

        private final FPlot results;

        private final FAggregate reference;

        private ProcessRaw(ScatterFactory factory, FAggregate aggregate) {
            this.factory = factory;

            this.results = factory.getFPlot();

            this.reference = aggregate.copy(false);
        }

        FPlot process() {

            run();

            return this.results;
        }

        private void run() {
            double radius = reference.getFStatParticleRadius().mean();
            double scaleFactor = 0.5;

            double cutoffInner = radius * 2;
            double cutoffOuter = reference.getLength(Length.MAX);

            double boxLength = cutoffOuter * scaleFactor;

            while (boxLength > cutoffInner) {
                step(boxLength);

                boxLength = boxLength * scaleFactor;
            }
        }

        private void step(double boxLength) {
            FSphereHelper sphereHelper = this.factory.getFSphereHelper();
            FPairPos3D boundary = this.reference.getBoundary();

            double minX = boundary.getPosA().getD0();
            double minY = boundary.getPosA().getD1();
            double minZ = boundary.getPosA().getD2();

            double widthX = boundary.getPosB().getD0() - minX;
            double widthY = boundary.getPosB().getD1() - minY;
            double widthZ = boundary.getPosB().getD2() - minZ;

            int sizeX = (int) Math.ceil(widthX / boxLength);
            int sizeY = (int) Math.ceil(widthY / boxLength);
            int sizeZ = (int) Math.ceil(widthZ / boxLength);

            int sum = 0;
            for (int i = 0; i < sizeX; i++) {
                double x = minX + (i * boxLength);

                for (int j = 0; j < sizeY; j++) {
                    double y = minY + ( j * boxLength);

                    next:
                    for (int k = 0; k < sizeZ; k++) {
                        double z = minZ + (k * boxLength);

                        for (Shape particle : this.reference) {
                            if (sphereHelper.intersectsCube(particle, x, y, z, boxLength)) {
                                sum++;

                                continue next;
                            }
                        }
                    }
                }
            }

            this.results.add(boxLength, sum);
        }
    }
}
