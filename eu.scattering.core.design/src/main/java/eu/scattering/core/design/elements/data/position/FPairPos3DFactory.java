package eu.scattering.core.design.elements.data.position;

public interface FPairPos3DFactory {

    default FPairPos3D getFPairPos3D(FPos3D posA, FPos3D posB) {

        return FPairPos3D.create(posA, posB);
    }
}
