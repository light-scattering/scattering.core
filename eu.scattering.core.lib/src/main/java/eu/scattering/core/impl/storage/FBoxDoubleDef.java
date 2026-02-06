package eu.scattering.core.impl.storage;

import eu.scattering.core.design.storage.box.variants.FBoxDouble;
import org.json.JSONObject;

import java.util.Objects;

public class FBoxDoubleDef implements FBoxDouble {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "boxDouble";
    private static final String JSON_VAL = "val";

    private double value = 0;

    private FBoxDoubleDef() {}

    public static FBoxDoubleDef create() {

        return new FBoxDoubleDef();
    }

    public double getValue() {

        return this.value;
    }

    public void setValue(double value) {

        this.value = value;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_VAL, getValue());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(this.value);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FBoxDoubleDef fBoxString) {

            return this.value == fBoxString.getValue();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
