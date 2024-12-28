module eu.scattering.core.impl.production {
    requires transitive eu.scattering.core.design;
    requires org.json;
    requires java.annotation;
    requires spring.context;
    requires spring.beans;
    requires eu.scattering.core.transfer;
    exports eu.scattering.core.impl.production;
    opens eu.scattering.core.impl.production;
}