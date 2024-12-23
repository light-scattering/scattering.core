package eu.scattering.core.design.transfers.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPairPos2D implements Core<FPairPos2D> {
    private static final String JSON_TAG = "pairPos2D";

    private final FPos2D posA;
    private final FPos2D posB;

    private FPairPos2D(FPos2D posA, FPos2D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos2D create(FPos2D posA, FPos2D posB) {

        return new FPairPos2D(posA, posB);
    }

    protected static FPairPos2D create(String text) {

        return create(new JSONObject(text));
    }

    protected static FPairPos2D create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos2D posA = FPos2D.create(structure.getJSONObject(0));
        FPos2D posB = FPos2D.create(structure.getJSONObject(1));

        return new FPairPos2D(posA, posB);
    }

    public FPos2D getPosA() {
        return posA;
    }

    public FPos2D getPosB() {
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

        if (object instanceof FPairPos2D) {
            FPairPos2D fPosition = (FPairPos2D) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
