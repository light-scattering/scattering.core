package eu.scattering.core.design.elements.data.position;

public interface FPairPos2DFactory {

    default FPairPos2D getFPairPos2D(FPos2D posA, FPos2D posB) {

        return FPairPos2D.create(posA, posB);
    }
}
