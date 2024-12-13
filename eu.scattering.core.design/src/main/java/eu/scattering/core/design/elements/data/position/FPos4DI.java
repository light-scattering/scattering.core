package eu.scattering.core.design.elements.data.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPos4DI implements Core<FPos4DI> {
    private static final String JSON_TAG = "pos4DI";

    private final int d0;
    private final int d1;
    private final int d2;
    private final int d3;

    private FPos4DI(int d0, int d1, int d2, int d3) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    protected static FPos4DI create(int d0, int d1, int d2, int d3) {

        return new FPos4DI(d0, d1, d2, d3);
    }

    protected static FPos4DI create(String json) {

        return create(new JSONObject(json));
    }

    protected static FPos4DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        int d0 = structure.getInt(0);
        int d1 = structure.getInt(1);
        int d2 = structure.getInt(2);
        int d3 = structure.getInt(3);

        return new FPos4DI(d0, d1, d2, d3);
    }

    public int getD0() {

        return d0;
    }

    public int getD1() {

        return d1;
    }

    public int getD2() {

        return d2;
    }

    public int getD3() {

        return d3;
    }

    public FPos4D toDouble() {
        return FPos4D.create(d0, d1, d2, d3);
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
        int hashCode = 7;

        hashCode = 31 * hashCode + d0;
        hashCode = 31 * hashCode + d1;
        hashCode = 31 * hashCode + d2;
        hashCode = 31 * hashCode + d3;

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos4DI) {
            FPos4DI fPosition = (FPos4DI) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2() && d3 == fPosition.getD3();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
