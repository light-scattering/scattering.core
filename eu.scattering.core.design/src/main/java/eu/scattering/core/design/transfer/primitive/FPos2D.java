package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPos2D implements Transfer {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pos2D";
    private static final String JSON_VAL = "val";

    private final double d0;
    private final double d1;

    private FPos2D(double d0, double d1) {

        this.d0 = d0;
        this.d1 = d1;
    }

    protected static FPos2D create(double d0, double d1) {

        return new FPos2D(d0, d1);
    }

    protected static FPos2D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        double d0 = structure.getDouble(0);
        double d1 = structure.getDouble(1);

        return new FPos2D(d0, d1);
    }

    public double getD0() {

        return d0;
    }

    public double getD1() {

        return d1;
    }

//--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getD0());
        json.append(JSON_VAL, getD1());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(d0, d1);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos2D fPos2D) {

            return d0 == fPos2D.getD0() && d1 == fPos2D.getD1();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
