package eu.scattering.core;

import eu.scattering.core.design.main.MainFactory;
import eu.scattering.core.design.development.DevelopmentFactory;
import eu.scattering.core.implementation.main.MainFactoryDevelopment;
import eu.scattering.core.implementation.development.DevelopmentFactoryDefault;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;

public class Config {

    private Config() { }

    public static MainFactory mainFactory = new MainFactoryDevelopment();
    public static DevelopmentFactory developmentFactory = new DevelopmentFactoryDefault();

    @Getter @Setter private static double jitter = 1E-10;
    @Getter @Setter private static PrintStream debugPrintStream = System.out;
}
