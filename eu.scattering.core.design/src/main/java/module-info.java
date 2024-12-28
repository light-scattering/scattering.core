module eu.scattering.core.design {
    requires org.json;
    requires eu.scattering.core.transfer;
    exports eu.scattering.core.design;
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
    exports eu.scattering.core.design.helpers.auxiliary;
    exports eu.scattering.core.design.elements.engine.random;
    exports eu.scattering.core.design.helpers.engine;
}