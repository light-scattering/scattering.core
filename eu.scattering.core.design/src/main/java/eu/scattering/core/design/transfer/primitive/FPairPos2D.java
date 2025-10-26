package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.transfer.Transfer;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos2D implements Transfer {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos2D";
    private static final String JSON_VAL = "val";

    private final FPos2D posA;
    private final FPos2D posB;

    private FPairPos2D(FPos2D posA, FPos2D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos2D create(double AD0, double AD1, double BD0, double BD1) {

        return new FPairPos2D(
                factory.getFPos2D(AD0, AD1),
                factory.getFPos2D(BD0, BD1)
        );
    }

    protected static FPairPos2D create(FPos2D posA, FPos2D posB) {

        return new FPairPos2D(posA, posB);
    }

    protected static FPairPos2D create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos2D posA = factory.getFPos2D(structure.getJSONObject(0));
        FPos2D posB = factory.getFPos2D(structure.getJSONObject(1));

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
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getPosA().toJSON());
        json.append(JSON_VAL, getPosB().toJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getPosA(), getPosB());
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

        return toJSON().toString();
    }
}
