package eu.scattering.core.transfer.containers.position.FPos3D;

import eu.scattering.core.transfer.containers.position.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FPos3D implements Position<FPos3D> {
    private static final String JSON_TAG = "pos3D";
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

        if (json.get(JSON_TYPE) != JSON_TAG) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var d0 = structure.getDouble(0);
        var d1 = structure.getDouble(1);
        var d2 = structure.getDouble(2);

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
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_TAG);
        json.append(JSON_VAL, getD0());
        json.append(JSON_VAL, getD1());
        json.append(JSON_VAL, getD2());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + (d0 * 1000);
        hashCode = 31 * hashCode + (d1 * 1000);
        hashCode = 31 * hashCode + (d2 * 1000);

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos3D) {
            FPos3D fPosition = (FPos3D) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
