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

    private static void validateDistFixed(List<DistributionMixin.RadFixed> fixed) {

        if (fixed == null) {

            return;
        }

        for (DistributionMixin.RadFixed dist : fixed) {

            if (dist.count() < 1) {
                throw new IllegalArgumentException("The number of primary particles in each distribution must be at least one.");
            }

            if (dist.radius() <= 0) {
                throw new IllegalArgumentException("The particle radius in fixed distributions must be greater then zero.");
            }
        }
    }

    private static void validateDistNormal(List<DistributionMixin.RadNormal> normal) {

        if (normal == null) {

            return;
        }

        for (DistributionMixin.RadNormal dist : normal) {

            if (dist.count() < 1) {
                throw new IllegalArgumentException("The number of primary particles in each distribution must be at least one.");
            }

            if (dist.std() <= 0) {
                throw new IllegalArgumentException("The standard deviation must be greater then zero.");
            }
        }
    }

    private static void validateDistUniform(List<DistributionMixin.RadUniform> uniform) {

        if (uniform == null) {

            return;
        }

        for (DistributionMixin.RadUniform dist : uniform) {

            if (dist.count() < 1) {
                throw new IllegalArgumentException("The number of primary particles in each distribution must be at least one.");
            }

            if (dist.max() <= dist.min()) {
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

    private static void applyDistFixed(FSphereProducer producer, List<DistributionMixin.RadFixed> fixed) {

        if (fixed == null) {

            return;
        }

        for (DistributionMixin.RadFixed dist : fixed) {
            producer.withFixRadius(dist.radius(), dist.count());
        }
    }

    private static void applyDistNormal(ScatterFactory factory, FSphereProducer producer, List<DistributionMixin.RadNormal> normal) {

        if (normal == null) {

            return;
        }

        for (DistributionMixin.RadNormal dist : normal) {
            producer.withDistRadius(factory.random().dist1D().normal(dist.avg(), dist.std()), dist.count());
        }
    }

    private static void applyDistUniform(ScatterFactory factory, FSphereProducer producer, List<DistributionMixin.RadUniform> uniform) {

        if (uniform == null) {

            return;
        }

        for (DistributionMixin.RadUniform dist : uniform) {
            producer.withDistRadius(factory.random().dist1D().uniform(dist.min(), dist.max()), dist.count());
        }
    }
}
