package eu.scattering.core.impl.storage.position;

import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variants.integer.FPairPos3DI;
import eu.scattering.core.design.storage.transfer.position.p1.variants.integer.FPos3DI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos3DIDef implements FPairPos3DI {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos3DI";
    private static final String JSON_VAL = "val";

    private final FPos3DI posA;
    private final FPos3DI posB;

    private FPairPos3DIDef(FPos3DI posA, FPos3DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    public static FPairPos3DIDef create(StorageFactory factory, int AD0, int AD1, int AD2, int BD0, int BD1, int BD2) {

        return new FPairPos3DIDef(
                factory.getFPos3DI(AD0, AD1, AD2),
                factory.getFPos3DI(BD0, BD1, BD2)
        );
    }

    public static FPairPos3DIDef create(StorageFactory factory, FPos3DI posA, FPos3DI posB) {

        return new FPairPos3DIDef(posA, posB);
    }

    public static FPairPos3DIDef create(StorageFactory factory, JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos3DI posA = factory.getFPos3DI(structure.getJSONObject(0));
        FPos3DI posB = factory.getFPos3DI(structure.getJSONObject(1));

        return new FPairPos3DIDef(posA, posB);
    }

    public FPos3DI getPosA() {

        return posA;
    }

    public FPos3DI getPosB() {

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

        if (object instanceof FPairPos3DIDef fPairPos3DI) {

            return getPosA().equals(fPairPos3DI.getPosA()) && getPosB().equals(fPairPos3DI.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
