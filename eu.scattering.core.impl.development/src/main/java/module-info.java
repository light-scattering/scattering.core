module eu.scattering.core.impl.development {
    requires transitive eu.scattering.core.impl.production;
    requires java.annotation;
    requires spring.context;
    requires spring.beans;
    exports eu.scattering.core.impl.development;
}