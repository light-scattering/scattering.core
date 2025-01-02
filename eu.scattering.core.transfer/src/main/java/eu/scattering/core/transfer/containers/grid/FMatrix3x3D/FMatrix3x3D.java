package eu.scattering.core.transfer.containers.grid.FMatrix3x3D;

import eu.scattering.core.transfer.containers.grid.Grid;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FMatrix3x3D implements Grid<FMatrix3x3D> {
    private static final String JSON_TAG = "matrix3x3D";
    private static final String JSON_VAL = "val";

    private final double[][] core;

    private FMatrix3x3D(double[][] core) {

        if (core == null) {
            throw new NullPointerException("The matrix is null");
        }

        if (core.length != 3 || core[0].length != 3) {
            throw new IllegalArgumentException("The matrix dimension must be 3x3");
        }

        this.core = copy(core);
    }

    protected static FMatrix3x3D create(double[][] core) {

        return new FMatrix3x3D(core);
    }

    protected static FMatrix3x3D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var ref = new double[3][3];

        JSONArray structure = json.getJSONArray(JSON_VAL);

        ref[0][0] = structure.getDouble(0);
        ref[0][1] = structure.getDouble(1);
        ref[0][2] = structure.getDouble(2);
        ref[1][0] = structure.getDouble(3);
        ref[1][1] = structure.getDouble(4);
        ref[1][2] = structure.getDouble(5);
        ref[2][0] = structure.getDouble(6);
        ref[2][1] = structure.getDouble(7);
        ref[2][2] = structure.getDouble(8);

        return create(ref);
    }

    public double get0x0() {
        return this.core[0][0];
    }

    public double get0x1() {
        return this.core[0][1];
    }

    public double get0x2() {
        return this.core[0][2];
    }

    public double get1x0() {
        return this.core[1][0];
    }

    public double get1x1() {
        return this.core[1][1];
    }

    public double get1x2() {
        return this.core[1][2];
    }

    public double get2x0() {
        return this.core[2][0];
    }

    public double get2x1() {
        return this.core[2][1];
    }

    public double get2x2() {
        return this.core[2][2];
    }

    public double[][] getArray() {
        return copy(this.core);
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_TAG);
        json.append(JSON_VAL, get0x0());
        json.append(JSON_VAL, get0x1());
        json.append(JSON_VAL, get0x2());
        json.append(JSON_VAL, get1x0());
        json.append(JSON_VAL, get1x1());
        json.append(JSON_VAL, get1x2());
        json.append(JSON_VAL, get2x0());
        json.append(JSON_VAL, get2x1());
        json.append(JSON_VAL, get2x2());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return Arrays.deepHashCode(this.core);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FMatrix3x3D) {
            FMatrix3x3D matrix = (FMatrix3x3D) object;

            var row0 = get0x0() == matrix.get0x0() && get0x1() == matrix.get0x1() && get0x2() == matrix.get0x2();
            var row1 = get1x0() == matrix.get1x0() && get1x1() == matrix.get1x1() && get1x2() == matrix.get1x2();
            var row2 = get2x0() == matrix.get2x0() && get2x1() == matrix.get2x1() && get2x2() == matrix.get2x2();

            return row0 && row1 && row2;
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    //--------------------------------------------------

    private double[][] copy(double[][] origin) {

        return Arrays.stream(origin)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }
}
