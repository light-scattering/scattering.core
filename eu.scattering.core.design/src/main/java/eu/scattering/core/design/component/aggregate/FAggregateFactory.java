package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.model.FModelFactory;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorFactory;
import eu.scattering.core.design.component.aggregate.validator.FValidatorFactory;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import org.json.JSONException;
import org.json.JSONObject;

public interface FAggregateFactory extends FModelFactory, FMonitorFactory, FValidatorFactory {

    FAggregate getFAggregate();

    @Modificator
    FAggregate getRefFAggregate(FAssembly<Shape> refParticles);

    //--------------------------------------------------

    FAggregate getFAggregatePreMono(int quantity, double radius);
    FAggregate getFAggregatePrePoly(int quantity, double avg, double std, double cutoff);

    //--------------------------------------------------

    FAggregate getFAggregateGeo1d(int d1, double radius);
    FAggregate getFAggregateGeo2d(int d1, int d2, double radius);
    FAggregate getFAggregateGeo3d(int d1, int d2, int d3, double radius);

    FAggregate getFAggregateGeoFullCircle(int layers, double radius);
    FAggregate getFAggregateGeoFullSphere(int layers, double radius);

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

    default FAggregate getFAggregateGeo1d(int d1) {

        return getFAggregateGeo1d(d1, 1);
    }

    default FAggregate getFAggregateGeo2d(int d1, int d2) {

        return getFAggregateGeo2d(d1, d2, 1);
    }

    default FAggregate getFAggregateGeo3d(int d1, int d2, int d3) {

        return getFAggregateGeo3d(d1, d2, d3, 1);
    }

    default FAggregate getFAggregateGeoFullCircle(int layers) {

        return getFAggregateGeoFullCircle(layers, 1);
    }

    default FAggregate getFAggregateGeoFullSphere(int layers) {

        return getFAggregateGeoFullSphere(layers, 1);
    }
}
