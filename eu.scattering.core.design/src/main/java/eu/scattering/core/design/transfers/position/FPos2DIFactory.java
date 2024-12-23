package eu.scattering.core.design.transfers.position;

public interface FPos2DIFactory {

    default FPos2DI getFPos2DI(int d0, int d1) {

        return FPos2DI.create(d0, d1);
    }
}
