package eu.scattering.core;

import eu.scattering.core.geometry.d0.IFPoint;
import eu.scattering.core.geometry.d0.impl.FPoint;

public class GeometryFactory {

    private GeometryFactory() { }

    public static IFPoint getIFPoint() {
        return FPoint.create();
    }
}
