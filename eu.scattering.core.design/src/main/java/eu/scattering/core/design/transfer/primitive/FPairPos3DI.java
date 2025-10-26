package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.transfer.Transfer;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos3DI implements Transfer {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos3DI";
    private static final String JSON_VAL = "val";

    private final FPos3DI posA;
    private final FPos3DI posB;

    private FPairPos3DI(FPos3DI posA, FPos3DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos3DI create(int AD0, int AD1, int AD2, int BD0, int BD1, int BD2) {

        return new FPairPos3DI(
                factory.getFPos3DI(AD0, AD1, AD2),
                factory.getFPos3DI(BD0, BD1, BD2)
        );
    }

    protected static FPairPos3DI create(FPos3DI posA, FPos3DI posB) {

        return new FPairPos3DI(posA, posB);
    }

    protected static FPairPos3DI create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos3DI posA = factory.getFPos3DI(structure.getJSONObject(0));
        FPos3DI posB = factory.getFPos3DI(structure.getJSONObject(1));

        return new FPairPos3DI(posA, posB);
    }

    public FPos3DI getPosA() {
        return posA;
    }

    public FPos3DI getPosB() {
        return posB;
    }

    public FPairPos3D toDouble() {

        return factory.getFPairPos3D(posA.toDouble(), posB.toDouble());
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

        if (object instanceof FPairPos3DI fPairPos3DI) {

            return getPosA().equals(fPairPos3DI.getPosA()) && getPosB().equals(fPairPos3DI.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
