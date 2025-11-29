package eu.scattering.core.impl.aspect.export;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.aspect.export.FExportAspect;
import eu.scattering.core.design.statistics.StatisticsAspectExport;
import eu.scattering.core.impl.statistics.StatisticsAspectExportDef;

import java.util.ArrayList;
import java.util.List;

public class FExportAspectDef implements FExportAspect {
    private static FExportAspect SELF;

    private final ScatFactory factory;

    private FExportAspectDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FExportAspect get(ScatFactory factory) {

        if (FExportAspectDef.SELF == null) {
            FExportAspectDef.SELF = new FExportAspectDef(factory);
        }

        return FExportAspectDef.SELF;
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
        builder.append(";");
        builder.append(shape.getRadius() / 1000);
        builder.append(");\n");

        return "particle_" + (int) shape.getIndex();
    }

    //--------------------------------------------------

    @Override
    public StatisticsAspectExport getFPlotContext() {

        return StatisticsAspectExportDef.create(this.factory);
    }
}
