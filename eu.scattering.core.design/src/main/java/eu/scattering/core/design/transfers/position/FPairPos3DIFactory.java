package eu.scattering.core.design.transfers.position;

public interface FPairPos3DIFactory {

    default FPairPos3DI getFPairPos3DI(FPos3DI posA, FPos3DI posB) {

        return FPairPos3DI.create(posA, posB);
    }
}
