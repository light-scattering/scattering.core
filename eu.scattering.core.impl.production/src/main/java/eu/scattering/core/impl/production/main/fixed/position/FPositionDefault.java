package eu.scattering.core.impl.production.main.fixed.position;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.fixed.position.FPosition;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FPositionDefault implements FPosition {

    private final int x;
    private final int y;
    private final int z;
    private final Factory factory;

    private FPositionDefault(Factory factory, int x, int y, int z) {

        this.factory = factory;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static FPosition create(Factory factory, int x, int y, int z) {

        return new FPositionDefault(factory, x, y, z);
    }

    public static FPosition parse(Factory factory, String json) {
        JSONArray structure = (new JSONObject(json)).getJSONArray("dipole");

        int x = structure.getInt(0);
        int y = structure.getInt(1);
        int z = structure.getInt(2);

        return new FPositionDefault(factory, x, y, z);
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

        if (object instanceof FPosition) {
            FPosition fPosition = (FPosition) object;

            return x == fPosition.getX() && y == fPosition.getY() && z == fPosition.getZ();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    @Override
    public int[] get() {

        return new int[] {x, y, z};
    }

    @Override
    public int getX() {

        return x;
    }

    @Override
    public int getY() {

        return y;
    }

    @Override
    public int getZ() {

        return z;
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append("dipole", getX());
        json.append("dipole", getY());
        json.append("dipole", getZ());

        return json;
    }

}
