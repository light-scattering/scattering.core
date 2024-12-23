package eu.scattering.core.design.transfers.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPos2D implements Core<FPos2D> {
    private static final String JSON_TAG = "pos2D";

    private final double d0;
    private final double d1;

    private FPos2D(double d0, double d1) {

        this.d0 = d0;
        this.d1 = d1;
    }

    protected static FPos2D create(double d0, double d1) {

        return new FPos2D(d0, d1);
    }

    protected static FPos2D create(String text) {

        return create(new JSONObject(text));
    }

    protected static FPos2D create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        double d0 = structure.getInt(0);
        double d1 = structure.getInt(1);

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
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append(JSON_TAG, getD0());
        json.append(JSON_TAG, getD1());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + (d0 * 1000);
        hashCode = 31 * hashCode + (d1 * 1000);

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos2D) {
            FPos2D fPosition = (FPos2D) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
