package eu.scattering.core.test;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.prototype.FProtoAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.impl.ScatFactoryDef;

public final class Config {

    private Config() {}

    public static final double epsilon = 1E-8;

    public static final ScatFactory factory = ScatFactoryDef.create();
    public static final FProtoAspect proto = factory.getProtoAspect();
    public static final FRandGenerator rand = factory.getFRand();
    public static final FRotAspect rot = factory.getRotAspect();
}
