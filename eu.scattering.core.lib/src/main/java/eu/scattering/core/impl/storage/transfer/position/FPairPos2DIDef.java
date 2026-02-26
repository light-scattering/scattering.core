package eu.scattering.core.impl.storage.transfer.position;

import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos2DI;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos2DI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos2DIDef implements FPairPos2DI {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos2DI";
    private static final String JSON_VAL = "val";

    private final FPos2DI posA;
    private final FPos2DI posB;

    private FPairPos2DIDef(FPos2DI posA, FPos2DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    public static FPairPos2DIDef create(StorageFactory factory, int AD0, int AD1, int BD0, int BD1) {

        return new FPairPos2DIDef(
                factory.getFPos2DI(AD0, AD1),
                factory.getFPos2DI(BD0, BD1)
        );
    }

    public static FPairPos2DIDef create(StorageFactory factory, FPos2DI posA, FPos2DI posB) {

        return new FPairPos2DIDef(posA, posB);
    }

    public static FPairPos2DIDef create(StorageFactory factory, JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos2DI posA = factory.getFPos2DI(structure.getJSONObject(0));
        FPos2DI posB = factory.getFPos2DI(structure.getJSONObject(1));

        return new FPairPos2DIDef(posA, posB);
    }

    public FPos2DI getPosA() {

        return posA;
    }

    public FPos2DI getPosB() {

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

        if (object instanceof FPairPos2DIDef fPairPos2DI) {

            return getPosA().equals(fPairPos2DI.getPosA()) && getPosB().equals(fPairPos2DI.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
