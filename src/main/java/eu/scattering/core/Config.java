package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.implementation.FactoryDevelopment;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;

public class Config {

    private Config() { }

    @Getter @Setter private static PrintStream debugPrintStream = System.out;
}
