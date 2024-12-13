module eu.scattering.core.test {
    requires eu.scattering.core.impl.development;
    requires org.junit.jupiter.api;
    requires org.assertj.core;
    requires org.json;
    opens eu.scattering.core.test.core.mutable.geometry.simple;
    opens eu.scattering.core.test.core.mutable.geometry.advanced;
    opens eu.scattering.core.test.core.mutable.number;
    opens eu.scattering.core.test.core.immutable;
    opens eu.scattering.core.test.core.engine;
    opens eu.scattering.core.test;
}