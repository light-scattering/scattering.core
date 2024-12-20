package eu.scattering.core.design.elements.data.position;

public interface FPos2DFactory {

    default FPos2D getFPos2D(double d0, double d1) {

        return FPos2D.create(d0, d1);
    }
}
