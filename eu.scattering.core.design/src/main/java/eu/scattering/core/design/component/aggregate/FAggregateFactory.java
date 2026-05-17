package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.aggregate.meta.FMetaFactory;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.model.FModelFactory;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorFactory;
import eu.scattering.core.design.component.aggregate.validator.FValidatorFactory;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface FAggregateFactory extends FModelFactory, FMonitorFactory, FValidatorFactory, FMetaFactory {

    FAggregate getFAggregate();

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles);
    @Modificator
    FAggregate getRefFAggregate(List<Shape> refParticles);

    //--------------------------------------------------

    FAggregateFactoryContext getFAggregateContext();

    //--------------------------------------------------

    FAggregate getFAggregate(JSONObject json);

    //--------------------------------------------------

    default FAggregate getFAggregate(String text) {

        try {
            return getFAggregate(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
