package eu.scattering.core.design.component.geometry.construct.segment;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FSegmentAspectRot {

    void aroundRg(Geometry in, FSegment ref, double angle);
    void aroundQt(Geometry in, FSegment ref, double angle);
}
