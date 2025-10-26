package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.transfer.Transfer;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos4D implements Transfer {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos4D";
    private static final String JSON_VAL = "val";

    private final FPos4D posA;
    private final FPos4D posB;

    private FPairPos4D(FPos4D posA, FPos4D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos4D create(double AD0, double AD1, double AD2, double AD3, double BD0, double BD1, double BD2, double BD3) {

        return new FPairPos4D(
                factory.getFPos4D(AD0, AD1, AD2, AD3),
                factory.getFPos4D(BD0, BD1, BD2, BD3)
        );
    }

    protected static FPairPos4D create(FPos4D posA, FPos4D posB) {

        return new FPairPos4D(posA, posB);
    }

    protected static FPairPos4D create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
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

        if (object instanceof FPairPos4D fPairPos4D) {

            return getPosA().equals(fPairPos4D.getPosA()) && getPosB().equals(fPairPos4D.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
