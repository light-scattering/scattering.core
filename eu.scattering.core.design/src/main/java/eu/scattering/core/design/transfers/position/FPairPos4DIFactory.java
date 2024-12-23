package eu.scattering.core.design.transfers.position;

public interface FPairPos4DIFactory {

    default FPairPos4DI getFPairPos4DI(FPos4DI posA, FPos4DI posB) {

        return FPairPos4DI.create(posA, posB);
    }
}
