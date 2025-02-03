package eu.scattering.core.transfer.container.position;

import eu.scattering.core.transfer.container.position.FPairPos2D.FPairPos2DFactory;
import eu.scattering.core.transfer.container.position.FPairPos2DI.FPairPos2DIFactory;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3DFactory;
import eu.scattering.core.transfer.container.position.FPairPos3DI.FPairPos3DIFactory;
import eu.scattering.core.transfer.container.position.FPairPos4D.FPairPos4DFactory;
import eu.scattering.core.transfer.container.position.FPairPos4DI.FPairPos4DIFactory;
import eu.scattering.core.transfer.container.position.FPos2D.FPos2DFactory;
import eu.scattering.core.transfer.container.position.FPos2DI.FPos2DIFactory;
import eu.scattering.core.transfer.container.position.FPos3D.FPos3DFactory;
import eu.scattering.core.transfer.container.position.FPos3DI.FPos3DIFactory;
import eu.scattering.core.transfer.container.position.FPos4D.FPos4DFactory;
import eu.scattering.core.transfer.container.position.FPos4DI.FPos4DIFactory;

public interface PositionFactory extends
        FPos2DFactory, FPos3DFactory, FPos4DFactory,
        FPos2DIFactory, FPos3DIFactory, FPos4DIFactory,
        FPairPos2DFactory, FPairPos3DFactory, FPairPos4DFactory,
        FPairPos2DIFactory, FPairPos3DIFactory, FPairPos4DIFactory {
}
