module eu.scattering.core.design {
    requires org.json;
    requires eu.scattering.core.transfer;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.mutable.geometry;
    exports eu.scattering.core.design.mutable.geometry.primitive;
    exports eu.scattering.core.design.mutable.geometry.primitive.point;
    exports eu.scattering.core.design.mutable.geometry.primitive.vector;
    exports eu.scattering.core.design.mutable.geometry.construct;
    exports eu.scattering.core.design.mutable.geometry.construct.line;
    exports eu.scattering.core.design.mutable.geometry.construct.plane;
    exports eu.scattering.core.design.mutable.geometry.shape.sphere;
    exports eu.scattering.core.design.mutable.number.complex;
    exports eu.scattering.core.design.mutable.number.quaternion;
    exports eu.scattering.core.design.engine.rotate;
    exports eu.scattering.core.design.mutable.geometry.shape;
    exports eu.scattering.core.design.helper.auxiliary;
    exports eu.scattering.core.design.engine.randomize;
    exports eu.scattering.core.design.engine.randomize.processor;
    exports eu.scattering.core.design.engine.rotate.processor;
    exports eu.scattering.core.design.engine.randomize.processor.core;
    exports eu.scattering.core.design.mutable.geometry.construct.ray;
    exports eu.scattering.core.design.mutable.geometry.construct.segment;
    exports eu.scattering.core.design.mutable;
    exports eu.scattering.core.design.engine.prototype;
}