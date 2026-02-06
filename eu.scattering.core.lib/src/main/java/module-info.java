module eu.scattering.core.impl.def {
    requires transitive eu.scattering.core.design;
    requires org.json;
    requires java.annotation;
    exports eu.scattering.core.impl;
    opens eu.scattering.core.impl;
}