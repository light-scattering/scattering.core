package eu.scattering.core.transfer.containers.position.FPairPos4D;

import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import eu.scattering.core.transfer.containers.position.Position;
import eu.scattering.core.transfer.containers.position.PositionFactory;
import eu.scattering.core.transfer.containers.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FPairPos4D implements Position<FPairPos4D> {
    private static PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_TAG = "pairPos4D";
    private static final String JSON_VAL = "val";

    private final FPos4D posA;
    private final FPos4D posB;

    private FPairPos4D(FPos4D posA, FPos4D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos4D create(FPos4D posA, FPos4D posB) {

        return new FPairPos4D(posA, posB);
    }

    protected static FPairPos4D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var posA = factory.getFPos4D(structure.getJSONObject(0));
        var posB = factory.getFPos4D(structure.getJSONObject(1));

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

        json.put(JSON_TYPE, JSON_TAG);
        json.append(JSON_VAL, getPosA().exportToJSON());
        json.append(JSON_VAL, getPosB().exportToJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getPosA(), getPosB());
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
