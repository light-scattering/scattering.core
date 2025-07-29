package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVectorHelper;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FVectorHelperDef implements FVectorHelper {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private FVectorHelperDef() {}

    public static FVectorHelper get() {

        return new FVectorHelperDef();
    }

    @Override
    public double getMagnitude(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return Math.sqrt(getMagnitudeP2(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public boolean isNearZeroLength(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        boolean posX = Math.abs(bX - hX) < EPSILON;
        boolean posY = Math.abs(bY - hY) < EPSILON;
        boolean posZ = Math.abs(bZ - hZ) < EPSILON;

        return posX && posY && posZ;
    }

    @Override
    public boolean isParallel(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double bX2, double bY2, double bZ2, double hX2, double hY2, double hZ2) {

        if (isNearZeroLength(bX1, bY1, bZ1, hX1, hY1, hZ1)) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX2, bY2, bZ2, hX2, hY2, hZ2)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double magO = getMagnitude(bX1, bY1, bZ1, hX1, hY1, hZ1);
        double headOX = (hX1 - bX1) / magO;
        double headOY = (hY1 - bY1) / magO;
        double headOZ = (hZ1 - bZ1) / magO;

        double magA = getMagnitude(bX2, bY2, bZ2, hX2, hY2, hZ2);
        double headAX = (hX2 - bX2) / magA;
        double headAY = (hY2 - bY2) / magA;
        double headAZ = (hZ2 - bZ2) / magA;

        double distX = Math.abs(headOX - headAX);
        double distY = Math.abs(headOY - headAY);
        double distZ = Math.abs(headOZ - headAZ);

        return distX < EPSILON && distY < EPSILON && distZ < EPSILON;
    }

    @Override
    public boolean isAntiParallel(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double bX2, double bY2, double bZ2, double hX2, double hY2, double hZ2) {


        if (isNearZeroLength(bX1, bY1, bZ1, hX1, hY1, hZ1)) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX2, bY2, bZ2, hX2, hY2, hZ2)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double magO = getMagnitude(bX1, bY1, bZ1, hX1, hY1, hZ1);
        double headOX = (hX1 - bX1) / magO;
        double headOY = (hY1 - bY1) / magO;
        double headOZ = (hZ1 - bZ1) / magO;

        double magA = getMagnitude(bX2, bY2, bZ2, hX2, hY2, hZ2);
        double headAX = (hX2 - bX2) / magA;
        double headAY = (hY2 - bY2) / magA;
        double headAZ = (hZ2 - bZ2) / magA;

        double distX = Math.abs(headOX + headAX);
        double distY = Math.abs(headOY + headAY);
        double distZ = Math.abs(headOZ + headAZ);

        return distX < EPSILON && distY < EPSILON && distZ < EPSILON;
    }

    @Override
    public boolean isCollinear(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double bX2, double bY2, double bZ2, double hX2, double hY2, double hZ2) {

        return isParallel(bX1, bY1, bZ1, hX1, hY1, hZ1, bX2, bY2, bZ2, hX2, hY2, hZ2) ||
                isAntiParallel(bX1, bY1, bZ1, hX1, hY1, hZ1, bX2, bY2, bZ2, hX2, hY2, hZ2);
    }

    @Override
    public boolean isCollinearBaseCommon(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double hX2, double hY2, double hZ2) {

        return isCollinear(bX1, bY1, bZ1, hX1, hY1, hZ1, bX1, bY1, bZ1, hX2, hY2, hZ2);
    }

    @Override
    public boolean isCollinearBaseCommon(double bX1, double bY1, double bZ1, FPos3D h1, FPos3D h2) {

        return isCollinearBaseCommon(
                bX1, bY1, bZ1, h1.getD0(), h1.getD1(), h1.getD2(),
                h2.getD0(), h2.getD1(), h2.getD2()
        );
    }

    //--------------------------------------------------

    @Override
    public double getMagnitudeP2(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double distX = hX - bX;
        double distY = hY - bY;
        double distZ = hZ - bZ;

        return (distX * distX) + (distY * distY) + (distZ * distZ);
    }
}
