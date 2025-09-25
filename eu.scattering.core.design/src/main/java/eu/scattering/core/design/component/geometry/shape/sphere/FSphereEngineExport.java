package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.util.annotation.Legacy;

public interface FSphereEngineExport {

    @Legacy
    void exportFLAGE(FSphere shape, StringBuilder builder);

    String exportNGSolve(FSphere shape, StringBuilder builder);
}
