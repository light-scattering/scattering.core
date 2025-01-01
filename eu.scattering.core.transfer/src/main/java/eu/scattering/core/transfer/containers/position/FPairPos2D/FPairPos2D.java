package eu.scattering.core.transfer.containers.position.FPairPos2D;

import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import eu.scattering.core.transfer.containers.position.Position;
import eu.scattering.core.transfer.containers.position.PositionFactory;
import eu.scattering.core.transfer.containers.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FPairPos2D implements Position<FPairPos2D> {
    private static PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_TAG = "pairPos2D";
    private static final String JSON_VAL = "val";

    private final FPos2D posA;
    private final FPos2D posB;

    private FPairPos2D(FPos2D posA, FPos2D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos2D create(FPos2D posA, FPos2D posB) {

        return new FPairPos2D(posA, posB);
    }

    protected static FPairPos2D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var posA = factory.getFPos2D(structure.getJSONObject(0));
        var posB = factory.getFPos2D(structure.getJSONObject(1));

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

        json.put(JSON_TYPE, JSON_TAG);
        json.append(JSON_VAL, getPosA().exportToJSON());
        json.append(JSON_VAL, getPosB().exportToJSON());

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
