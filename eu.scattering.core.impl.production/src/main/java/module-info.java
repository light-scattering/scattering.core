module eu.scattering.core.impl.production {
    requires transitive eu.scattering.core.design;
    requires transitive org.json;
    requires java.annotation;
    requires spring.context;
    requires spring.beans;
    exports eu.scattering.core.impl.production.development.statistics to eu.scattering.core.impl.development;
    exports eu.scattering.core.impl.production;

}