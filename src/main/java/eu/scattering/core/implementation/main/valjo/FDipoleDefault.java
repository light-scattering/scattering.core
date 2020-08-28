package eu.scattering.core.implementation.main.valjo;

import eu.scattering.core.design.main.valjo.FDipole;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FDipoleDefault implements FDipole {

    private final int x;
    private final int y;
    private final int z;

    private FDipoleDefault(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static FDipole create(int x, int y, int z) {

        return new FDipoleDefault(x, y, z);
    }

    public static FDipole parse(String json) {
        JSONArray structure = (new JSONObject(json)).getJSONArray("dipole");

        return new FDipoleDefault(structure.getInt(0), structure.getInt(1), structure.getInt(2));
    }

    @Override
    public int hashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + x;
        hashCode = 31 * hashCode + y;
        hashCode = 31 * hashCode + z;

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FDipole) {
            FDipole ref = (FDipole) object;

            return (x == ref.getPositionX()) && (y == ref.getPositionY()) && (z == ref.getPositionZ());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    @Override
    public int[] getPosition() {

        return new int[] {x, y, z};
    }

    @Override
    public int getPositionX() {

        return x;
    }

    @Override
    public int getPositionY() {

        return y;
    }

    @Override
    public int getPositionZ() {

        return z;
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append("dipole", getPositionX());
        json.append("dipole", getPositionY());
        json.append("dipole", getPositionZ());

        return json;
    }

}
