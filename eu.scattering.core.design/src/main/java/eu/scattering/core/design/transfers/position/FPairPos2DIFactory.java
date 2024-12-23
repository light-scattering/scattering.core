package eu.scattering.core.design.transfers.position;

public interface FPairPos2DIFactory {

    default FPairPos2DI getFPairPos2DI(FPos2DI posA, FPos2DI posB) {

        return FPairPos2DI.create(posA, posB);
    }
}
