package eu.scattering.core.impl.component.aggregate.export;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.PovRayPreset;
import eu.scattering.core.design.type.RadiusOfGyration;

import java.util.HashMap;
import java.util.Map;

public class PovRayDef {
    private static final boolean MONOCHROMATIC = false;

    public static void core(FAggregate aggregate, PovRayPreset preset, StringBuilder builder) {

        builder.append("""
#include "colors.inc"
#include "textures.inc"
#include "transforms.inc"
 
#declare Cam_Rot = <-15, -15, 0>;
#declare Zoom = 250;
                
light_source {
    <0, 0, 300>
    color White
    parallel
    point_at <0, 0, 0>
    rotate Cam_Rot
}
 
camera {
    orthographic
    location <0, 0, 300>
    look_at  <0, 0, 0>
    right    x * (image_width / image_height) * Zoom
    up       y * Zoom
    rotate   Cam_Rot
}

background {
    color rgb<1,1,1>
}

        """);

        FAggregate aggregateResized = aggregate.copy(false);
        aggregateResized.setCenterAsZero(Center.MASS);
        aggregateResized.setRadiusFrom(Center.ORIGIN, 100);

        if (preset.equals(PovRayPreset.RADIUS)) {
            radiusVolume(aggregate, aggregateResized, builder);
            radiusOfGyration(aggregate, aggregateResized, builder);
        }

        if (preset.equals(PovRayPreset.BOX)) {
            boundary(aggregateResized, builder);
        }

        particles(aggregateResized, builder);
    }

    private static void radiusVolume(FAggregate aggregate, FAggregate aggregateParsed, StringBuilder builder) {
        double radius = aggregate.getVolumeRadius();
        double radiusParsed = aggregateParsed.getVolumeRadius();

        builder.append("// Volume radius - ").append(radius).append("\n");
        builder.append("sphere {\n");
        builder.append("    <0,0,0>, ").append(radiusParsed).append("\n");
        builder.append("""
    no_shadow
    pigment {
        color rgbt <0,0,0,0.2>
    }
    finish {
        ambient 0.8
        diffuse 0.8
    }
}

        """);
    }

    private static void radiusOfGyration(FAggregate aggregate, FAggregate aggregateParsed, StringBuilder builder) {
        double radius = aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);
        double radiusParsed = aggregateParsed.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);

        builder.append("// Radius of gyration - ").append(radius).append("\n");
        builder.append("sphere {\n");
        builder.append("    <0,0,0>, ").append(radiusParsed).append("\n");
        builder.append("""
    no_shadow
    pigment {
        color rgbt <0,0,0,0.6>
    }
    finish {
        ambient 0.8
        diffuse 0.8
    }
}

        """);
    }

    private static void boundary(FAggregate aggregate, StringBuilder builder) {
        FPairPos3D boundary = aggregate.getBoundary();
        FPos3D posA = boundary.getPosA();
        FPos3D posB = boundary.getPosB();

        boundaryX(posA.getD0(), posB.getD0(), posA.getD1(), posA.getD2(), builder);
        boundaryX(posA.getD0(), posB.getD0(), posB.getD1(), posA.getD2(), builder);
        boundaryX(posA.getD0(), posB.getD0(), posA.getD1(), posB.getD2(), builder);
        boundaryX(posA.getD0(), posB.getD0(), posB.getD1(), posB.getD2(), builder);

        boundaryY(posA.getD0(), posA.getD1(), posB.getD1(), posA.getD2(), builder);
        boundaryY(posB.getD0(), posA.getD1(), posB.getD1(), posA.getD2(), builder);
        boundaryY(posA.getD0(), posA.getD1(), posB.getD1(), posB.getD2(), builder);
        boundaryY(posB.getD0(), posA.getD1(), posB.getD1(), posB.getD2(), builder);

        boundaryZ(posA.getD0(), posA.getD1(), posA.getD2(), posB.getD2(), builder);
        boundaryZ(posA.getD0(), posB.getD1(), posA.getD2(), posB.getD2(), builder);
        boundaryZ(posB.getD0(), posA.getD1(), posA.getD2(), posB.getD2(), builder);
        boundaryZ(posB.getD0(), posB.getD1(), posA.getD2(), posB.getD2(), builder);
    }

    private static void boundaryX(double xMin, double xMax, double y, double z, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0,0,0> <").append(Math.abs(xMax - xMin)).append(",0,0> 0.3\n");
        builder.append("    translate <").append(xMin).append(",").append(y).append(",").append(z).append(">\n");

        boundaryFinish(builder);
    }

    private static void boundaryY(double x, double yMin, double yMax, double z, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0,0,0> <0,").append(Math.abs(yMax - yMin)).append(",0> 0.3\n");
        builder.append("    translate <").append(x).append(",").append(yMin).append(",").append(z).append(">\n");

        boundaryFinish(builder);
    }

    private static void boundaryZ(double x, double y, double zMin, double zMax, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0,0,0> <0,0,").append(Math.abs(zMax - zMin)).append("> 0.3\n");
        builder.append("    translate <").append(x).append(",").append(y).append(",").append(zMin).append(">\n");

        boundaryFinish(builder);
    }

    private static void boundaryFinish(StringBuilder builder) {

        builder.append("""
    no_shadow
    pigment {
        color rgbt <0.3,0.3,0.3,0>
    }
    finish {
        ambient 0.2
        diffuse 0.8
        phong 0.1
        phong_size 3
    }
}

                """);
    }

    private static void particles(FAggregate aggregate, StringBuilder builder) {
        Map<String, String> material;

        if (MONOCHROMATIC) {
            material = getMaterialMonochromatic(aggregate);
        } else {
            material = getMaterial(aggregate);
        }

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                toFSphere(fSphere, material, builder);
            }
        });
    }

    private static void toFSphere(FSphere shape, Map<String, String> material, StringBuilder builder) {

        builder.append("sphere {\n");
        builder.append("    <0,0,0>, ").append(shape.getRadius()).append("\n");
        builder.append("    translate <").append(shape.getCenterX()).append(",").append(shape.getCenterY()).append(",").append(shape.getCenterZ()).append(">\n");
        builder.append("""
    no_shadow
    pigment {
        """);
        builder.append(material.get(shape.getMeta()));
        builder.append("""
    }
    finish {
        ambient 0.2
        diffuse 0.4
        phong 0.8
        phong_size 150
    }
}

        """);
    }

    //--------------------------------------------------

    private static Map<String, String> getMaterialMonochromatic(FAggregate aggregate) {
        Map<String, String> map = new HashMap<>();

        for (Shape particle : aggregate) {
            for (int i = 0 ; i < particle.getLayerCount() ; i++) {
                map.put(particle.getMeta(i), "        color rgbt <0.1,0.1,0.1,0.0>\n");
            }
        }

        return map;
    }

    private static Map<String, String> getMaterial(FAggregate aggregate) {
        Map<String, String> map = new HashMap<>();

        for (Shape particle : aggregate) {
            for (int i = 0 ; i < particle.getLayerCount() ; i++) {
                map.put(particle.getMeta(i), "");
            }
        }

        double pigmentStep = 1d / (map.size() + 1);

        int i = 0;
        for (String key : map.keySet()) {
            double pigment = i++ * pigmentStep + 0.1;
            map.put(key, "        color rgbt <" + pigment + "," + pigment + "," + pigment + ",0.0>\n");
        }

        return map;
    }
}
