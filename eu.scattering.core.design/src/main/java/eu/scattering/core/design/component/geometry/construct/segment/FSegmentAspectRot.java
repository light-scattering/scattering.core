package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FSegmentAspectRot {

    void rotRgAround(Geometry in, FSegment ref, double angle);
    void rotQtAround(Geometry in, FSegment ref, double angle);
}
