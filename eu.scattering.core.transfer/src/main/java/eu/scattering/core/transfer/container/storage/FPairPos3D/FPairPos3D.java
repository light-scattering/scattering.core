package eu.scattering.core.transfer.container.storage.FPairPos3D;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.Storage;
import eu.scattering.core.transfer.container.storage.XXXFactory;
import eu.scattering.core.transfer.container.storage.XXXFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FPairPos3D implements Storage<FPairPos3D> {
    private static final XXXFactory factory = XXXFactoryConcrete.create();
    private static final String JSON_MAIN = "pairPos3D";
    private static final String JSON_VAL = "val";

    private final FPos3D posA;
    private final FPos3D posB;

    private FPairPos3D(FPos3D posA, FPos3D posB) {

        this.posA = posA;
        this.posB = posB;
    }

    protected static FPairPos3D create(double AD0, double AD1, double AD2, double BD0, double BD1, double BD2) {

        return new FPairPos3D(
                factory.getFPos3D(AD0, AD1, AD2),
                factory.getFPos3D(BD0, BD1, BD2)
        );
    }

    protected static FPairPos3D create(FPos3D posA, FPos3D posB) {

        return new FPairPos3D(posA, posB);
    }

    protected static FPairPos3D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPos3D posA = factory.getFPos3D(structure.getJSONObject(0));
        FPos3D posB = factory.getFPos3D(structure.getJSONObject(1));

        return new FPairPos3D(posA, posB);
    }

    public FPos3D getPosA() {

        return posA;
    }

    public FPos3D getPosB() {

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

        if (object instanceof FPairPos3D) {
            FPairPos3D fPosition = (FPairPos3D) object;

            return getPosA().equals(fPosition.getPosA()) && getPosB().equals(fPosition.getPosB());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
