module eu.scattering.core.design {
    requires org.json;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.debug;
    exports eu.scattering.core.design.debug.stats;
    exports eu.scattering.core.design.support.helper;
    exports eu.scattering.core.design.core.mutable;
    exports eu.scattering.core.design.core.mutable.geometry;
    exports eu.scattering.core.design.core.mutable.geometry.simple;
    exports eu.scattering.core.design.core.mutable.geometry.simple.point;
    exports eu.scattering.core.design.core.mutable.geometry.simple.vector;
    exports eu.scattering.core.design.core.mutable.geometry.advanced;
    exports eu.scattering.core.design.core.mutable.geometry.advanced.line;
    exports eu.scattering.core.design.core.mutable.geometry.advanced.plane;
    exports eu.scattering.core.design.core.mutable.geometry.shape.sphere;
    exports eu.scattering.core.design.core.mutable.number.complex;
    exports eu.scattering.core.design.core.mutable.number.quaternion;
    exports eu.scattering.core.design.core.immutable.position;
    exports eu.scattering.core.design.core.immutable.rotation;
    exports eu.scattering.core.design.core.mutable.geometry.shape;
}