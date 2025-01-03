module eu.scattering.core.design {
    requires org.json;
    requires eu.scattering.core.transfer;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.mutables.algebra;
    exports eu.scattering.core.design.mutables.algebra.geometry;
    exports eu.scattering.core.design.mutables.algebra.geometry.primitive;
    exports eu.scattering.core.design.mutables.algebra.geometry.primitive.point;
    exports eu.scattering.core.design.mutables.algebra.geometry.primitive.vector;
    exports eu.scattering.core.design.mutables.algebra.geometry.construct;
    exports eu.scattering.core.design.mutables.algebra.geometry.construct.line;
    exports eu.scattering.core.design.mutables.algebra.geometry.construct.plane;
    exports eu.scattering.core.design.mutables.algebra.geometry.shape.sphere;
    exports eu.scattering.core.design.mutables.algebra.number.complex;
    exports eu.scattering.core.design.mutables.algebra.number.quaternion;
    exports eu.scattering.core.design.mutables.engine.rotation;
    exports eu.scattering.core.design.mutables.algebra.geometry.shape;
    exports eu.scattering.core.design.helpers.auxiliary;
    exports eu.scattering.core.design.mutables.engine.random;
    exports eu.scattering.core.design.helpers.engine;
}