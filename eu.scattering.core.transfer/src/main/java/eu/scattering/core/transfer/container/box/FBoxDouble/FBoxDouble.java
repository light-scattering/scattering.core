package eu.scattering.core.transfer.container.box.FBoxDouble;

import eu.scattering.core.transfer.container.box.Box;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FBoxDouble implements Box<FBoxDouble> {
    private static final String JSON_MAIN = "boxDouble";
    private static final String JSON_VAL = "val";

    private double value = 0;

    private FBoxDouble() {}

    protected static FBoxDouble create() {

        return new FBoxDouble();
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

        if (object instanceof FBoxDouble fBoxString) {

            return this.value == fBoxString.getValue();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
