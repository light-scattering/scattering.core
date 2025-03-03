package eu.scattering.core.transfer.container.storage;

import eu.scattering.core.transfer.container.storage.FMatrix3x3D.FMatrix3x3DFactory;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2DFactory;
import eu.scattering.core.transfer.container.storage.FPairPos2DI.FPairPos2DIFactory;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3DFactory;
import eu.scattering.core.transfer.container.storage.FPairPos3DI.FPairPos3DIFactory;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4DFactory;
import eu.scattering.core.transfer.container.storage.FPairPos4DI.FPairPos4DIFactory;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2DFactory;
import eu.scattering.core.transfer.container.storage.FPos2DI.FPos2DIFactory;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3DFactory;
import eu.scattering.core.transfer.container.storage.FPos3DI.FPos3DIFactory;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4DFactory;
import eu.scattering.core.transfer.container.storage.FPos4DI.FPos4DIFactory;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQtFactory;

public interface StorageFactory extends FMatrix3x3DFactory, FRotQtFactory,
        FPos2DFactory, FPos3DFactory, FPos4DFactory,
        FPos2DIFactory, FPos3DIFactory, FPos4DIFactory,
        FPairPos2DFactory, FPairPos3DFactory, FPairPos4DFactory,
        FPairPos2DIFactory, FPairPos3DIFactory, FPairPos4DIFactory {
}
