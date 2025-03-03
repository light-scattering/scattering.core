package eu.scattering.core.transfer.container.storage.FPairPos4DI;

import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos4DI.FPos4DI;
import eu.scattering.core.transfer.container.storage.Storage;
import eu.scattering.core.transfer.container.storage.StorageFactory;
import eu.scattering.core.transfer.container.storage.StorageFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FPairPos4DI implements Storage<FPairPos4DI> {
    private static final StorageFactory factory = StorageFactoryConcrete.create();
    private static final String JSON_MAIN = "pairPos4DI";
    private static final String JSON_VAL = "val";

    private final FPos4DI posA;
    private final FPos4DI posB;

    private FPairPos4DI(FPos4DI posA, FPos4DI posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos4DI create(int AD0, int AD1, int AD2, int AD3, int BD0, int BD1, int BD2, int BD3) {

        return new FPairPos4DI(
                factory.getFPos4DI(AD0, AD1, AD2, AD3),
                factory.getFPos4DI(BD0, BD1, BD2, BD3)
        );
    }

    protected static FPairPos4DI create(FPos4DI posA, FPos4DI posB) {

        return new FPairPos4DI(posA, posB);
    }

    protected static FPairPos4DI create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
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

        if (object instanceof FPairPos4DI) {
            FPairPos4DI fPosition = (FPairPos4DI) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
