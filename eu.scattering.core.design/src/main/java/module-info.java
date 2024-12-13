module eu.scattering.core.design {
    requires org.json;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.elements.data;
    exports eu.scattering.core.design.elements.algebra;
    exports eu.scattering.core.design.elements.algebra.geometry;
    exports eu.scattering.core.design.elements.algebra.geometry.primitive;
    exports eu.scattering.core.design.elements.algebra.geometry.primitive.point;
    exports eu.scattering.core.design.elements.algebra.geometry.primitive.vector;
    exports eu.scattering.core.design.elements.algebra.geometry.construct;
    exports eu.scattering.core.design.elements.algebra.geometry.construct.line;
    exports eu.scattering.core.design.elements.algebra.geometry.construct.plane;
    exports eu.scattering.core.design.elements.algebra.geometry.shape.sphere;
    exports eu.scattering.core.design.elements.algebra.number.complex;
    exports eu.scattering.core.design.elements.algebra.number.quaternion;
    exports eu.scattering.core.design.elements.engine.rotation;
    exports eu.scattering.core.design.elements.algebra.geometry.shape;
    exports eu.scattering.core.design.helpers.angle;
    exports eu.scattering.core.design.helpers.random;
    exports eu.scattering.core.design.elements.data.position;
    exports eu.scattering.core.design.elements.engine.random;
}