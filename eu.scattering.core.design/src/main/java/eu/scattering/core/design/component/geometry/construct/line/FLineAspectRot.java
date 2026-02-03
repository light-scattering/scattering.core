package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FLineAspectRot {

    void rotRgAround(Geometry in, FLine ref, double angle);
    void rotQtAround(Geometry in, FLine ref, double angle);
}
