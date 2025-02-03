module eu.scattering.core.transfer {
    requires org.json;
    exports eu.scattering.core.transfer.container;
    exports eu.scattering.core.transfer.container.position;
    exports eu.scattering.core.transfer;
    exports eu.scattering.core.transfer.container.position.FPairPos2D;
    exports eu.scattering.core.transfer.container.position.FPairPos2DI;
    exports eu.scattering.core.transfer.container.position.FPairPos3D;
    exports eu.scattering.core.transfer.container.position.FPairPos3DI;
    exports eu.scattering.core.transfer.container.position.FPairPos4D;
    exports eu.scattering.core.transfer.container.position.FPairPos4DI;
    exports eu.scattering.core.transfer.container.position.FPos2D;
    exports eu.scattering.core.transfer.container.position.FPos2DI;
    exports eu.scattering.core.transfer.container.position.FPos3D;
    exports eu.scattering.core.transfer.container.position.FPos3DI;
    exports eu.scattering.core.transfer.container.position.FPos4D;
    exports eu.scattering.core.transfer.container.position.FPos4DI;
    exports eu.scattering.core.transfer.helper.transfer;
    exports eu.scattering.core.transfer.container.engine.FRotQt;
    exports eu.scattering.core.transfer.container.storage.FMatrix3x3D;
}