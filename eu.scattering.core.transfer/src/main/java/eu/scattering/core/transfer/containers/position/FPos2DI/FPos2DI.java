package eu.scattering.core.transfer.containers.position.FPos2DI;

import eu.scattering.core.transfer.containers.Container;
import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import eu.scattering.core.transfer.containers.position.PositionFactory;
import eu.scattering.core.transfer.containers.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FPos2DI implements Container<FPos2DI> {
    private static PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_TAG = "pos2DI";
    private static final String JSON_VAL = "val";

    private final int d0;
    private final int d1;

    private FPos2DI(int d0, int d1) {

        this.d0 = d0;
        this.d1 = d1;
    }

    protected static FPos2DI create(int d0, int d1) {

        return new FPos2DI(d0, d1);
    }

    protected static FPos2DI create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var d0 = structure.getInt(0);
        var d1 = structure.getInt(1);

        return new FPos2DI(d0, d1);
    }

    public int getD0() {

        return d0;
    }

    public int getD1() {

        return d1;
    }

    public FPos2D toDouble() {

        return factory.getFPos2D(d0, d1);
    }

//--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_TAG);
        json.append(JSON_VAL, getD0());
        json.append(JSON_VAL, getD1());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + d0;
        hashCode = 31 * hashCode + d1;

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos2DI) {
            FPos2DI fPosition = (FPos2DI) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
