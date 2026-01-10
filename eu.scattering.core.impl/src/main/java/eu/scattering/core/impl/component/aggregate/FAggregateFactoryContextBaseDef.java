package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.normal.FDist1DNormal;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextBase;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.extension.Producer;
import eu.scattering.core.design.statistics.base.FStat;

import java.util.ArrayList;
import java.util.List;

public class FAggregateFactoryContextBaseDef implements FAggregateFactoryContextBase {
    private final ScatFactory factory;

    private FAggregateFactoryContextBaseDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FAggregateFactoryContextBase create(ScatFactory factory) {

        return new FAggregateFactoryContextBaseDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregate monodisperse(int quantity, double radius) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(factory, fAssembly);
    }

    @Override
    public FAggregate polydisperse(int quantity, double avg, double std) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        FDist1DNormal fDist = factory.getFRand().getFDist1DNormal(avg, std);

        Producer<FSphere> fProducer = factory.getFSphereProducer(fDist);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(factory, fAssembly);
    }

    @Override
    public FAggregate polydisperse(int quantity, double avg, double std, double avgErr, double stdErr) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        FDist1DNormal fDist = factory.getFRand().getFDist1DNormal(avg, std);

        Producer<FSphere> fProducer = factory.getFSphereProducer(fDist);

        List<FSphere> candidates = new ArrayList<>(quantity);
        FStat statistics = factory.getFStat();

        while (true) {
            candidates.clear();
            statistics.clear();

            candidates.addAll(fProducer.getListRandomized(quantity));
            candidates.forEach(e -> statistics.add(e.getRadius()));

            if (Math.abs(statistics.mean() - avg) < avgErr) {
                if (Math.abs(statistics.std(true) - std) < stdErr) {
                    break;
                }
            }
        }

        FAssembly<Shape> fAssembly = factory.getFAssembly(candidates);

        return FAggregateDef.create(factory, fAssembly);
    }
}
