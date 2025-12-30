package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.normal.FDist1DNormal;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModuleTemplate;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.extension.Producer;

public class FAggregateFactoryModuleTemplateDef implements FAggregateFactoryModuleTemplate {
    private static FAggregateFactoryModuleTemplate self;
    private final ScatFactory factory;

    private FAggregateFactoryModuleTemplateDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FAggregateFactoryModuleTemplate get(ScatFactory factory) {

        if (FAggregateFactoryModuleTemplateDef.self == null) {
            FAggregateFactoryModuleTemplateDef.self = new FAggregateFactoryModuleTemplateDef(factory);
        }

        return FAggregateFactoryModuleTemplateDef.self;
    }

    //--------------------------------------------------

    public FAggregate monodisperse(int quantity, double radius) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(factory, fAssembly);
    }

    public FAggregate polydisperse(int quantity, double avg, double std, double cutoff) {

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
