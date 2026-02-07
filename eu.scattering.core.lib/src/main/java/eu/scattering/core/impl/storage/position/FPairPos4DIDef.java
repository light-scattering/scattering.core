package eu.scattering.core.impl.storage.position;

import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos4DI;
import eu.scattering.core.design.storage.transfer.single.variants.FPos4DI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class FPairPos4DIDef implements FPairPos4DI {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "pairPos4DI";
    private static final String JSON_VAL = "val";

    private final FPos4DI posA;
    private final FPos4DI posB;

    private FPairPos4DIDef(FPos4DI posA, FPos4DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    public static FPairPos4DIDef create(StorageFactory factory, int AD0, int AD1, int AD2, int AD3, int BD0, int BD1, int BD2, int BD3) {

        return new FPairPos4DIDef(
                factory.getFPos4DI(AD0, AD1, AD2, AD3),
                factory.getFPos4DI(BD0, BD1, BD2, BD3)
        );
    }

    public static FPairPos4DIDef create(StorageFactory factory, FPos4DI posA, FPos4DI posB) {

        return new FPairPos4DIDef(posA, posB);
    }

    public static FPairPos4DIDef create(StorageFactory factory, JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos4DI posA = factory.getFPos4DI(structure.getJSONObject(0));
        FPos4DI posB = factory.getFPos4DI(structure.getJSONObject(1));

        return new FPairPos4DIDef(posA, posB);
    }

    public FPos4DI getPosA() {

        return posA;
    }

    public FPos4DI getPosB() {

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

        if (object instanceof FPairPos4DIDef fPairPos4DI) {

            return getPosA().equals(fPairPos4DI.getPosA()) && getPosB().equals(fPairPos4DI.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
