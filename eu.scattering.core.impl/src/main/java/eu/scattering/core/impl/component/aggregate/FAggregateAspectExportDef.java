package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateAspectExport;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.type.PovRayPreset;
import eu.scattering.core.impl.component.aggregate.export.PovRayDef;

import java.util.ArrayList;
import java.util.List;

public class FAggregateAspectExportDef implements FAggregateAspectExport {

    private FAggregateAspectExportDef() {
    }

    public static FAggregateAspectExportDef create() {

       return new FAggregateAspectExportDef();
    }

    //--------------------------------------------------

    @Override
    public void toFLAGE(FAggregate aggregate, StringBuilder builder) {
        builder.append("FLAGE: 1.00\n\n");
        builder.append("ID Radius X Y Z Re(M) Im(M) Type\n");

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                toFLAGE(fSphere, builder);
            }
        });
    }

    @Override
    public void toNGSolve(FAggregate aggregate, StringBuilder builder) {
        List<String> names = new ArrayList<>();

        builder.append("algebraic3d\n\n");

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                names.add(toNGSolve(fSphere, builder));
            }
        });

        builder.append('\n');
        builder.append("solid structure = ");
        builder.append(String.join(" or ", names));
        builder.append(";\n\n");
        builder.append("solid aggregate = structure;\n");
        builder.append("tlo aggregate;");
    }

    @Override
    public void toPovRay(FAggregate aggregate, PovRayPreset preset, StringBuilder builder) {

        PovRayDef.core(aggregate, preset, builder);
    }

    //--------------------------------------------------

    private void toFLAGE(FSphere shape, StringBuilder builder) {

        // FLAGE doesn't support float refractive indexes (only material id)

        builder.append((int) shape.getIndex());
        builder.append(" ");
        builder.append(shape.getRadius());
        builder.append(" ");
        builder.append(shape.getCenterX());
        builder.append(" ");
        builder.append(shape.getCenterY());
        builder.append(" ");
        builder.append(shape.getCenterZ());
        builder.append(" ");
        builder.append(1);
        builder.append(" ");
        builder.append(1);
        builder.append(" ");
        builder.append("Type_Sphere\n");
    }

    private String toNGSolve(FSphere shape, StringBuilder builder) {

        builder.append("solid particle_");
        builder.append((int) shape.getIndex());
        builder.append(" = sphere (");
        builder.append(shape.getCenterX() / 1000);
        builder.append(",");
        builder.append(shape.getCenterY() / 1000);
        builder.append(",");
        builder.append(shape.getCenterZ() / 1000);
        builder.append(";");
        builder.append(shape.getRadius() / 1000);
        builder.append(");\n");

        return "particle_" + (int) shape.getIndex();
    }
}
