package eu.scattering.core.impl.component.aggregate.load;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.utility.type.preset.ExBasic;

public class ImBasicDef {

    public static FAggregate core(ScatterFactory factory, String data, ExBasic preset) {
        FAggregate fAggregate = factory.getFAggregate();

        if (preset == ExBasic.MULTISPHERE) {
            particlesMultisphere(factory, fAggregate, data);
        }

        return fAggregate;
    }

    private static void particlesMultisphere(ScatterFactory factory, FAggregate aggregate, String data) {
        String[] particles = data.split("\n");

        for (String particle : particles) {
            aggregate.addRefParticle(toFSphereMultisphere(factory, particle));
        }
    }

    private static FSphere toFSphereMultisphere(ScatterFactory factory, String particle) {
        String parsed = particle.replaceAll("\t", " ");

        while (parsed.contains("  ")) {
            parsed = parsed.replaceAll("  ", " ");
        }

        String[] elements = parsed.split(" ");

        if (elements.length < 4) {
            throw new IllegalArgumentException("The FAggregate file is corrupted");
        }

        return factory.getFSphere(
                Double.parseDouble(elements[0]),
                Double.parseDouble(elements[1]),
                Double.parseDouble(elements[2]),
                Double.parseDouble(elements[3])
        );
    }
}
