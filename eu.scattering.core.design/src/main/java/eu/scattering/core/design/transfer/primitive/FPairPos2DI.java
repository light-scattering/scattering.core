package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.transfer.Transfer;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos2DI implements Transfer {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos2DI";
    private static final String JSON_VAL = "val";

    private final FPos2DI posA;
    private final FPos2DI posB;

    private FPairPos2DI(FPos2DI posA, FPos2DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos2DI create(int AD0, int AD1, int BD0, int BD1) {

        return new FPairPos2DI(
                factory.getFPos2DI(AD0, AD1),
                factory.getFPos2DI(BD0, BD1)
        );
    }

    protected static FPairPos2DI create(FPos2DI posA, FPos2DI posB) {

        return new FPairPos2DI(posA, posB);
    }

    protected static FPairPos2DI create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos2DI posA = factory.getFPos2DI(structure.getJSONObject(0));
        FPos2DI posB = factory.getFPos2DI(structure.getJSONObject(1));

        return new FPairPos2DI(posA, posB);
    }

    public FPos2DI getPosA() {
        return posA;
    }

    public FPos2DI getPosB() {
        return posB;
    }

    public FPairPos2D toDouble() {

        return factory.getFPairPos2D(posA.toDouble(), posB.toDouble());
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

        if (object instanceof FPairPos2DI fPairPos2DI) {

            return getPosA().equals(fPairPos2DI.getPosA()) && getPosB().equals(fPairPos2DI.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
