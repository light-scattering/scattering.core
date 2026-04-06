package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FAggregateModuleFractalDimensionDCDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionDCDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    protected FPlot getResults(RadiusOfGyration method, double step, boolean rangeLimit) {
        FPlot results = this.factory.getFPlot();
        FPoint massCenter = this.factory.getFPoint();

        double radiusOfGyration = this.aggregate.getRadiusOfGyration(method, massCenter, null, null);

        double range = rangeLimit ? radiusOfGyration * 0.5 : this.aggregate.getRadiusFrom(massCenter) * 2.1;

        FStat distances = getCorePairDistance(massCenter.toFPos3D(), range);

        double min = distances.min();
        double start = min * step;
        double delta = min * 0.5;

        setDistance(results, distances, step, start, delta);
        setDensity(results, delta);

        results.filter((x, y) -> x > 0 && y > 0);

        return results;
    }

    protected double analyze(FPlot results, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        results.filter((x, y) -> y > 0);

        results.mutate((a, b) -> {
            a.ln();
            b.ln();
        });

        FPoly regression = window == 1 ? results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * window));

        FPlot fit = results.copy();
        fit.setY(regression);

        FPlotMetaGlobal plotConfig = factory.getFPlotMetaGlobal()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, results, fit);

        return 3 + regression.at(1);
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
        double cutoffP2 = (range * 2) * (range * 2);

        for (Shape particle : particles) {
            double distance = particle.getDistCenterP2(center);

            if (distance <= rangeP2) {
                internal.add(particle);
            } else if (distance <= cutoffP2) {
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
}
