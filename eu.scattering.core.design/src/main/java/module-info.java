module eu.scattering.core.design {
    requires org.json;
    exports eu.scattering.core.design.main.mutable.geometry.base.point;
    exports eu.scattering.core.design.main.mutable.geometry.base.vector;
    exports eu.scattering.core.design.main.mutable.geometry.extension.line;
    exports eu.scattering.core.design.main.mutable.geometry.extension.plane;
    exports eu.scattering.core.design.main.mutable.geometry.shape.ball;
    exports eu.scattering.core.design.main.mutable.number.complex;
    exports eu.scattering.core.design.main.mutable.number.quaternion;
    exports eu.scattering.core.design.main.fixed.position;
    exports eu.scattering.core.design.main.fixed.rotation;
    exports eu.scattering.core.design.support.helper;
    exports eu.scattering.core.design;
    exports eu.scattering.core.design.development.statistics;
    exports eu.scattering.core.design.main.mutable.geometry;
    exports eu.scattering.core.design.main.mutable.geometry.base;
    exports eu.scattering.core.design.main.mutable.geometry.extension;
    exports eu.scattering.core.design.main.mutable;
}