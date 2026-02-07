package eu.scattering.core.impl.storage.position;

import eu.scattering.core.design.storage.transfer.single.variants.FPos2DI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPos2DIDef implements FPos2DI {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pos2DI";
    private static final String JSON_VAL = "val";

    private final int d0;
    private final int d1;

    private FPos2DIDef(int d0, int d1) {

        this.d0 = d0;
        this.d1 = d1;
    }

    public static FPos2DI create(int d0, int d1) {

        return new FPos2DIDef(d0, d1);
    }

    public static FPos2DI create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        int d0 = structure.getInt(0);
        int d1 = structure.getInt(1);

        return new FPos2DIDef(d0, d1);
    }

    public int getD0() {

        return d0;
    }

    public int getD1() {

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

        if (object instanceof FPos2DIDef fPos2DI) {

            return d0 == fPos2DI.getD0() && d1 == fPos2DI.getD1();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
