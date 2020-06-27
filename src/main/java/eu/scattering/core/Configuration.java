package eu.scattering.core;

import java.io.PrintStream;

public class Configuration {

    private Configuration() { }

    public static final double jitter = 1E-10;

    public static final PrintStream debugPrintStream = System.out;
}
