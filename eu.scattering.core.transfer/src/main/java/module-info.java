module eu.scattering.core.transfer {
    requires org.json;
    exports eu.scattering.core.transfer.containers;
    exports eu.scattering.core.transfer.containers.position;
    exports eu.scattering.core.transfer;
    exports eu.scattering.core.transfer.containers.position.FPairPos2D;
    exports eu.scattering.core.transfer.containers.position.FPairPos2DI;
    exports eu.scattering.core.transfer.containers.position.FPairPos3D;
    exports eu.scattering.core.transfer.containers.position.FPairPos3DI;
    exports eu.scattering.core.transfer.containers.position.FPairPos4D;
    exports eu.scattering.core.transfer.containers.position.FPairPos4DI;
    exports eu.scattering.core.transfer.containers.position.FPos2D;
    exports eu.scattering.core.transfer.containers.position.FPos2DI;
    exports eu.scattering.core.transfer.containers.position.FPos3D;
    exports eu.scattering.core.transfer.containers.position.FPos3DI;
    exports eu.scattering.core.transfer.containers.position.FPos4D;
    exports eu.scattering.core.transfer.containers.position.FPos4DI;
    exports eu.scattering.core.transfer.helpers.transfer;
    exports eu.scattering.core.transfer.containers.engine.FRot;
    exports eu.scattering.core.transfer.containers.grid.FMatrix3x3D;
}