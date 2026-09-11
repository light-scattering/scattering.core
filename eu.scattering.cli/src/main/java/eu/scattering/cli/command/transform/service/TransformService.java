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

    private static void rotate(ScatterFactory factory, FAggregate aggregate, double[] data) {

        if (data.length != 4) {
            throw new IllegalArgumentException("Rotation requires exactly 4 parameters (d0,d1,d2,radians).");
        }

        factory.rotate().mutate().aroundRg(aggregate, factory.getFPoint(data[0], data[1], data[2]), data[3]);
    }

    private static void translate(FAggregate aggregate, double[] data) {

        if (data.length != 3) {
            throw new IllegalArgumentException("Translation requires exactly 3 parameters (d0,d1,d2).");
        }

        aggregate.translate(data[0], data[1], data[2]);
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
