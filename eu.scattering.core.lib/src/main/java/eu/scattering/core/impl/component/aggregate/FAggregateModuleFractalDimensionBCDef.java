package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.type.option.Length;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static eu.scattering.core.impl.ScatConfigDef.EPSILON;

public class FAggregateModuleFractalDimensionBCDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;
    private final FSphereHelper sphereHelper;

    protected FAggregateModuleFractalDimensionBCDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;

        this.sphereHelper = this.factory.getFSphereHelper();
    }

    protected FPlot getResultsOptimized(double step, int shift, boolean reposition, boolean pca) {

        if (step <= 1) {
            throw new IllegalArgumentException("The step factor value must be greater then one");
        }

        if (shift < 1) {
            throw new IllegalArgumentException("The shift value must be greater then zero");
        }

        FPlot results = this.factory.getFPlot();

        FAggregate reference = this.aggregate.copy(false);
        FAggregate replica = this.aggregate.copy(false);

        if (pca) {
            reference.pca();
        }

        if (reposition) {
            reference.shiftBoundaryToZero();
        }

        double radius = reference.getFStatParticleRadius().mean();
        double scaleFactor = 1 / step;

        List<FPos3D> shifts = getShifts(shift);

        double cutoffInner = radius * 2;
        double cutoffOuter = reference.getLength(Length.MAX);

        double box = cutoffOuter * scaleFactor;
        while (box >= cutoffInner) {
            stepOptimized(reference, replica, shifts, results, box);

            box *= scaleFactor;
        }

        return results;
    }

    protected FPlot getResultsBruteForce() {
        FPlot results = this.factory.getFPlot();

        FAggregate reference = this.aggregate.copy(false);

        double radius = reference.getFStatParticleRadius().mean();
        double scaleFactor = 0.5;

        double cutoffInner = radius * 2;
        double cutoffOuter = reference.getLength(Length.MAX);

        double box = cutoffOuter * scaleFactor;
        while (box >= cutoffInner) {
            stepBruteForce(reference, results, box);

            box = box * scaleFactor;
        }

        return results;
    }

    protected double analyze(FPlot results, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        results.mutateY((x, y) -> Math.log(y));
        results.mutateX((x, y) -> Math.log(x));

        results.filter((x, y) -> y > 0);

        FPoly regression = window == 1 ? results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * window));

        FPlot fit = results.copy();
        fit.setY(regression);

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setName("Box-Counting Dimension")
                .setNameX("$\\\\ln \\\\delta$")
                .setNameY("$\\\\ln N_{\\\\delta}$");

        FPlotMeta metaPlotFit = factory.getFPlotMeta().setLinesColor("lightgray").setLinesWidth(2).setLinesShow(true).setMarkersShow(false);
        FPlotMeta metaPlotResults = factory.getFPlotMeta().setMarkersColor("black").setMarkersSize(5).setLinesShow(false).setMarkersShow(true);

        fit.setName("Approximation").setRefMeta(metaPlotFit);
        results.setName("Measurements").setRefMeta(metaPlotResults);

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, fit, results);

        return -regression.at(1);
    }

    private void stepOptimized(FAggregate reference, FAggregate replica, List<FPos3D> shifts, FPlot results, double boxLength) {
        int countMin = Integer.MAX_VALUE;

        for (FPos3D shift : shifts) {
            prepareReplica(replica, reference, boxLength, shift);

            Queue<Shape> particles = new LinkedList<>(replica.getRefParticles().asList());
            List<Shape> neighbours = new ArrayList<>(replica.size());

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

        results.add(boxLength, countMin);
    }

    private void stepBruteForce(FAggregate reference, FPlot results, double boxLength) {
        FSphereHelper sphereHelper = this.factory.getFSphereHelper();
        FPairPos3D boundary = reference.getBoundary();

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

                    for (Shape particle : reference) {
                        if (sphereHelper.intersectsCube(particle, x, y, z, boxLength)) {
                            sum++;

                            continue next;
                        }
                    }
                }
            }
        }

        results.add(boxLength, sum);
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

    private void prepareReplica(FAggregate replica, FAggregate reference, double boxLength, FPos3D offset) {
        double boxLengthInverted = 1 / boxLength;

        for (int i = 0 ; i < reference.size() ; i++) {
            replica.getRefParticles().asList().get(i).setCenter(reference.getRefParticles().asList().get(i).getRefCenter());
            replica.getRefParticles().asList().get(i).setRadius(reference.getRefParticles().asList().get(i).getRadius());
        }

        replica.forEach((particle) -> {
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
}
