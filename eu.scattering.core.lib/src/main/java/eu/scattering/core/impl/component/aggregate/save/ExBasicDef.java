package eu.scattering.core.impl.component.aggregate.save;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.utility.type.preset.ExBasic;

public class ExBasicDef {

    public static void core(FAggregate aggregate, ExBasic preset, StringBuilder builder) {

        if (preset == ExBasic.MULTISPHERE) {
            particlesMultisphere(aggregate, builder);
        }
    }

    private static void particlesMultisphere(FAggregate aggregate, StringBuilder builder) {

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                toFSphereMultisphere(fSphere, builder);
            }
        });
    }

    private static void toFSphereMultisphere(FSphere shape, StringBuilder builder) {

        builder
                .append(shape.getCenterX())
                .append(" ")
                .append(shape.getCenterY())
                .append(" ")
                .append(shape.getCenterZ())
                .append(" ")
                .append(shape.getRadius())
                .append("\n");
    }
}
