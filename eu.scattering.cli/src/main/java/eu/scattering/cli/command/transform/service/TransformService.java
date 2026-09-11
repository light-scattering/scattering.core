package eu.scattering.cli.command.transform.service;

import eu.scattering.cli.command.transform.service.mixin.TransformMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;

public class TransformService {

    public static void transform(ScatterFactory factory, FAggregate aggregate, TransformMixin transformMixin) {

        if (transformMixin.steps != null) {
            for (TransformMixin.Step step : transformMixin.steps) {
                if (step.rotate != null) {
                    rotate(factory, aggregate, step.rotate);
                } else if (step.translate != null) {
                    translate(aggregate, step.translate);
                } else if (step.scale != null) {
                    scale(aggregate, step.scale);
                } else if (step.pca) {
                    pca(aggregate);
                }
            }
        }
    }

    private static void rotate(ScatterFactory factory, FAggregate aggregate, TransformMixin.Rotation data) {

        factory.rotate().mutate().aroundRg(aggregate, factory.getFPoint(data.d0(), data.d1(), data.d2()), data.radians());
    }

    private static void translate(FAggregate aggregate, TransformMixin.Translation data) {

        aggregate.translate(data.d0(), data.d1(), data.d2());
    }

    private static void scale(FAggregate aggregate, double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("Scaling requires exactly 1 parameter (factor).");
        }

        aggregate.scalePosition(factor);
        aggregate.scaleSize(factor);
    }

    private static void pca(FAggregate aggregate) {

        aggregate.pca();
    }
}
