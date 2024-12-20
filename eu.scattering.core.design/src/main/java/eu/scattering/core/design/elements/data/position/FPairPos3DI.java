package eu.scattering.core.design.elements.data.position;

import eu.scattering.core.design.elements.Core;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPairPos3DI implements Core<FPairPos3DI> {
    private static final String JSON_TAG = "pairPos3DI";

    private final FPos3DI posA;
    private final FPos3DI posB;

    private FPairPos3DI(FPos3DI posA, FPos3DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos3DI create(FPos3DI posA, FPos3DI posB) {

        return new FPairPos3DI(posA, posB);
    }

    protected static FPairPos3DI create(String text) {

        return create(new JSONObject(text));
    }

    protected static FPairPos3DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos3DI posA = FPos3DI.create(structure.getJSONObject(0));
        FPos3DI posB = FPos3DI.create(structure.getJSONObject(1));

        return new FPairPos3DI(posA, posB);
    }

    public FPos3DI getPosA() {
        return posA;
    }

    public FPos3DI getPosB() {
        return posB;
    }

    public FPairPos3D toDouble() {

        return FPairPos3D.create(posA.toDouble(), posB.toDouble());
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

        if (object instanceof FPairPos3DI) {
            FPairPos3DI fPosition = (FPairPos3DI) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
