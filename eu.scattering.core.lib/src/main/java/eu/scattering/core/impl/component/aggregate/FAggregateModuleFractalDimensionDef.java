package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.method.MassCenter;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.*;

import static eu.scattering.core.impl.ScatConfigDef.EPSILON;

public class FAggregateModuleFractalDimensionDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getBoxCoverageFunction(double factor, int offset, boolean start, boolean shift,  boolean pca) {

        if (factor <= 1) {
            throw new IllegalArgumentException("The factor value must be greater then one");
        }

        if (offset < 1) {
            throw new IllegalArgumentException("The offset value must be greater then zero");
        }

        FPlot results = this.factory.getFPlot();

        FAggregate reference = this.aggregate.copy(false);

        if (shift) {
            reference.shiftBoundaryToZero();
        }

        if (pca) {
            reference.pca();
        }

        FAggregate replica = this.aggregate.copy(false);

        double radius = this.aggregate.getFStatParticleRadius().mean();
        double scaleFactor = 1 / factor;

        List<FPos3D> offsets = getOffsetValues(offset);

        double cutoffInner = radius * 2;
        double cutoffOuter = this.aggregate.getLength(Length.MAX);

        if (start) {
            results.add(cutoffOuter, 1);
        }

        double box = cutoffOuter * scaleFactor;
        while (box >= cutoffInner) {
            getBoxCoverageFunctionStep(reference, replica, offsets, results, box);

            box = box * scaleFactor;
        }

        return results;
    }

    protected FPlot getBoxCoverageFunctionBruteForce() {
        FPlot results = this.factory.getFPlot();

        double radius = this.aggregate.getFStatParticleRadius().mean();
        double factor = 0.5;

        double cutoffInner = radius * 2;
        double cutoffOuter = this.aggregate.getLength(Length.MAX);

        double size = cutoffOuter * factor;
        while (size >= cutoffInner) {
            getBoxCoverageFunctionBruteForceStep(results, size);

            size = size * factor;
        }

        return results;
    }

    private void getBoxCoverageFunctionStep(FAggregate reference, FAggregate replica, List<FPos3D> offsets, FPlot results, double box) {
        FSphereHelper sphereHelper = this.factory.getFSphereHelper();
        int sumMin = Integer.MAX_VALUE;
        double scale = 1 / box;

        for (FPos3D offset : offsets) {

            for (int i = 0 ; i < this.aggregate.size() ; i++) {
                replica.getRefParticles().asList().get(i).setCenter(reference.getRefParticles().asList().get(i).getRefCenter());
                replica.getRefParticles().asList().get(i).setRadius(reference.getRefParticles().asList().get(i).getRadius());
            }

            Queue<Shape> particles = new LinkedList<>(replica.getRefParticles().asList());
            particles.forEach(e -> e.scalePosition(scale).scaleSize(scale));
            particles.forEach(e -> e.translate(offset));

            List<Shape> neighbours = new ArrayList<>(particles.size());

            int sum = 0;
            while (!particles.isEmpty()) {
                Shape particle = particles.poll();

                neighbours.clear();

                particles.forEach(e -> {
                    if (e.getDistCenterP2(particle) < Math.pow(e.getRadius() + particle.getRadius() + 2, 2)) {
                        neighbours.add(e);
                    }
                });

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

            if (sum < sumMin) {
                sumMin = sum;
            }
        }

        results.add(box, sumMin);
    }

    private void getBoxCoverageFunctionBruteForceStep(FPlot data, double step) {
        FSphereHelper sphereHelper = this.factory.getFSphereHelper();
        FPairPos3D boundary = this.aggregate.getBoundary();

        double minX = boundary.getPosA().getD0();
        double minY = boundary.getPosA().getD1();
        double minZ = boundary.getPosA().getD2();

        double widthX = boundary.getPosB().getD0() - minX;
        double widthY = boundary.getPosB().getD1() - minY;
        double widthZ = boundary.getPosB().getD2() - minZ;

        int sizeX = (int) Math.ceil(widthX / step);
        int sizeY = (int) Math.ceil(widthY / step);
        int sizeZ = (int) Math.ceil(widthZ / step);

        int sum = 0;
        for (int i = 0; i < sizeX; i++) {
            double x = minX + (i * step);

            for (int j = 0; j < sizeY; j++) {
                double y = minY + ( j * step);

                next:
                for (int k = 0; k < sizeZ; k++) {
                    double z = minZ + (k * step);

                    for (Shape particle : this.aggregate) {
                        if (sphereHelper.intersectsCube(particle, x, y, z, step)) {
                            sum++;

                            continue next;
                        }
                    }
                }
            }
        }

        data.add(step, sum);
    }

    private List<FPos3D> getOffsetValues(int count) {
        List<FPos3D> results = new ArrayList<>(count * count * count);

        double shift = (double) 1 / count;

        for (int i = 0 ; i < count ; i++) {
            for (int j = 0 ; j < count ; j++) {
                for (int k = 0 ; k < count ; k++) {
                    results.add(this.factory.getFPos3D(i * shift, j * shift, k * shift));
                }
            }
        }

        return results;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getDensityCorrelationFunction(double factor) {

        return getCoreDensityCorrelationFunction(factor, Double.POSITIVE_INFINITY);
    }

    private FPlot getCoreDensityCorrelationFunction(double factor, double exclusion) {
        FPlot results = this.factory.getFPlot();

        FStat distances = getCorePairDistance(exclusion);

        double min = distances.min();
        double delta = min * 0.5;

        setDistance(results, distances, factor, min, delta);
        setDensity(results, delta);

        results.filter((x, y) -> x > 0 && y > 0);

        return results;
    }

    private FStat getCorePairDistance(double exclusion) {
        FStat results = this.factory.getFStat();

        FPos3D center = this.aggregate.getMassCenter(MassCenter.SIMPLE_POLY);

        double cutoff = this.aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY);
        double range = cutoff * exclusion;

        List<Shape> internal = new ArrayList<>();
        List<Shape> external = new ArrayList<>();

        splitByRange(internal, external, center, range, cutoff);

        addInternalPairDistance(results, internal, range);
        addExternalPairDistance(results, internal, external, range);

        return results;
    }

    private void splitByRange(Collection<Shape> internal, Collection<Shape> external, FPos3D center, double range, double cutoff) {
        List<Shape> particles = this.aggregate.getRefParticles().asList();

        if (range <= 0) {
            external.addAll(particles);
            return;
        }

        double rangeP2 = range * range;
        double cutoffP2 = cutoff * cutoff;

        for (Shape particle : particles) {
            double distance = particle.getDistCenterP2(center);

            if (distance < rangeP2) {
                internal.add(particle);
            } else if (distance < cutoffP2) {
                external.add(particle);
            }
        }
    }

    private void addInternalPairDistance(FStat distances, List<Shape> internal, double range) {

        for (int i = 0 ; i < internal.size() - 1 ; i++) {
            for (int j = i + 1 ; j < internal.size() ; j++) {
                double distance = internal.get(i).getDistCenter(internal.get(j));

                if (distance <= range) {
                    distances.add(distance);
                    distances.add(distance);
                }
            }
        }
    }

    private void addExternalPairDistance(FStat distances, List<Shape> internal, List<Shape> external, double range) {

        for (Shape inner : internal) {
            for (Shape outer : external) {
                double distance = inner.getDistCenter(outer);

                if (distance <= range) {
                    distances.add(distance);
                }
            }
        }
    }

    private void setDistance(FPlot results, FStat distances, double factor, double start, double delta) {
        double max = distances.max() - delta;

        double step = start;
        while (step <= max) {
            results.add(step, 0);
            step = step * factor;
        }

        for (double distance : distances) {
            for (int i = 0 ; i < results.size() ; i++) {

                if (Math.abs(distance - results.getX(i)) < delta) {
                    results.setY(i, results.getY(i) + 1);
                }

                if (distance + delta < results.getX(i)) {
                    break;
                }
            }
        }
    }

    private void setDensity(FPlot results, double delta) {
        FSphereHelper helper = this.factory.getFSphereHelper();

        for (int i = 0 ; i < results.size() ; i++) {
            double element = results.getX(i);
            double volume = helper.getVolumeRing(element - delta, element + delta);

            results.setY(i, results.getY(i) / volume);
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected double getFractalDimensionBox(double window, double scale, int offset, boolean start, boolean shift,  boolean pca) {

        return getBoxCoverageAnalyze(getBoxCoverageFunction(scale, offset, start, shift, pca), window);
    }

    protected double getFractalDimensionCorrelation(double window, double factor) {

        return getCoreDensityCorrelationAnalyze(getCoreDensityCorrelationFunction(factor, 0.5), window);
    }

    protected double getFractalDimension(FractalDimension type) {

        return switch (type) {
            case BOX_FAST -> getBoxCoverageAnalyze(
                    getBoxCoverageFunction(2, 1, true, true, false),
                    1
            );
            case BOX_FAST_BRUTE_FORCE -> getBoxCoverageAnalyze(
                    getBoxCoverageFunctionBruteForce(),
                    1
            );
            case BOX_ADVANCED_1 -> getBoxCoverageAnalyze(
                    getBoxCoverageFunction(1.3, 3, false, false, true),
                    0.9
            );
            case BOX_ADVANCED_2 -> getBoxCoverageAnalyze(
                    getBoxCoverageFunction(1.3, 5, false, false, true),
                    0.9
            );
            case CORRELATION -> getCoreDensityCorrelationAnalyze(
                    getCoreDensityCorrelationFunction(1.1, 0.5),
                    0.9
            );
        };
    }

    private double getBoxCoverageAnalyze(FPlot data, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        data.mutateY((x, y) -> Math.log(y));
        data.mutateX((x, y) -> Math.log(1 / x));

        data.filter((x, y) -> y > 0);

        FPoly regression = window == 1 ? data.reg().poly(1) : data.reg().fitSlope((int) (data.size() * window));

        FPlot fit = data.copy();
        fit.setY(regression);

        FPlotMeta plotConfig = factory.getFPlotMeta()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, data, fit);

        return regression.at(1);
    }

    private double getCoreDensityCorrelationAnalyze(FPlot data, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        data.mutate((a, b) -> {
            a.ln();
            b.ln();
        });

        FPoly regression = window == 1 ? data.reg().poly(1) : data.reg().fitSlope((int) (data.size() * window));

        FPlot fit = data.copy();
        fit.setY(regression);

        FPlotMeta plotConfig = factory.getFPlotMeta()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, data, fit);

        return 3 + regression.at(1);
    }
}
