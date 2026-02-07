package eu.scattering.core.impl.helper;

import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos3D;

public class FTrigHelperDef implements FTrigHelper {
    private final TransferFactory factoryExt;

    private FTrigHelperDef(TransferFactory factoryExt) {

        this.factoryExt = factoryExt;
    }

    public static FTrigHelper create(TransferFactory factoryExt) {

        return new FTrigHelperDef(factoryExt);
    }

    @Override
    public double convertRadToDeg(double radian) {

        return radian * 180 / Math.PI;
    }

    @Override
    public double convertDegToRad(double degree) {

        return degree * Math.PI / 180;
    }

    @Override
    public double getAngleBetweenVectors(FPos3D base, FPos3D headA, FPos3D headB) {
        var parsedHeadA = sub(headA, base);
        var parsedHeadB = sub(headB, base);

        var dotProduct = getDotProduct(parsedHeadA, parsedHeadB);
        var magAB = getLength(parsedHeadA) * getLength(parsedHeadB);

        return Math.acos(dotProduct / magAB);
    }

    @Override
    public double getAngle(double adjA, double adjB, double oppC) {

        return Math.acos(((adjA * adjA) + (adjB * adjB) - (oppC * oppC)) / (2 * adjA * adjB));
    }

    @Override
    public boolean isValid(double sideA, double sideB, double sideC) {

        return !(sideA + sideB < sideC) && !(sideA + sideC < sideB) && !(sideB + sideC < sideA);
    }

    //--------------------------------------------------

    private FPos3D sub(FPos3D origin, FPos3D ref) {
        var d0 = origin.getD0() - ref.getD0();
        var d1 = origin.getD1() - ref.getD1();
        var d2 = origin.getD2() - ref.getD2();

        return factoryExt.getFPos3D(d0, d1, d2);
    }

    private double getLength(FPos3D ref) {
        var d0 = ref.getD0() * ref.getD0();
        var d1 = ref.getD1() * ref.getD1();
        var d2 = ref.getD2() * ref.getD2();

        return Math.sqrt(d0 + d1 + d2);
    }

    private double getDotProduct(FPos3D posA, FPos3D posB) {
        var dimX = posA.getD0() * posB.getD0();
        var dimY = posA.getD1() * posB.getD1();
        var dimZ = posA.getD2() * posB.getD2();

        return dimX + dimY + dimZ;
    }
}
