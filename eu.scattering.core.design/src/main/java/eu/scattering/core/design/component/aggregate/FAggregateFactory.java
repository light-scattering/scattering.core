package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.aggregate.model.FModelFactory;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.storage.FMetaData;
import eu.scattering.core.transfer.container.buffer.array.FArray;

public interface FAggregateFactory extends FModelFactory {

    FAggregate getFAggregate();

    FAggregate getFAggregate(int capacity);

    FAggregate getFAggregate(FAssembly<Shape> particles);

    FAggregate getFAggregate(FAssembly<Shape> particles, int capacity);

    //--------------------------------------------------

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles);

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles, int capacity);

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles, FArray<FMetaData> refElements);

    //--------------------------------------------------

    FAggregate getFAggregateMono(int count, double radius);

    FAggregate getFAggregateMono(int count, double radius, int capacity);
}
