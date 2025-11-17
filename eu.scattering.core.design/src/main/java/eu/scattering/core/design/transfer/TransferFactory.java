package eu.scattering.core.design.transfer;

import eu.scattering.core.design.transfer.box.FBoxDoubleFactory;
import eu.scattering.core.design.transfer.box.FBoxStringFactory;
import eu.scattering.core.design.transfer.primitive.*;

public interface TransferFactory extends FPolyFactory,
        FMatrix3x3DFactory,
        FBoxDoubleFactory, FBoxStringFactory,
        FPos2DFactory, FPos3DFactory, FPos4DFactory,
        FPos2DIFactory, FPos3DIFactory, FPos4DIFactory,
        FPairPos2DFactory, FPairPos3DFactory, FPairPos4DFactory,
        FPairPos2DIFactory, FPairPos3DIFactory, FPairPos4DIFactory {
}
