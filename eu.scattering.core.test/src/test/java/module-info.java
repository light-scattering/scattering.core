module eu.scattering.core.test {
    requires org.junit.jupiter.api;
    requires org.assertj.core;
    requires org.json;
    requires eu.scattering.core.design;
    requires eu.scattering.core.transfer;
    requires eu.scattering.core.impl.def;
    opens eu.scattering.core.test.mutable.geometry.primitive;
    opens eu.scattering.core.test.mutable.geometry.construct;
    opens eu.scattering.core.test.mutable.number;
    opens eu.scattering.core.test.engine;
    opens eu.scattering.core.test.helper;
    opens eu.scattering.core.test;
    opens eu.scattering.core.test.mutable.geometry.primitive.engine;
    opens eu.scattering.core.test.mutable.geometry.construct.engine;
    opens eu.scattering.core.test.mutable.number.engine;
}