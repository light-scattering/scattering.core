package eu.scattering.core.design.transfer.box;

import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONObject;

import java.util.Objects;

public class FBoxString implements Transfer {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "boxString";
    private static final String JSON_VAL = "val";

    private String value = "";

    private FBoxString() {}

    protected static FBoxString create() {

        return new FBoxString();
    }

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

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

        if (object instanceof FBoxString fBoxString) {

            return this.value.equals(fBoxString.getValue());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
