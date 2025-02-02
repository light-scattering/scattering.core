module eu.scattering.core.design {
    requires org.json;
    requires eu.scattering.core.transfer;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.mutables.geometry;
    exports eu.scattering.core.design.mutables.geometry.primitive;
    exports eu.scattering.core.design.mutables.geometry.primitive.point;
    exports eu.scattering.core.design.mutables.geometry.primitive.vector;
    exports eu.scattering.core.design.mutables.geometry.construct;
    exports eu.scattering.core.design.mutables.geometry.construct.line;
    exports eu.scattering.core.design.mutables.geometry.construct.plane;
    exports eu.scattering.core.design.mutables.geometry.shape.sphere;
    exports eu.scattering.core.design.mutables.number.complex;
    exports eu.scattering.core.design.mutables.number.quaternion;
    exports eu.scattering.core.design.engines.rot;
    exports eu.scattering.core.design.mutables.geometry.shape;
    exports eu.scattering.core.design.helpers.auxiliary;
    exports eu.scattering.core.design.engines.rand;
    exports eu.scattering.core.design.engines.rand.processor;
    exports eu.scattering.core.design.engines.rot.processor;
    exports eu.scattering.core.design.engines.rand.processor.core;
    exports eu.scattering.core.design.mutables.geometry.construct.ray;
    exports eu.scattering.core.design.mutables.geometry.construct.segment;
    exports eu.scattering.core.design.mutables;
    exports eu.scattering.core.design.engines.prot;
}