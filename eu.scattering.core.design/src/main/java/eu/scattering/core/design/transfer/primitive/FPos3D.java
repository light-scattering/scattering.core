package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPos3D implements Transfer {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pos3D";
    private static final String JSON_VAL = "val";

    private final double d0;
    private final double d1;
    private final double d2;

    private FPos3D(double d0, double d1, double d2) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    protected static FPos3D create(double d0, double d1, double d2) {

        return new FPos3D(d0, d1, d2);
    }

    protected static FPos3D create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        double d0 = structure.getDouble(0);
        double d1 = structure.getDouble(1);
        double d2 = structure.getDouble(2);

        return new FPos3D(d0, d1, d2);
    }

    public double getD0() {

        return d0;
    }

    public double getD1() {

        return d1;
    }

    public double getD2() {

        return d2;
    }

//--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getD0());
        json.append(JSON_VAL, getD1());
        json.append(JSON_VAL, getD2());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(d0, d1, d2);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos3D fPos3D) {

            return d0 == fPos3D.getD0() && d1 == fPos3D.getD1() && d2 == fPos3D.getD2();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
