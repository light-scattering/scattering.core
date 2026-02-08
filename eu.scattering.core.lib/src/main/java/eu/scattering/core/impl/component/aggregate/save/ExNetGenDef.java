package eu.scattering.core.impl.component.aggregate.save;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;

import java.util.ArrayList;
import java.util.List;

public class ExNetGenDef {

    public static void core(FAggregate aggregate, StringBuilder builder) {
        List<String> names = new ArrayList<>();

        builder.append("algebraic3d\n\n");

        particles(aggregate, names, builder);

        builder.append('\n');
        builder.append("solid structure = ");
        builder.append(String.join(" or ", names));
        builder.append(";\n\n");
        builder.append("solid aggregate = structure;\n");
        builder.append("tlo aggregate;");
    }

    private static void particles(FAggregate aggregate, List<String> names, StringBuilder builder) {

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                names.add(toFSphere(fSphere, builder));
            }
        });
    }

    private static String toFSphere(FSphere shape, StringBuilder builder) {

        builder.append("solid particle_");
        builder.append((int) shape.getIndex());
        builder.append(" = sphere (");
        builder.append(shape.getCenterX());
        builder.append(",");
        builder.append(shape.getCenterY());
        builder.append(",");
        builder.append(shape.getCenterZ());
        builder.append(";");
        builder.append(shape.getRadius());
        builder.append(");\n");

        return "particle_" + (int) shape.getIndex();
    }
}
