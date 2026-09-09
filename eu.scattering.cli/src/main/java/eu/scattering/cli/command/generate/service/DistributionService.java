package eu.scattering.cli.command.generate.service;

import eu.scattering.cli.command.generate.service.mixin.DistributionMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;

import java.util.List;

public class DistributionService {

    public static FAggregate assemble(ScatterFactory factory, DistributionMixin dist) {

        validateDist(dist);

        FAggregate template = factory.getRefFAggregate(applyDist(factory, dist).getList());

        if (template.size() < 5) {
            throw new IllegalArgumentException("The aggregate must consist of at least five particles");
        }

        return template;
    }

    //---------------------------------------------------------------------

    private static void validateDist(DistributionMixin dist) {
        int types = 0;

        if (dist.fixed != null && !dist.fixed.isEmpty()) {
            types++;
        }

        if (dist.normal != null && !dist.normal.isEmpty()) {
            types++;
        }

        if (dist.uniform != null && !dist.uniform.isEmpty()) {
            types++;
        }

        if (types == 0) {
            throw new IllegalArgumentException("At least one distribution must be defined.");
        }

        validateDistFixed(dist.fixed);
        validateDistNormal(dist.normal);
        validateDistUniform(dist.uniform);

    }

    private static void validateDistFixed(List<double[]> fixed) {

        if (fixed == null) {

            return;
        }

        for (double[] dist : fixed) {

            if (dist.length != 2) {
                throw new IllegalArgumentException("Fixed distributions must contain exactly two parameters.");
            }

            if (dist[0] < 1) {
                throw new IllegalArgumentException("The number of primary particles in each distribution must be at least one.");
            }

            if (dist[1] <= 0) {
                throw new IllegalArgumentException("The particle radius in fixed distributions must be greater then zero.");
            }
        }
    }

    private static void validateDistNormal(List<double[]> normal) {

        if (normal == null) {

            return;
        }

        for (double[] dist : normal) {

            if (dist.length != 3) {
                throw new IllegalArgumentException("Normal distributions must contain exactly three parameters.");
            }

            if (dist[0] < 1) {
                throw new IllegalArgumentException("The number of primary particles in each distribution must be at least one.");
            }

            if (dist[2] <= 0) {
                throw new IllegalArgumentException("The standard deviation must be greater then zero.");
            }
        }
    }

    private static void validateDistUniform(List<double[]> uniform) {

        if (uniform == null) {

            return;
        }

        for (double[] dist : uniform) {

            if (dist.length != 3) {
                throw new IllegalArgumentException("Uniform distributions must contain exactly three parameters.");
            }

            if (dist[0] < 1) {
                throw new IllegalArgumentException("The number of primary particles in each distribution must be at least one.");
            }

            if (dist[2] <= dist[1]) {
                throw new IllegalArgumentException("The max value must be greater then the min value.");
            }
        }
    }

    //---------------------------------------------------------------------

    private static FSphereProducer applyDist(ScatterFactory factory, DistributionMixin dist) {
        FSphereProducer producer = factory.getFSphereProducer();

        applyDistFixed(producer, dist.fixed);
        applyDistNormal(factory, producer, dist.normal);
        applyDistUniform(factory, producer, dist.uniform);

        return producer;
    }

    private static void applyDistFixed(FSphereProducer producer, List<double[]> fixed) {

        if (fixed == null) {

            return;
        }

        for (double[] dist : fixed) {
            producer.withFixRadius(dist[1], (int) dist[0]);
        }
    }

    private static void applyDistNormal(ScatterFactory factory, FSphereProducer producer, List<double[]> normal) {

        if (normal == null) {

            return;
        }

        for (double[] dist : normal) {
            producer.withDistRadius(factory.random().dist1D().normal(dist[1], dist[2]), (int) dist[0]);
        }
    }

    private static void applyDistUniform(ScatterFactory factory, FSphereProducer producer, List<double[]> uniform) {

        if (uniform == null) {

            return;
        }

        for (double[] dist : uniform) {
            producer.withDistRadius(factory.random().dist1D().uniform(dist[1], dist[2]), (int) dist[0]);
        }
    }
}
