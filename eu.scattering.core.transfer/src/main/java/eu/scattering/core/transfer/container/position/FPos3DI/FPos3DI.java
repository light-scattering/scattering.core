package eu.scattering.core.transfer.container.position.FPos3DI;

import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.position.Position;
import eu.scattering.core.transfer.container.position.PositionFactory;
import eu.scattering.core.transfer.container.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FPos3DI implements Position<FPos3DI> {
    private static final PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_MAIN = "pos3DI";
    private static final String JSON_VAL = "val";

    private final int d0;
    private final int d1;
    private final int d2;

    private FPos3DI(int d0, int d1, int d2) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    protected static FPos3DI create(int d0, int d1, int d2) {

        return new FPos3DI(d0, d1, d2);
    }

    protected static FPos3DI create(String json) {

        return create(new JSONObject(json));
    }

    protected static FPos3DI create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        int d0 = structure.getInt(0);
        int d1 = structure.getInt(1);
        int d2 = structure.getInt(2);

        return new FPos3DI(d0, d1, d2);
    }

    public int getD0() {

        return d0;
    }

    public int getD1() {

        return d1;
    }

    public int getD2() {

        return d2;
    }

    public FPos3D toDouble() {

        return factory.getFPos3D(d0, d1, d2);
    }

//--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getD0());
        json.append(JSON_VAL, getD1());
        json.append(JSON_VAL, getD2());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(d0, d1, d2);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos3DI) {
            FPos3DI fPosition = (FPos3DI) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
