package eu.scattering.core.design.core.data.tuplePos3DI;

import eu.scattering.core.design.core.Core;
import eu.scattering.core.design.core.data.pos3D.FPos3D;
import eu.scattering.core.design.core.data.pos3DI.FPos3DI;
import org.json.JSONArray;
import org.json.JSONObject;

public class FTuplePos3DI implements Core<FTuplePos3DI> {
    private static final String JSON_TAG = "tuplePos3DI";

    private final FPos3DI posA;
    private final FPos3DI posB;

    private FTuplePos3DI(FPos3DI posA, FPos3DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    public static FTuplePos3DI create(FPos3DI posA, FPos3DI posB) {

        return new FTuplePos3DI(posA, posB);
    }

    public static FTuplePos3DI create(String text) {

        return create(new JSONObject(text));
    }

    public static FTuplePos3DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos3DI posA = FPos3DI.create(structure.getJSONObject(0));
        FPos3DI posB = FPos3DI.create(structure.getJSONObject(1));

        return new FTuplePos3DI(posA, posB);
    }

    public FPos3DI getPosA() {
        return posA;
    }

    public FPos3DI getPosB() {
        return posB;
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append(JSON_TAG, getPosA().exportToJSON());
        json.append(JSON_TAG, getPosB().exportToJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + getPosA().hashCode();
        hashCode = 31 * hashCode + getPosB().hashCode();

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FTuplePos3DI) {
            FTuplePos3DI fPosition = (FTuplePos3DI) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
