package eu.scattering.core.design.core.data.position;

import eu.scattering.core.design.core.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPos3D implements Core<FPos3D> {
    private static final String JSON_TAG = "pos3D";

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

    protected static FPos3D create(String json) {

        return create(new JSONObject(json));
    }

    protected static FPos3D create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        double d0 = structure.getInt(0);
        double d1 = structure.getInt(1);
        double d2 = structure.getInt(2);

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

        json.append(JSON_TAG, getD0());
        json.append(JSON_TAG, getD1());
        json.append(JSON_TAG, getD2());

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
