module eu.scattering.core.test {
    requires org.junit.jupiter.api;
    requires org.assertj.core;
    requires org.json;
    requires eu.scattering.core.design;
    requires eu.scattering.core.transfer;
    requires eu.scattering.core.impl.def;
    opens eu.scattering.core.test.mutables.geometry.primitive;
    opens eu.scattering.core.test.mutables.geometry.construct;
    opens eu.scattering.core.test.mutables.number;
    opens eu.scattering.core.test.engines;
    opens eu.scattering.core.test.helpers;
    opens eu.scattering.core.test;
    opens eu.scattering.core.test.mutables.geometry.primitive.engine;
    opens eu.scattering.core.test.mutables.geometry.construct.engine;
}