package eu.scattering.core.predefined.aggregate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class F3D_N1000_Mono {

    public static String mono_22_10;
    public static String mono_18_14;
    public static String mono_14_18;

    static {
        try {
            mono_22_10 = new String(Files.readAllBytes(
                    Paths.get("src/test/java/eu/scattering/core/predefined/aggregate/F3D_N1000_D22_K10_R1.json")));
            mono_18_14 = new String(Files.readAllBytes(
                    Paths.get("src/test/java/eu/scattering/core/predefined/aggregate/F3D_N1000_D18_K14_R1.json")));
            mono_14_18 = new String(Files.readAllBytes(
                    Paths.get("src/test/java/eu/scattering/core/predefined/aggregate/F3D_N1000_D14_K18_R1.json")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get_22_10() {

        return F3D_N1000_Mono.mono_22_10;
    }

    public static String get_18_14() {

        return F3D_N1000_Mono.mono_18_14;
    }

    public static String get_14_18() {

        return F3D_N1000_Mono.mono_14_18;
    }
}
