module eu.scattering.core.impl.production {
    requires transitive eu.scattering.core.design;
    requires org.json;
    requires java.annotation;
    requires spring.context;
    requires spring.beans;
    exports eu.scattering.core.impl.production.debug.stats;
    exports eu.scattering.core.impl.production;
    opens eu.scattering.core.impl.production;
}