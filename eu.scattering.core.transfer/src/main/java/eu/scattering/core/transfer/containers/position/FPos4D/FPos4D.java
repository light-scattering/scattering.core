package eu.scattering.core.transfer.containers.position.FPos4D;

import eu.scattering.core.transfer.containers.position.Position;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPos4D implements Position<FPos4D> {
    private static final String JSON_TAG = "pos4D";

    private final double d0;
    private final double d1;
    private final double d2;
    private final double d3;

    private FPos4D(double d0, double d1, double d2, double d3) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    protected static FPos4D create(double d0, double d1, double d2, double d3) {

        return new FPos4D(d0, d1, d2, d3);
    }

    protected static FPos4D create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        double d0 = structure.getInt(0);
        double d1 = structure.getInt(1);
        double d2 = structure.getInt(2);
        double d3 = structure.getInt(3);

        return new FPos4D(d0, d1, d2, d3);
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

    public double getD3() {

        return d3;
    }

//--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append(JSON_TAG, getD0());
        json.append(JSON_TAG, getD1());
        json.append(JSON_TAG, getD2());
        json.append(JSON_TAG, getD3());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + (d0 * 1000);
        hashCode = 31 * hashCode + (d1 * 1000);
        hashCode = 31 * hashCode + (d2 * 1000);
        hashCode = 31 * hashCode + (d3 * 1000);

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos4D) {
            FPos4D fPosition = (FPos4D) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2() && d3 == fPosition.getD3();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
