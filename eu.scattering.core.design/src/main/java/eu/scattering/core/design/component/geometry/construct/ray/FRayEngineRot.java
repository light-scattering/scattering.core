package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FRayEngineRot {

    void rotQtAround(Geometry in, FRay ref, double angle);
    void rotRgAround(Geometry in, FRay ref, double angle);
}
