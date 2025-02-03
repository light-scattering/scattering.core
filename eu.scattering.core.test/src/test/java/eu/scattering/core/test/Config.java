package eu.scattering.core.test;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.engine.prototype.FProtoEngine;
import eu.scattering.core.design.engine.randomize.processor.FRandProcessor;
import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.impl.FactoryDef;

public final class Config {

    private Config() {
    }

    public static final double epsilon = 1E-8;

    public static final FactoryDesignConcrete factory = FactoryDef.create();
    public static final FProtoEngine proto = factory.getFProtoEngine();
    public static final FRandProcessor rand = factory.getFRandProcessor();
    public static final FRotEngine rot = factory.getFRotEngine();

}
