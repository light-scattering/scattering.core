package eu.scattering.core.design.transfers.position;

public interface FPairPos4DFactory {

    default FPairPos4D getFPairPos4D(FPos4D posA, FPos4D posB) {

        return FPairPos4D.create(posA, posB);
    }
}
