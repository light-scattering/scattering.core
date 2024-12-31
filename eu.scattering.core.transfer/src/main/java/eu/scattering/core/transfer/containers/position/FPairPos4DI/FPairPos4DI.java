package eu.scattering.core.transfer.containers.position.FPairPos4DI;

import eu.scattering.core.transfer.containers.position.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.containers.position.FPos4DI.FPos4DI;
import eu.scattering.core.transfer.containers.position.Position;
import eu.scattering.core.transfer.containers.position.PositionFactory;
import eu.scattering.core.transfer.containers.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

public class FPairPos4DI implements Position<FPairPos4DI> {
    private static PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_TAG = "pairPos4DI";

    private final FPos4DI posA;
    private final FPos4DI posB;

    private FPairPos4DI(FPos4DI posA, FPos4DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos4DI create(FPos4DI posA, FPos4DI posB) {

        return new FPairPos4DI(posA, posB);
    }

    protected static FPairPos4DI create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FPos4DI posA = factory.getFPos4DI(structure.getJSONObject(0));
        FPos4DI posB = factory.getFPos4DI(structure.getJSONObject(1));

        return new FPairPos4DI(posA, posB);
    }

    public FPos4DI getPosA() {
        return posA;
    }

    public FPos4DI getPosB() {
        return posB;
    }

    public FPairPos4D toDouble() {

        return factory.getFPairPos4D(posA.toDouble(), posB.toDouble());
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

        if (object instanceof FPairPos4DI) {
            FPairPos4DI fPosition = (FPairPos4DI) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
