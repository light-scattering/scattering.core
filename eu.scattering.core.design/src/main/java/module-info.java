module eu.scattering.core.design {
    requires org.json;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.debug;
    exports eu.scattering.core.design.debug.stats;
    exports eu.scattering.core.design.core.algebra;
    exports eu.scattering.core.design.core.algebra.geometry;
    exports eu.scattering.core.design.core.algebra.geometry.primitive;
    exports eu.scattering.core.design.core.algebra.geometry.primitive.point;
    exports eu.scattering.core.design.core.algebra.geometry.primitive.vector;
    exports eu.scattering.core.design.core.algebra.geometry.construct;
    exports eu.scattering.core.design.core.algebra.geometry.construct.line;
    exports eu.scattering.core.design.core.algebra.geometry.construct.plane;
    exports eu.scattering.core.design.core.algebra.geometry.shape.sphere;
    exports eu.scattering.core.design.core.algebra.number.complex;
    exports eu.scattering.core.design.core.algebra.number.quaternion;
    exports eu.scattering.core.design.core.data.pos3DI;
    exports eu.scattering.core.design.core.engine.rotation;
    exports eu.scattering.core.design.core.algebra.geometry.shape;
    exports eu.scattering.core.design.core.data;
    exports eu.scattering.core.design.helper.angle;
    exports eu.scattering.core.design.helper.random;
}