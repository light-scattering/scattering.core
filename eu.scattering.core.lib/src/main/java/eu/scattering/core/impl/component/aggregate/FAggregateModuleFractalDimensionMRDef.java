package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.ArrayList;
import java.util.List;

public class FAggregateModuleFractalDimensionMRDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionMRDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    protected FPlot getResults(RadiusOfGyration type, double step, boolean rangeLimit) {
        FPoint massCenter = this.factory.getFPoint();

        List<Double> massFragments = new ArrayList<>(this.aggregate.size());
        List<FPos3D> centerFragments = new ArrayList<>(this.aggregate.size());

        double radiusOfGyration = this.aggregate.getRadiusOfGyration(type, massCenter, massFragments, centerFragments);

        double range = rangeLimit ? radiusOfGyration * 0.5 : this.aggregate.getRadiusFrom(massCenter) * 2.1;

        FPlot results = getData(step, range);

        int count = setData(results, massCenter.toFPos3D(), massFragments, centerFragments, range);

        results.mutateY(a -> a.mutate(b -> b / count));
        results.mutateX(a -> a.mutate(Math::sqrt));

        return results;
    }

    protected double analyze(FPlot results, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        for (int i = 1 ; i < results.size() ; i++) {
            if (results.getY(i) == results.getY(i - 1)) {
                results.setX(i - 1, Double.NaN);
            } else {

                break;
            }
        }

        for (int i = results.size() - 2 ; i >= 0 ; i--) {
            if (results.getY(i) == results.getY(i + 1)) {
                results.setX(i + 1 , Double.NaN);
            } else {

                break;
            }
        }

        results = results.removeNaN();

        results.mutateY((x, y) -> Math.log(y));
        results.mutateX((x, y) -> Math.log(x));

        results.filter((x, y) -> y > 0);

        FPoly regression = window == 1 ? results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * window));

        FPlot fit = results.copy();
        fit.setY(regression);

        FPlotMetaGlobal plotConfig = factory.getFPlotMetaGlobal()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, results, fit);

        return regression.at(1);
    }

    private FPlot getData(double step, double range) {
        FPlot results = this.factory.getFPlot();

        FStat particles = this.aggregate.getFStatParticleRadius();

        double min = particles.mean() * 2;

        double distance = min * step;
        while (distance <= range) {
            results.add(distance * distance, 0);
            distance = distance * step;
        }

        return results;
    }

    private int setData(FPlot results, FPos3D massCenter, List<Double> massFragments, List<FPos3D> centerFragments, double limit) {
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

}
