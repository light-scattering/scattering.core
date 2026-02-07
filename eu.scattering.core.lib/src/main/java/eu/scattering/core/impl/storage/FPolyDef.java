package eu.scattering.core.impl.storage;

import eu.scattering.core.design.storage.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.annotation.Modificator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FPolyDef implements FPoly {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "poly";
    private static final String JSON_VAL = "val";

    private final double[] core;

    private FPolyDef(double[] val) {

        this.core = val;
    }

    public static FPolyDef create(double... core) {

        return new FPolyDef(core);
    }

    public static FPolyDef create(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);

        List<Double> array = new ArrayList<>();

        for (int i = 0; i < structure.length(); i++) {
            array.add(structure.getDouble(i));
        }

        return new FPolyDef(array.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public int size() {

        return this.core.length;
    }

    public double at(int n) {

        return core[n];
    }

    public double value(double x) {
        double value = 0;

        for (int i = 0 ; i < this.core.length ; i++) {
            value += this.core[i] * Math.pow(x, i);
        }

        return value;
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

        if (object instanceof FPolyDef fPoly) {

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
