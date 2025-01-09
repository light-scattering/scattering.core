package eu.scattering.core.impl.helpers;

import eu.scattering.core.design.helpers.auxiliary.FTrigHelper;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public class FTrigHelperDef implements FTrigHelper {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private FTrigHelperDef() { }

    public static FTrigHelper create() {

        return new FTrigHelperDef();
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

    //--------------------------------------------------

    private FPos3D sub(FPos3D origin, FPos3D ref) {
        var d0 = origin.getD0() - ref.getD0();
        var d1 = origin.getD1() - ref.getD1();
        var d2 = origin.getD2() - ref.getD2();

        return factory.getFPos3D(d0, d1, d2);
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
