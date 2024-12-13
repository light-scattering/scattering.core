package eu.scattering.core.design.elements.data.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPos3DI implements Core<FPos3DI> {
    private static final String JSON_TAG = "pos3DI";

    private final int d0;
    private final int d1;
    private final int d2;

    private FPos3DI(int d0, int d1, int d2) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    protected static FPos3DI create(int d0, int d1, int d2) {

        return new FPos3DI(d0, d1, d2);
    }

    protected static FPos3DI create(String json) {

        return create(new JSONObject(json));
    }

    protected static FPos3DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        int d0 = structure.getInt(0);
        int d1 = structure.getInt(1);
        int d2 = structure.getInt(2);

        return new FPos3DI(d0, d1, d2);
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

    public FPos3D toDouble() {
        return FPos3D.create(d0, d1, d2);
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
        int hashCode = 7;

        hashCode = 31 * hashCode + d0;
        hashCode = 31 * hashCode + d1;
        hashCode = 31 * hashCode + d2;

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos3DI) {
            FPos3DI fPosition = (FPos3DI) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
