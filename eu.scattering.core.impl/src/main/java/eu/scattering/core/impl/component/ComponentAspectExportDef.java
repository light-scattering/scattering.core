package eu.scattering.core.impl.component;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.ComponentAspectExport;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;

import java.util.ArrayList;
import java.util.List;

public class ComponentAspectExportDef implements ComponentAspectExport {
    private final ScatFactory factory;

    private ComponentAspectExportDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static ComponentAspectExportDef create(ScatFactory factory) {

       return new ComponentAspectExportDef(factory);
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
