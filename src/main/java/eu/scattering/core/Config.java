package eu.scattering.core;

import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;

public class Config {

    private Config() { }

    @Getter @Setter private static double jitter = 1E-10;

    @Getter @Setter private static PrintStream debugPrintStream = System.out;

    @Getter @Setter private static boolean devEnabled = true;
    @Getter @Setter private static boolean devObjectStatsSuspended = true;
}
