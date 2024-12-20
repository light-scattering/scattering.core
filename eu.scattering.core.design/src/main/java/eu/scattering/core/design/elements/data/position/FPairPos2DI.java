package eu.scattering.core.design.elements.data.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPairPos2DI implements Core<FPairPos2DI> {
    private static final String JSON_TAG = "pairPos2DI";

    private final FPos2DI posA;
    private final FPos2DI posB;

    private FPairPos2DI(FPos2DI posA, FPos2DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos2DI create(FPos2DI posA, FPos2DI posB) {

        return new FPairPos2DI(posA, posB);
    }

    protected static FPairPos2DI create(String text) {

        return create(new JSONObject(text));
    }

    protected static FPairPos2DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos2DI posA = FPos2DI.create(structure.getJSONObject(0));
        FPos2DI posB = FPos2DI.create(structure.getJSONObject(1));

        return new FPairPos2DI(posA, posB);
    }

    public FPos2DI getPosA() {
        return posA;
    }

    public FPos2DI getPosB() {
        return posB;
    }

    public FPairPos2D toDouble() {

        return FPairPos2D.create(posA.toDouble(), posB.toDouble());
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

        if (object instanceof FPairPos2DI) {
            FPairPos2DI fPosition = (FPairPos2DI) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
