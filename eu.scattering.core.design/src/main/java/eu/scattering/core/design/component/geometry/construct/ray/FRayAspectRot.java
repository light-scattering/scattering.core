package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FRayAspectRot {

    void aroundRg(Geometry in, FRay ref, double angle);
    void aroundQt(Geometry in, FRay ref, double angle);
}
