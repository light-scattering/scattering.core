module eu.scattering.core.impl.production {
    requires transitive eu.scattering.core.design;
    requires java.annotation;
    requires spring.context;
    requires spring.beans;
    requires org.json;
//    exports eu.scattering.core.impl.production.main.mutable.geometry.base.point;
//    exports eu.scattering.core.impl.production.main.mutable.geometry.base.vector;
//    exports eu.scattering.core.impl.production.main.mutable.geometry.extension.line;
//    exports eu.scattering.core.impl.production.main.mutable.geometry.extension.plane;
//    exports eu.scattering.core.impl.production.main.mutable.geometry.shape.ball;
//    exports eu.scattering.core.impl.production.main.mutable.number.complex;
//    exports eu.scattering.core.impl.production.main.mutable.number.quaternion;
//    exports eu.scattering.core.impl.production.main.fixed.position;
//    exports eu.scattering.core.impl.production.main.fixed.rotation;
//    exports eu.scattering.core.impl.production.support.helper;
    exports eu.scattering.core.impl.production;
}