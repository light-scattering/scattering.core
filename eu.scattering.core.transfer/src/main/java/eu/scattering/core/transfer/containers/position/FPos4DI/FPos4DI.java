package eu.scattering.core.transfer.containers.position.FPos4DI;

import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import eu.scattering.core.transfer.containers.position.Position;
import eu.scattering.core.transfer.containers.position.PositionFactory;
import eu.scattering.core.transfer.containers.position.PositionFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FPos4DI implements Position<FPos4DI> {
    private static PositionFactory factory = PositionFactoryConcrete.create();
    private static final String JSON_TAG = "pos4DI";
    private static final String JSON_VAL = "val";

    private final int d0;
    private final int d1;
    private final int d2;
    private final int d3;

    private FPos4DI(int d0, int d1, int d2, int d3) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    protected static FPos4DI create(int d0, int d1, int d2, int d3) {

        return new FPos4DI(d0, d1, d2, d3);
    }

    protected static FPos4DI create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var d0 = structure.getInt(0);
        var d1 = structure.getInt(1);
        var d2 = structure.getInt(2);
        var d3 = structure.getInt(3);


        return new FPos4DI(d0, d1, d2, d3);
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

    public int getD3() {

        return d3;
    }

    public FPos4D toDouble() {

        return factory.getFPos4D(d0, d1, d2, d3);
    }

//--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_TAG);
        json.append(JSON_VAL, getD0());
        json.append(JSON_VAL, getD1());
        json.append(JSON_VAL, getD2());
        json.append(JSON_VAL, getD3());

        return json;
    }

//--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(d0, d1, d2, d3);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPos4DI) {
            FPos4DI fPosition = (FPos4DI) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2() && d3 == fPosition.getD3();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
