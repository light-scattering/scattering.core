package eu.scattering.core.transfer.container.storage.FPos4D;

import eu.scattering.core.transfer.container.storage.Storage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FPos4D implements Storage<FPos4D> {
    private static final String JSON_MAIN = "pos4D";
    private static final String JSON_VAL = "val";

    private final double d0;
    private final double d1;
    private final double d2;
    private final double d3;

    private FPos4D(double d0, double d1, double d2, double d3) {

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    protected static FPos4D create(double d0, double d1, double d2, double d3) {

        return new FPos4D(d0, d1, d2, d3);
    }

    protected static FPos4D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        double d0 = structure.getDouble(0);
        double d1 = structure.getDouble(1);
        double d2 = structure.getDouble(2);
        double d3 = structure.getDouble(3);

        return new FPos4D(d0, d1, d2, d3);
    }

    public double getD0() {

        return d0;
    }

    public double getD1() {

        return d1;
    }

    public double getD2() {

        return d2;
    }

    public double getD3() {

        return d3;
    }

//--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
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

        if (object instanceof FPos4D) {
            FPos4D fPosition = (FPos4D) object;

            return d0 == fPosition.getD0() && d1 == fPosition.getD1() && d2 == fPosition.getD2() && d3 == fPosition.getD3();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
