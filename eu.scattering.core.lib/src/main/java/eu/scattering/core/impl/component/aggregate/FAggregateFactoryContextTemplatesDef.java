package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal.FRandDist1DNormal;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextTemplates;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.design.statistics.base.FStat;

import java.util.ArrayList;
import java.util.List;

public class FAggregateFactoryContextTemplatesDef implements FAggregateFactoryContextTemplates {
    private final ScatterFactory factory;

    private FAggregateFactoryContextTemplatesDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FAggregateFactoryContextTemplates create(ScatterFactory factory) {

        return new FAggregateFactoryContextTemplatesDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregate monodisperse(int quantity, double radius) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        Producer<FSphere> fProducer = this.factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = this.factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(this.factory, fAssembly);
    }

    @Override
    public FAggregate polydisperse(int quantity, double avg, double std) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        FRandDist1DNormal fDist = this.factory.random().dist1D().normal(avg, std);

        Producer<FSphere> fProducer = this.factory.getFSphereProducer(fDist);
        FAssembly<Shape> fAssembly = this.factory.getFAssembly(fProducer.getListRandomized(quantity));

        return FAggregateDef.create(this.factory, fAssembly);
    }

    @Override
    public FAggregate polydisperse(int quantity, double avg, double std, double avgErr, double stdErr) {

        if (quantity < 1) {
            throw new IllegalArgumentException("The number of particles must be greater than zero");
        }

        FRandDist1DNormal fDist = this.factory.random().dist1D().normal(avg, std);

        Producer<FSphere> fProducer = this.factory.getFSphereProducer(fDist);

        List<FSphere> candidates = new ArrayList<>(quantity);
        FStat statistics = this.factory.getFStat();

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

        FAssembly<Shape> fAssembly = this.factory.getFAssembly(candidates);

        return FAggregateDef.create(this.factory, fAssembly);
    }
}
