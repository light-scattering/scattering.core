package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.Geometry;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

public interface FAssemblyFactory {

    <T extends Geometry> FAssemblyProducer<T> getFAssemblyProducer();

    <T extends Geometry> FAssembly<T> getFAssembly();
    <T extends Geometry> FAssembly<T> getFAssembly(List<? extends T> elements);

    //--------------------------------------------------

    <T extends Geometry> FAssembly<T> getFAssembly(JSONObject json);

    //--------------------------------------------------

    default  <T extends Geometry> FAssembly<T> getFAssembly(String text) {

        try {
            return getFAssembly(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
