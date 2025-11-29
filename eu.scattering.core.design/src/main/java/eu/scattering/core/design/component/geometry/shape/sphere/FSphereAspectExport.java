package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Legacy;

public interface FSphereAspectExport {

    @Legacy
    @Fragment
    void exportFLAGE(FSphere shape, StringBuilder builder);

    @Fragment
    String exportNGSolve(FSphere shape, StringBuilder builder);
}
