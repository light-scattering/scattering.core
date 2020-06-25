package eu.scattering.core.helper;

import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.geometry.support.plane.IFPlane;

public final class HelperNull {

    private HelperNull() { }

    public static IFPoint getIFPoint() {

        return null;
    }

    public static IFVector getIFVector() {

        return null;
    }

    public static IFLine getIFLine() {

        return null;
    }

    public static IFPlane getIFPlane() {

        return null;
    }
}
