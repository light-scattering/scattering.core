package eu.scattering.core;

import java.io.PrintStream;

public class Configuration {

    private Configuration() { }

    public static double jitter = 1E-10;

    public static PrintStream debugPrintStream = System.out;
}
