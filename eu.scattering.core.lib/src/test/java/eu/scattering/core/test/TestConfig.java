package eu.scattering.core.test;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.prototype.FProtoAspect;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.impl.factory.ScatterFactoryDef;

public final class TestConfig {

    private TestConfig() {}

    public static final double epsilon = 1E-8;

    public static final ScatterFactory factory = ScatterFactoryDef.create();
    public static final FProtoAspect proto = factory.prototype();
    public static final FRandEngine rand = factory.random().engine();
    public static final FRotAspect rot = factory.rotate();
}
