module eu.scattering.core.test {
    requires org.junit.jupiter.api;
    requires org.assertj.core;
    requires org.json;
    requires eu.scattering.core.design;
    requires eu.scattering.core.impl.production;
    requires eu.scattering.core.transfer;
    opens eu.scattering.core.test.mutables.algebra.geometry.simple;
    opens eu.scattering.core.test.mutables.algebra.geometry.advanced;
    opens eu.scattering.core.test.mutables.algebra.number;
    opens eu.scattering.core.test.mutables.immutable;
    opens eu.scattering.core.test.mutables.engine;
    opens eu.scattering.core.test;
}