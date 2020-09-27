module eu.scattering.core.design {
    requires org.json;
    exports eu.scattering.core.test.design.main.mutable.geometry.base.point;
    exports eu.scattering.core.test.design.main.mutable.geometry.base.vector;
    exports eu.scattering.core.test.design.main.mutable.geometry.extension.line;
    exports eu.scattering.core.test.design.main.mutable.geometry.extension.plane;
    exports eu.scattering.core.test.design.main.mutable.geometry.shape.ball;
    exports eu.scattering.core.test.design.main.mutable.number.complex;
    exports eu.scattering.core.test.design.main.mutable.number.quaternion;
    exports eu.scattering.core.test.design.main.fixed.position;
    exports eu.scattering.core.test.design.main.fixed.rotation;
    exports eu.scattering.core.test.design.support.helper;
    exports eu.scattering.core.test.design;
    exports eu.scattering.core.test.design.development.statistics;
    exports eu.scattering.core.test.design.main.mutable.geometry;
    exports eu.scattering.core.test.design.main.mutable.geometry.base;
    exports eu.scattering.core.test.design.main.mutable.geometry.extension;
    exports eu.scattering.core.test.design.main.mutable;
    exports eu.scattering.core.test.design.development;
}