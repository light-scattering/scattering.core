package eu.scattering.core.design.mutable.geometry;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;

import java.util.List;

public interface Geometry {

    @Fragment
    List<FPoint> disassemble();
}
