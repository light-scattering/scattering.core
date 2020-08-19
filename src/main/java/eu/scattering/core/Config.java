package eu.scattering.core;

import lombok.Getter;
import lombok.Setter;

import java.io.PrintStream;

public class Config {

    private Config() { }

    public static final double jitter = 1E-10;

    public static final PrintStream debugPrintStream = System.out;

    @Getter @Setter public static boolean devEnabled = true;
    @Getter @Setter public static boolean devObjectStatsSuspended = true;
}
