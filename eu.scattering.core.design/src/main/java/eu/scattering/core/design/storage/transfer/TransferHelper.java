package eu.scattering.core.design.storage.transfer;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;

public interface TransferHelper {

    FPairPos2D getFPairPos2DWithRange(double range);
    FPairPos3D getFPairPos3DWithRange(double range);
    FPairPos4D getFPairPos4DWithRange(double range);

    FPairPos2D getFPairPos2DWithRange(double rangeX, double rangeY);
    FPairPos3D getFPairPos3DWithRange(double rangeX, double rangeY, double rangeZ);
    FPairPos4D getFPairPos4DWithRange(double rangeX, double rangeY, double rangeZ, double rangeW);
}
