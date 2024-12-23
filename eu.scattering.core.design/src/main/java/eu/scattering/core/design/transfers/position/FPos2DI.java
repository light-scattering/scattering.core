package eu.scattering.core.design.transfers.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPos2DI implements Core<FPos2DI> {
    private static final String JSON_TAG = "pos2DI";

    private final int d0;
    private final int d1;

    private FPos2DI(int d0, int d1) {

        this.d0 = d0;
        this.d1 = d1;
    }

    protected static FPos2DI create(int d0, int d1) {

        return new FPos2DI(d0, d1);
    }

    protected static FPos2DI create(String json) {

        return create(new JSONObject(json));
    }

    protected static FPos2DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        int d0 = structure.getInt(0);
        int d1 = structure.getInt(1);

        return new FPos2DI(d0, d1);
    }

    public int getD0() {

        return d0;
    }

    public int getD1() {

        return d1;
    }

    public FPos2D toDouble() {

        return FPos2D.create(d0, d1);
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
        int hashCode = 7;

        hashCode = 31 * hashCode + d0;
        hashCode = 31 * hashCode + d1;

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos2DI) {
            FPos2DI fPosition = (FPos2DI) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
