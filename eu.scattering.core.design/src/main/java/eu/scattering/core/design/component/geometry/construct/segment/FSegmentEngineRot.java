package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FSegmentEngineRot {

    void rotQtAround(Geometry in, FSegment ref, double angle);
    void rotRgAround(Geometry in, FSegment ref, double angle);
}
