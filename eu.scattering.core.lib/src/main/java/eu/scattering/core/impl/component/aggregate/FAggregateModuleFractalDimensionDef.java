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

    protected FPlot getBoxCoverageFunction(boolean log) {
        FPlot results = this.factory.getFPlot();

        double radius = this.aggregate.getFStatParticleRadius().mean();

        double cutoffInner = radius * 2;
        double cutoffOuter = this.aggregate.getLength(Length.MAX);

        results.add(cutoffOuter, 1);

        double size = log ? cutoffOuter * 0.5 : cutoffOuter - radius;
        while (size >= cutoffInner) {
            getBoxCoverageStep(results, size);
            size = log ? size * 0.5 : size - radius;
        }

        return results;
    }

    private void getBoxCoverageStep(FPlot data, double step) {
        FSphereHelper helper = this.factory.getFSphereHelper();

        FPos3D origin = this.aggregate.getBoundary().getPosA();
        double scale = 1 / step;

        Queue<Shape> particles = new LinkedList<>(this.aggregate.getRefParticles().copy().asList());
        particles.forEach(e -> e.translate(-origin.getD0(), -origin.getD1(), -origin.getD2()));
        particles.forEach(e -> e.scalePosition(scale).scaleSize(scale));

        int sum = 0;
        while (particles.size() > 0) {
            Shape particle = particles.poll();

            List<Shape> neighbours = new ArrayList<>(particles.size());

            particles.forEach(e -> {
                if (e.getDistCenterP2(particle) < Math.pow(e.getRadius() + particle.getRadius() + 2, 2)) {
                    neighbours.add(e);
                }
            });

            double coreMinX = particle.getCenterX() - particle.getRadius() + EPSILON;
            int minX = (int) Math.floor(coreMinX);
            double coreMinY = particle.getCenterY() - particle.getRadius() + EPSILON;
            int minY = (int) Math.floor(coreMinY);
            double coreMinZ = particle.getCenterZ() - particle.getRadius() + EPSILON;
            int minZ = (int) Math.floor(coreMinZ);

            double coreMaxX = particle.getCenterX() + particle.getRadius() - EPSILON;
            int maxX = (int) Math.ceil(coreMaxX);
            double coreMaxY = particle.getCenterY() + particle.getRadius() - EPSILON;
            int maxY = (int) Math.ceil(coreMaxY);
            double coreMaxZ = particle.getCenterZ() + particle.getRadius() - EPSILON;
            int maxZ = (int) Math.ceil(coreMaxZ);

            for (int x = minX ; x < maxX ; x++) {
                for (int y = minY ; y < maxY ; y++) {

                    next:
                    for (int z = minZ ; z < maxZ ; z++) {
                        if (helper.intersectsCube(particle, x + 0.5, y + 0.5, z + 0.5, 1)) {
                            for (Shape neighbour : neighbours) {
                                if (helper.intersectsCube(neighbour, x + 0.5, y + 0.5, z + 0.5, 1)) {
                                    continue next;
                                }
                            }

                            sum ++;
                        }
                    }
                }
            }
        }

        data.add(step, sum);
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getDensityCorrelationFunction(boolean log) {

        return getCoreDensityCorrelationFunction(Double.POSITIVE_INFINITY, log);
    }

    private FPlot getCoreDensityCorrelationFunction(double rangeFactor, boolean log) {
        FPlot results = this.factory.getFPlot();

        FStat distances = getCorePairDistance(rangeFactor);

        double min = distances.min();
        double delta = min * 0.5;

        setDistance(results, distances, min, delta, log);
        setDensity(results, delta);

        results.filter((x, y) -> x > 0 && y > 0);

        return results;
    }

    private FStat getCorePairDistance(double rangeFactor) {
        FStat results = this.factory.getFStat();

        FPos3D center = this.aggregate.getMassCenter(MassCenter.SIMPLE_POLY);

        double cutoff = this.aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY);
        double range = cutoff * rangeFactor;

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

    private void setDistance(FPlot results, FStat distances, double min, double delta, boolean log) {
        double max = distances.max() - delta;

        double step = min;
        while (step <= max) {
            results.add(step, 0);
            step = log ? step * 1.1 : step + delta;
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
            case BOX -> getBoxCoverageAnalyze(
                    getBoxCoverageFunction(true));
            case CORRELATION -> getCoreDensityCorrelationAnalyze(
                    getCoreDensityCorrelationFunction(0.5, true));
        };
    }

    private double getBoxCoverageAnalyze(FPlot data) {
        data.mutateY((x, y) -> Math.log(y));
        data.mutateX((x, y) -> Math.log(1 / x));

        data.filter((x, y) -> y > 0);

        FPoly regression = data.reg().poly(1);

        FPlot fit = data.copy();
        fit.setY(regression);

        FPlotMeta plotConfig = factory.getFPlotMeta()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, data, fit);

        return regression.at(1);
    }

    private double getCoreDensityCorrelationAnalyze(FPlot data) {

        data.mutate((a, b) -> {
            a.ln();
            b.ln();
        });

        FPoly regression = data.reg().fitSlope((int) (data.size() * 0.9));

        FPlot fit = data.copy();
        fit.setY(regression);

        FPlotMeta plotConfig = factory.getFPlotMeta()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, data, fit);

        return 3 + regression.at(1);
    }
}
