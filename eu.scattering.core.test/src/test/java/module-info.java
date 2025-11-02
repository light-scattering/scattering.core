module eu.scattering.core.test {
    requires org.junit.jupiter.api;
    requires org.assertj.core;
    requires org.json;
    requires eu.scattering.core.design;
    requires eu.scattering.core.impl.def;
    opens eu.scattering.core.test.component.geometry.base;
    opens eu.scattering.core.test.component.geometry.construct;
    opens eu.scattering.core.test.component.number;
    opens eu.scattering.core.test.engine;
    opens eu.scattering.core.test.helper;
    opens eu.scattering.core.test;
    opens eu.scattering.core.test.component.geometry.base.engine;
    opens eu.scattering.core.test.component.geometry.construct.engine;
    opens eu.scattering.core.test.component.number.engine;
    opens eu.scattering.core.test.component.geometry.shape;
    opens eu.scattering.core.test.component.geometry.shape.engine;
    opens eu.scattering.core.test.component.geometry.container;
    opens eu.scattering.core.test.component.geometry;
    opens eu.scattering.core.test.engine.randomize;
    opens eu.scattering.core.test.engine.randomize.module;
    opens eu.scattering.core.test.component.aggregate;
    opens eu.scattering.core.test.component.aggregate.monitor;
    opens eu.scattering.core.test.component.aggregate.model.pc;
    opens eu.scattering.core.test.statistics;
    opens eu.scattering.core.test.transfer;
    opens eu.scattering.core.test.storage;
}