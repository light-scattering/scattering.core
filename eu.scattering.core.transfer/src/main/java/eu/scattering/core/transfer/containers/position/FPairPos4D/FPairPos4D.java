package eu.scattering.core.transfer.containers.position.FPairPos4D;

import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import eu.scattering.core.transfer.containers.position.Position;
import eu.scattering.core.transfer.containers.position.PositionFactory;
import eu.scattering.core.transfer.containers.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPairPos4D implements Position<FPairPos4D> {
    private static PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_TAG = "pairPos4D";

    private final FPos4D posA;
    private final FPos4D posB;

    private FPairPos4D(FPos4D posA, FPos4D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos4D create(FPos4D posA, FPos4D posB) {

        return new FPairPos4D(posA, posB);
    }

    protected static FPairPos4D create(String text) {

        return create(new JSONObject(text));
    }

    protected static FPairPos4D create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos4D posA = factory.getFPos4D(structure.getJSONObject(0));
        FPos4D posB = factory.getFPos4D(structure.getJSONObject(1));

        return new FPairPos4D(posA, posB);
    }

    public FPos4D getPosA() {
        return posA;
    }

    public FPos4D getPosB() {
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

        if (object instanceof FPairPos4D) {
            FPairPos4D fPosition = (FPairPos4D) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
