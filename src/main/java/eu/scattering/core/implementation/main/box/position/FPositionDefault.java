package eu.scattering.core.implementation.main.box.position;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.box.position.FPosition;
import eu.scattering.core.implementation.FactoryDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FPositionDefault implements FPosition {

    private static Factory factory = FactoryDefault.create();
    private static double jitter = 1E-8;

    public static void setFactory(Factory factory) {

        FPositionDefault.factory = factory;
    }

    public static void setJitter(double jitter) {

        FPositionDefault.jitter = jitter;
    }

    private final int x;
    private final int y;
    private final int z;

    private FPositionDefault(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static FPosition create(int x, int y, int z) {

        return new FPositionDefault(x, y, z);
    }

    public static FPosition parse(String json) {
        JSONArray structure = (new JSONObject(json)).getJSONArray("dipole");

        return new FPositionDefault(structure.getInt(0), structure.getInt(1), structure.getInt(2));
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
