package eu.scattering.core.design.mutables.geometry;

import eu.scattering.core.design.annotations.Fragment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;

import java.util.List;

public interface Geometry {

    @Fragment
    List<FPoint> disassemble();
}
