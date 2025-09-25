package eu.scattering.core.impl.engine.export;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.engine.export.FExportEngine;

import java.util.ArrayList;
import java.util.List;

public class FExportEngineDef implements FExportEngine {
    private static FExportEngine self;

    private FExportEngineDef() {}

    public static FExportEngine get() {

        if (FExportEngineDef.self == null) {
            FExportEngineDef.self = new FExportEngineDef();
        }

        return FExportEngineDef.self;
    }

    //--------------------------------------------------

    @Override
    public void exportFLAGE(FAggregate aggregate, StringBuilder builder) {
        builder.append("FLAGE: 1.00\n\n");
        builder.append("ID Radius X Y Z Re(M) Im(M) Type\n");

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                exportFLAGE(fSphere, builder);
            }
        });
    }

    @Override
    public void exportNGSolve(FAggregate aggregate, StringBuilder builder) {
        List<String> names = new ArrayList<>();

        builder.append("algebraic3d\n\n");

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                names.add(exportNGSolve(fSphere, builder));
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
    public void exportFLAGE(FSphere shape, StringBuilder builder) {

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

    @Override
    public String exportNGSolve(FSphere shape, StringBuilder builder) {

        builder.append("solid particle_");
        builder.append((int) shape.getIndex());
        builder.append(" = sphere (");
        builder.append(shape.getCenterX() / 1000);
        builder.append(",");
        builder.append(shape.getCenterY() / 1000);
        builder.append(",");
        builder.append(shape.getCenterZ() / 1000);
        builder.append(";0.001);\n");

        return "particle_" + (int) shape.getIndex();
    }
}
