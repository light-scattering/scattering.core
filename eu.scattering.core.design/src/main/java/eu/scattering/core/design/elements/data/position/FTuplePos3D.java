package eu.scattering.core.design.elements.data.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FTuplePos3D implements Core<FTuplePos3D> {
    private static final String JSON_TAG = "tuplePos3D";

    private final FPos3D posA;
    private final FPos3D posB;

    private FTuplePos3D(FPos3D posA, FPos3D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FTuplePos3D create(FPos3D posA, FPos3D posB) {

        return new FTuplePos3D(posA, posB);
    }

    protected static FTuplePos3D create(String text) {

        return create(new JSONObject(text));
    }

    protected static FTuplePos3D create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos3D posA = FPos3D.create(structure.getJSONObject(0));
        FPos3D posB = FPos3D.create(structure.getJSONObject(1));

        return new FTuplePos3D(posA, posB);
    }

    public FPos3D getPosA() {
        return posA;
    }

    public FPos3D getPosB() {
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

        if (object instanceof FTuplePos3D) {
            FTuplePos3D fPosition = (FTuplePos3D) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
