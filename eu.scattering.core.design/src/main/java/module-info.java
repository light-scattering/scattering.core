module eu.scattering.core.design {
    requires org.json;
    requires eu.scattering.core.transfer;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.component.geometry;
    exports eu.scattering.core.design.component.geometry.base;
    exports eu.scattering.core.design.component.geometry.base.point;
    exports eu.scattering.core.design.component.geometry.base.vector;
    exports eu.scattering.core.design.component.geometry.construct;
    exports eu.scattering.core.design.component.geometry.construct.line;
    exports eu.scattering.core.design.component.geometry.construct.plane;
    exports eu.scattering.core.design.component.geometry.shape.sphere;
    exports eu.scattering.core.design.component.number.complex;
    exports eu.scattering.core.design.component.number.quaternion;
    exports eu.scattering.core.design.engine.rotate;
    exports eu.scattering.core.design.component.geometry.shape;
    exports eu.scattering.core.design.helper.trigonometry;
    exports eu.scattering.core.design.engine.randomize;
    exports eu.scattering.core.design.engine.randomize.generator;
    exports eu.scattering.core.design.engine.rotate.generator;
    exports eu.scattering.core.design.engine.randomize.generator.core;
    exports eu.scattering.core.design.component.geometry.construct.ray;
    exports eu.scattering.core.design.component.geometry.construct.segment;
    exports eu.scattering.core.design.component;
    exports eu.scattering.core.design.engine.prototype;
    exports eu.scattering.core.design.helper.statistics;
}