module eu.scattering.core.test {
    requires eu.scattering.core.impl.production;
    requires org.junit.jupiter.api;
    requires org.assertj.core;
    opens eu.scattering.core.test.main.mutable.geometry.base;
    opens eu.scattering.core.test.main.mutable.geometry.extension;
    opens eu.scattering.core.test.main.mutable.number;
    opens eu.scattering.core.test.main.fixed;
    opens eu.scattering.core.test;
}