package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.model.FModelFactory;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorFactory;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import org.json.JSONObject;

public interface FAggregateFactory extends FModelFactory, FMonitorFactory {

    FAggregate getFAggregate();

    FAggregate getFAggregate(int capacity);

    FAggregate getFAggregate(FAssembly<Shape> particles);

    FAggregate getFAggregate(FAssembly<Shape> particles, int capacity);

    //--------------------------------------------------

    FAggregate getFAggregate(String json);

    FAggregate getFAggregate(JSONObject json);

    //--------------------------------------------------

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles);

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles, int capacity);

    //--------------------------------------------------

    FAggregate getFAggregateMono(int count, double radius);

    FAggregate getFAggregateMono(int count, double radius, int capacity);
}
