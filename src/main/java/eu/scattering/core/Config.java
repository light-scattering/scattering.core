package eu.scattering.core;

import eu.scattering.core.design.injection.MainFactory;
import eu.scattering.core.implementation.injection.FactoryDevelopment;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;

public class Config {

    private Config() { }

    public static MainFactory mainFactory = new FactoryDevelopment();

    @Getter @Setter private static double jitter = 1E-10;
    @Getter @Setter private static PrintStream debugPrintStream = System.out;
}
