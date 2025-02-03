package eu.scattering.core.transfer.helper.transfer;

import eu.scattering.core.transfer.container.position.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.position.FPairPos4D.FPairPos4D;

public interface FPositionHelper{

    FPairPos2D getFPairPos2DWithRange(double range);
    FPairPos3D getFPairPos3DWithRange(double range);
    FPairPos4D getFPairPos4DWithRange(double range);

    FPairPos2D getFPairPos2DWithRange(double rangeX, double rangeY);
    FPairPos3D getFPairPos3DWithRange(double rangeX, double rangeY, double rangeZ);
    FPairPos4D getFPairPos4DWithRange(double rangeX, double rangeY, double rangeZ, double rangeW);
}
