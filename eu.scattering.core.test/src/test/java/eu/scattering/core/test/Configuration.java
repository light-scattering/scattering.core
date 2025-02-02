package eu.scattering.core.test;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.engines.proto.FProtoEngine;
import eu.scattering.core.design.engines.rand.processor.FRandProcessor;
import eu.scattering.core.design.engines.rot.FRotEngine;
import eu.scattering.core.impl.FactoryDef;

public final class Configuration {

    private Configuration() {
    }

    public static final double epsilon = 1E-8;

    public static final FactoryDesignConcrete factory = FactoryDef.create();
    public static final FProtoEngine proto = factory.getFProtoEngine();
    public static final FRandProcessor rand = factory.getFRandProcessor();
    public static final FRotEngine rot = factory.getFRotEngine();

}
