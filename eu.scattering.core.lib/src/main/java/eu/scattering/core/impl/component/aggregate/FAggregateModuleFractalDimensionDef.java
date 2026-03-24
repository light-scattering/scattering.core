package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
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

    protected FPlot getMassRadiusFunction(RadiusOfGyration type, double stepFactor, boolean rangeLimit) {
        FPoint massCenter = this.factory.getFPoint();

        List<Double> massFragments = new ArrayList<>(this.aggregate.size());
        List<FPos3D> centerFragments = new ArrayList<>(this.aggregate.size());

        double radiusOfGyration = this.aggregate.getRadiusOfGyration(type, massCenter, massFragments, centerFragments);

        double range = rangeLimit ? radiusOfGyration * 0.5 : this.aggregate.getRadiusFrom(massCenter) * 2.1;

        FPlot results = createMassRadiusData(stepFactor, range);

        int count = setMassRadiusData(results, massCenter.toFPos3D(), massFragments, centerFragments, range);

        results.mutateY(a -> a.mutate(b -> b / count));
        results.mutateX(a -> a.mutate(Math::sqrt));

        return results;
    }

    private FPlot createMassRadiusData(double stepFactor, double limit) {
        FPlot results = this.factory.getFPlot();

        FStat particles = this.aggregate.getFStatParticleRadius();

        double step = particles.min();
        while (step <= limit) {
            results.add(step * step, 0);
            step = step * stepFactor;
        }

        return results;
    }

    private int setMassRadiusData(FPlot results, FPos3D massCenter, List<Double> massFragments, List<FPos3D> centerFragments, double limit) {
        int count = 0;
        int size = this.aggregate.size();
        double limitP2 = limit * limit;

        FPointHelper helper = this.factory.getFPointHelper();

        for (int i = 0 ; i < size ; i++) {
            FPos3D cA = centerFragments.get(i);

            if (helper.getDistanceP2(massCenter, cA) > limitP2) {
                continue;
            }

            count ++;

            for (int j = 0 ; j < size ; j++) {
                FPos3D cB = centerFragments.get(j);

                double distP2 = helper.getDistanceP2(cA, cB);

                if (distP2 > limitP2) {
                    continue;
                }

                for (int k = 0 ; k < results.size() ; k++) {
                    if (distP2 <= results.getX(k)) {
                        results.setY(k, results.getY(k) + massFragments.get(j));

                        break;
                    }
                }
            }
        }

        for (int k = 1 ; k < results.size() ; k++) {
            results.setY(k, results.getY(k) + results.getY(k - 1));
        }

        return count;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getBoxCoverageFunction(double stepFactor, int offset, boolean start, boolean shift,  boolean pca) {

        if (stepFactor <= 1) {
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
        double scaleFactor = 1 / stepFactor;

        List<FPos3D> offsets = getOffsetValues(offset);

        //-----

//        double cutoffInner = radius * 2;
//        double cutoffOuter = this.aggregate.getLength(Length.MAX);
//
//        if (start) {
//            results.add(cutoffOuter, 1);
//        }
//
//        double box = cutoffOuter * scaleFactor;

        //-----

        double cutoffInner = radius * 2;
        double cutoffOuter = this.aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);

        double box = cutoffOuter;

        //-----

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

    protected FPlot getDensityCorrelationFunction(double stepFactor) {

        return getDensityCorrelationFunction(RadiusOfGyration.SIMPLE_POLY, stepFactor, false);
    }

    private FPlot getDensityCorrelationFunction(RadiusOfGyration method, double stepFactor, boolean rangeLimit) {
        FPlot results = this.factory.getFPlot();
        FPoint massCenter = this.factory.getFPoint();

        double radiusOfGyration = this.aggregate.getRadiusOfGyration(method, massCenter, null, null);

        double range = rangeLimit ? radiusOfGyration * 0.5 : this.aggregate.getRadiusFrom(massCenter) * 2.1;

        FStat distances = getCorePairDistance(massCenter.toFPos3D(), range);

        double min = distances.min();
        double start = min * stepFactor;
        double delta = min * 0.5;

        setDistance(results, distances, stepFactor, start, delta);
        setDensity(results, delta);

        results.filter((x, y) -> x > 0 && y > 0);

        return results;
    }

    private FStat getCorePairDistance(FPos3D center, double range) {
        FStat results = this.factory.getFStat();

        List<Shape> internal = new ArrayList<>();
        List<Shape> external = new ArrayList<>();

        splitByRange(internal, external, center, range);

        addInternalPairDistance(results, internal, range);
        addExternalPairDistance(results, internal, external, range);

        return results;
    }

    private void splitByRange(Collection<Shape> internal, Collection<Shape> external, FPos3D center, double range) {
        List<Shape> particles = this.aggregate.getRefParticles().asList();

        if (range <= 0) {
            external.addAll(particles);
            return;
        }

        double rangeP2 = range * range;
        double cutoffP2 = rangeP2 * rangeP2;

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

    protected double getFractalDimension(FractalDimension type) {

        return switch (type) {
            case BOX_FAST -> getBoxCountingAnalyze(
                    getBoxCoverageFunction(2, 1, true, true, false),
                    1
            );
            case BOX_FAST_BRUTE_FORCE -> getBoxCountingAnalyze(
                    getBoxCoverageFunctionBruteForce(),
                    1
            );
            case BOX_ADVANCED_1 -> getBoxCountingAnalyze(
                    getBoxCoverageFunction(1.3, 3, false, false, false),
                    0.9
            );
            case BOX_ADVANCED_2 -> getBoxCountingAnalyze(
                    getBoxCoverageFunction(1.3, 5, false, false, true),
                    0.9
            );
            case CORRELATION -> getDensityCorrelationAnalyze(
                    getDensityCorrelationFunction(RadiusOfGyration.SIMPLE_POLY, 1.1, true),
                    0.9
            );
            case CORRELATION_FULL -> getDensityCorrelationAnalyze(
                    getDensityCorrelationFunction(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9
            );
            case MASS -> getMassRadiusAnalyze(
                    getMassRadiusFunction(RadiusOfGyration.SIMPLE_POLY, 1.05, true),
                    0.8
            );
            case MASS_FULL -> getMassRadiusAnalyze(
                    getMassRadiusFunction(RadiusOfGyration.SIMPLE_POLY, 1.05, false),
                    0.8
            );
        };
    }

    protected double getFractalDimensionMassRadius(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return getMassRadiusAnalyze(getMassRadiusFunction(method, stepFactor, rangeLimit), window);
    }

    protected double getFractalDimensionBoxCounting(double window, double stepFactor, int offset, boolean start, boolean shift, boolean pca) {

        return getBoxCountingAnalyze(getBoxCoverageFunction(stepFactor, offset, start, shift, pca), window);
    }

    protected double getFractalDimensionDensityCorrelation(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return getDensityCorrelationAnalyze(getDensityCorrelationFunction(method, stepFactor, rangeLimit), window);
    }

    private double getMassRadiusAnalyze(FPlot data, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        for (int i = 1 ; i < data.size() ; i++) {
            if (data.getY(i) == data.getY(i - 1)) {
                data.setX(i - 1, Double.NaN);
            } else {

                break;
            }
        }

        for (int i = data.size() - 2 ; i >= 0 ; i--) {
            if (data.getY(i) == data.getY(i + 1)) {
                data.setX(i + 1 , Double.NaN);
            } else {

                break;
            }
        }

        data = data.removeNaN();

        data.mutateY((x, y) -> Math.log(y));
        data.mutateX((x, y) -> Math.log(x));

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

    private double getBoxCountingAnalyze(FPlot data, double window) {

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

    private double getDensityCorrelationAnalyze(FPlot data, double window) {

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
