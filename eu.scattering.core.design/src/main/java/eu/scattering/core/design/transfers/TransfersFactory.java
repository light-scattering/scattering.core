package eu.scattering.core.design.transfers;

import eu.scattering.core.design.transfers.position.*;

public interface TransfersFactory extends
        FPos2DFactory, FPos3DFactory, FPos4DFactory,
        FPos2DIFactory, FPos3DIFactory, FPos4DIFactory,
        FPairPos2DFactory, FPairPos3DFactory, FPairPos4DFactory,
        FPairPos2DIFactory, FPairPos3DIFactory, FPairPos4DIFactory {
}
