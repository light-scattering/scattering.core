package eu.scattering.core.design.transfers;

import eu.scattering.core.design.transfers.position.*;

public interface TransfersFactory extends
        FPos2DIFactory, FPos3DIFactory, FPos4DIFactory,
        FPos2DFactory, FPos3DFactory, FPos4DFactory,
        FPairPos2DFactory, FPairPos2DIFactory,
        FPairPos3DFactory, FPairPos3DIFactory{
}
