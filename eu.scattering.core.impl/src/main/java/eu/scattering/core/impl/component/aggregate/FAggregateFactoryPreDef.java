package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.normal.FDist1DNormal;
import eu.scattering.core.design.extension.Producer;

public class FAggregateFactoryPreDef {

    public static FAggregate createFAggregatePreMono(ScatFactory factory, int quantity, double radius) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(factory, fAssembly);
    }

    public static FAggregate createFAggregatePrePoly(ScatFactory factory, int quantity, double avg, double std, double cutoff) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        FDist1DNormal fDist = factory.getFRand().getFDist1DNormal(avg, std);
        fDist.setCutoffMin(cutoff);

        Producer<FSphere> fProducer = factory.getFSphereProducer(fDist);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(factory, fAssembly);
    }
}
