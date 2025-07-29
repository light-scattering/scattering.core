package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface FVectorHelper {

    double getMagnitude(double bX, double bY, double bZ, double hX, double hY, double hZ);

    boolean isNearZeroLength(double bX, double bY, double bZ, double hX, double hY, double hZ);

    boolean isParallel(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double bX2, double bY2, double bZ2, double hX2, double hY2, double hZ2
    );

    boolean isAntiParallel(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double bX2, double bY2, double bZ2, double hX2, double hY2, double hZ2
    );

    boolean isCollinear(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double bX2, double bY2, double bZ2, double hX2, double hY2, double hZ2
    );

    boolean isCollinearBaseCommon(
            double bX1, double bY1, double bZ1, double hX1, double hY1, double hZ1,
            double hX2, double hY2, double hZ2
    );

    boolean isCollinearBaseCommon(double bX1, double bY1, double bZ1, FPos3D h1, FPos3D h2);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2(double bX, double bY, double bZ, double hX, double hY, double hZ);
}
