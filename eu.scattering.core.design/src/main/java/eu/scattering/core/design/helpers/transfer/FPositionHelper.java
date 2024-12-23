package eu.scattering.core.design.helpers.transfer;

import eu.scattering.core.design.transfers.position.FPairPos2D;
import eu.scattering.core.design.transfers.position.FPairPos3D;

public interface FPositionHelper {

    FPairPos2D getFPairPos2DWithRange(double range);
    FPairPos3D getFPairPos3DWithRange(double range);

    FPairPos2D getFPairPos2DWithRange(double rangeX, double rangeY);
    FPairPos3D getFPairPos3DWithRange(double rangeX, double rangeY, double rangeZ);
}
