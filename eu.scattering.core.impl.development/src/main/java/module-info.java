module eu.scattering.core.impl.development {
    requires transitive eu.scattering.core.impl.production;
    requires java.annotation;
    requires spring.context;
    requires spring.beans;
    requires org.json;
    exports eu.scattering.core.impl.development;
    opens eu.scattering.core.impl.development;
}