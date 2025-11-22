package eu.scattering.core.design.transfer.primitive;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FPoly implements Transfer {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "poly";
    private static final String JSON_VAL = "val";

    private final double[] core;

    private FPoly(double[] val) {

        this.core = val;
    }

    protected static FPoly create(double... core) {

        return new FPoly(core);
    }

    protected static FPoly create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);

        List<Double> array = new ArrayList<>();

        for (int i = 0; i < structure.length(); i++) {
            array.add(structure.getDouble(i));
        }

        return new FPoly(array.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public int size() {

        return this.core.length;
    }

    public double getValue(double x) {
        double value = 0;

        for (int i = 0 ; i < this.core.length ; i++) {
            value += this.core[i] * Math.pow(x, i);
        }

        return value;
    }

    public double getCore0() {

        if (size() < 1) {
            throw new IllegalArgumentException("The index is erroneous");
        }

        return core[0];
    }

    public double getCore1() {

        if (size() < 2) {
            throw new IllegalArgumentException("The index is erroneous");
        }

        return core[1];
    }

    public double getCoreN(int n) {

        if (n < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (size() <= n) {
            throw new IllegalArgumentException("The index is erroneous");
        }

        return core[n];
    }

    @Modificator
    public double[] getRefCore() {

        return this.core;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        for (double v : this.core) {
            json.append(JSON_VAL, v);
        }

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return Arrays.hashCode(this.core);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPoly fPoly) {

            if (this.core.length != fPoly.getRefCore().length) {
                return false;
            }

            for (int i = 0 ; i < this.core.length ; i++) {
                if (this.core[i] != fPoly.getRefCore()[i]) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
