module eu.scattering.core.transfer {
    requires org.json;
    exports eu.scattering.core.transfer.container;
    exports eu.scattering.core.transfer;
    exports eu.scattering.core.transfer.container.storage.FPairPos2D;
    exports eu.scattering.core.transfer.container.storage.FPairPos2DI;
    exports eu.scattering.core.transfer.container.storage.FPairPos3D;
    exports eu.scattering.core.transfer.container.storage.FPairPos3DI;
    exports eu.scattering.core.transfer.container.storage.FPairPos4D;
    exports eu.scattering.core.transfer.container.storage.FPairPos4DI;
    exports eu.scattering.core.transfer.container.storage.FPos2D;
    exports eu.scattering.core.transfer.container.storage.FPos2DI;
    exports eu.scattering.core.transfer.container.storage.FPos3D;
    exports eu.scattering.core.transfer.container.storage.FPos3DI;
    exports eu.scattering.core.transfer.container.storage.FPos4D;
    exports eu.scattering.core.transfer.container.storage.FPos4DI;
    exports eu.scattering.core.transfer.helper.transfer;
    exports eu.scattering.core.transfer.container.storage.FRotQt;
    exports eu.scattering.core.transfer.container.storage.FMatrix3x3D;
    exports eu.scattering.core.transfer.container.buffer.FStream3D;
    exports eu.scattering.core.transfer.container.buffer.FStream3DI;
}