package eu.scattering.core.implementation.main.valjo;

import eu.scattering.core.design.main.valjo.FDipole;

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

    public static FDipole parse(String dipole) {

        String[] pos = dipole.split(",");

        return new FDipoleDefault(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(pos[2]));
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

        return x + "," + y + "," + z;
    }

    @Override
    public Object clone() {

        throw new UnsupportedOperationException("The clone method is not implemented");
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

}
