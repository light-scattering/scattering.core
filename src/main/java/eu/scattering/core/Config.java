package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.development.DevelopmentFactory;
import eu.scattering.core.implementation.development.DevelopmentFactoryDefault;
import eu.scattering.core.implementation.FactoryDevelopment;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;

public class Config {

    private Config() { }

    public static Factory mainFactory = new FactoryDevelopment();
    public static DevelopmentFactory developmentFactory = new DevelopmentFactoryDefault();

    public static Factory getFactory() {

        return mainFactory;
    }

    @Getter @Setter private static double jitter = 1E-10;
    @Getter @Setter private static PrintStream debugPrintStream = System.out;
}
