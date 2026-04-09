package eu.scattering.core.impl.component.aggregate.save;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.Volume;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.HashMap;
import java.util.Map;

public class ExPovRayDef {

    public static void core(FAggregate aggregate, ExPovRay preset, StringBuilder builder) {

        boolean shadow = switch (preset) {
            case FREE, RADIUS, BOX_COUNTING -> false;
            case BOUNDARY -> true;
        };

        boolean monochromatic = switch (preset) {
            case FREE, BOUNDARY, BOX_COUNTING -> false;
            case RADIUS -> true;
        };

        builder.append("""
               #include "colors.inc"
               #include "textures.inc"
               #include "transforms.inc"
               
               #declare Cam_Rot = <25, 35, 0>;
               #declare Zoom = 250;
               
               light_source {
                   <0, 1000, 0>
                   color rgb <0.6, 0.6, 0.6>
                   parallel
                   point_at <0, 0, 0>
               }
               
               light_source {
                   <-1000, 0, 0>
                   color rgb <0.6, 0.6, 0.6>
                   parallel
                   point_at <0, 0, 0>
               }
               
               light_source {
                   <0, 0, -1000>
                   color rgb <0.6, 0.6, 0.6>
                   jitter
                   parallel point_at <0, 0, 0>
               }
               
               light_source {
                   <-300, 300, -300>
                   color rgb <0.2, 0.2, 0.2>
                   shadowless
                   parallel
                   point_at <0, 0, 0>
               }
               
               camera {
                   orthographic
                   location <0, 0, -300>
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

        if (shadow) {
            FPairPos3D boundary = aggregateResized.getBoundary();
            FPos3D posA = boundary.getPosA();
            FPos3D posB = boundary.getPosB();

            double maxX = posB.getD0();
            double maxZ = posB.getD2();

            builder.append("plane {\n");
            builder.append("    y, ").append(posA.getD1()).append("\n");
            builder.append("""
                        no_shadow
                        pigment {
                            color rgb <1.2, 1.2, 1.2>
                        }
                        finish {
                            ambient 0.65
                            diffuse 0.6
                        }
                    }
                    
                    """);

            builder.append("plane {\n");
            builder.append("    -x, ").append(-maxX).append("\n");
            builder.append("""
                no_shadow
                pigment {
                    color rgb <1.2, 1.2, 1.2>
                }
                finish {
                    ambient 0.65
                    diffuse 0.6
                }
            }
            
            """);

            builder.append("plane {\n");
            builder.append("    -z, ").append(-maxZ).append("\n");
            builder.append("""
                no_shadow
                pigment {
                    color rgb <1.2, 1.2, 1.2>
                }
                finish {
                    ambient 0.65
                    diffuse 0.6
                }
            }
            
            """);
        }

        if (preset.equals(ExPovRay.RADIUS)) {
            centerOfMass(builder);
            radiusVolume(aggregate, aggregateResized, builder);
            radiusOfGyration(aggregate, aggregateResized, builder);
        }

        if (preset.equals(ExPovRay.BOUNDARY)) {
            boundary(aggregateResized, builder);
        }

        if (preset.equals(ExPovRay.BOX_COUNTING)) {
            boundaryVisio(aggregateResized, builder);
        }

        particles(aggregateResized, builder, monochromatic);
    }

    private static void centerOfMass(StringBuilder builder) {
        builder.append("""
            sphere {
                <0,0,0>, 1
                translate <0, 0, -200>
                rotate Cam_Rot
                no_shadow
                pigment {
                    color rgb <0, 0, 0>
                }
                finish {
                    ambient 1.0
                    diffuse 0.0
                }
            }
            
            """);
    }

    private static void radiusVolume(FAggregate aggregate, FAggregate aggregateParsed, StringBuilder builder) {
        double radius = aggregate.getVolumeRadius(Volume.ADAPTIVE);
        double radiusParsed = aggregateParsed.getVolumeRadius(Volume.ADAPTIVE);

        builder.append("// Volume radius - ").append(radius).append("\n");
        builder.append("torus {\n");
        builder.append("    ").append(radiusParsed).append(", 0.25\n");
        builder.append("""
            rotate x * 90
            translate <0, 0, -200>
            rotate Cam_Rot
            no_shadow
            pigment {
                color rgb <0, 0, 0>
            }
            finish {
                ambient 1.0
                diffuse 0.0
            }
        }
        
        """);

        builder.append("union {\n");
        builder.append("    cylinder { <0, 0, 0>, <").append(radiusParsed - 5.0).append(", 0, 0>, 0.25 }\n");
        builder.append("    cone { <").append(radiusParsed - 5.0).append(", 0, 0>, 2.0, <").append(radiusParsed).append(", 0, 0>, 0.0 }\n");
        builder.append("""
            pigment { color rgb <0, 0, 0> }
            finish {
                ambient 1.0
                diffuse 0.0
            }
            rotate z * 135
            translate <0, 0, -200>
            rotate Cam_Rot
            no_shadow
        }
        
        """);
    }

    private static void radiusOfGyration(FAggregate aggregate, FAggregate aggregateParsed, StringBuilder builder) {
        double radius = aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);
        double radiusParsed = aggregateParsed.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);

        double dashScale = (radiusParsed * 2 * Math.PI) / 35.0;

        builder.append("// Radius of gyration - ").append(radius).append("\n");
        builder.append("torus {\n");
        builder.append("    ").append(radiusParsed).append(", 0.25\n");
        builder.append("""
            pigment {
                radial
                frequency 35
                color_map {
                    [0.0 color rgb <0, 0, 0>]
                    [0.5 color rgb <0, 0, 0>]
                    [0.5 color rgbt <0, 0, 0, 1>]
                    [1.0 color rgbt <0, 0, 0, 1>]
                }
            }
            rotate x * 90
            translate <0, 0, -200>
            rotate Cam_Rot
            no_shadow
            finish {
                ambient 1.0
                diffuse 0.0
            }
        }

        """);

        builder.append("union {\n");
        builder.append("    cylinder { <0, 0, 0>, <").append(radiusParsed - 5.0).append(", 0, 0>, 0.25\n");
        builder.append("        pigment {\n");
        builder.append("            gradient x\n");
        builder.append("            color_map {\n");
        builder.append("                [0.0 color rgb <0, 0, 0>]\n");
        builder.append("                [0.5 color rgb <0, 0, 0>]\n");
        builder.append("                [0.5 color rgbt <0, 0, 0, 1>]\n");
        builder.append("                [1.0 color rgbt <0, 0, 0, 1>]\n");
        builder.append("            }\n");
        builder.append("            scale ").append(dashScale).append("\n");
        builder.append("        }\n");
        builder.append("    }\n");

        builder.append("    cone { <").append(radiusParsed - 5.0).append(", 0, 0>, 2.0, <").append(radiusParsed).append(", 0, 0>, 0.0\n");
        builder.append("        pigment { color rgb <0, 0, 0> }\n");
        builder.append("    }\n");

        builder.append("""
            finish {
                ambient 1.0
                diffuse 0.0
            }
            rotate z * 45
            translate <0, 0, -200>
            rotate Cam_Rot
            no_shadow
        }
        
        """);
    }

    private static void boundary(FAggregate aggregate, StringBuilder builder) {

        builder.append("// BOUNDARY\n\n");

        FPairPos3D boundary = aggregate.getBoundary();
        FPos3D posA = boundary.getPosA();
        FPos3D posB = boundary.getPosB();

        boundarySingleX(posA.getD0(), posB.getD0(), posA.getD1(), posA.getD2(), builder);
//        boundarySingleX(posA.getD0(), posB.getD0(), posB.getD1(), posA.getD2(), builder);
        boundarySingleX(posA.getD0(), posB.getD0(), posA.getD1(), posB.getD2(), builder);
        boundarySingleX(posA.getD0(), posB.getD0(), posB.getD1(), posB.getD2(), builder);

//        boundarySingleY(posA.getD0(), posA.getD1(), posB.getD1(), posA.getD2(), builder);
        boundarySingleY(posB.getD0(), posA.getD1(), posB.getD1(), posA.getD2(), builder);
        boundarySingleY(posA.getD0(), posA.getD1(), posB.getD1(), posB.getD2(), builder);
        boundarySingleY(posB.getD0(), posA.getD1(), posB.getD1(), posB.getD2(), builder);

        boundarySingleZ(posA.getD0(), posA.getD1(), posA.getD2(), posB.getD2(), builder);
//        boundarySingleZ(posA.getD0(), posB.getD1(), posA.getD2(), posB.getD2(), builder);
        boundarySingleZ(posB.getD0(), posA.getD1(), posA.getD2(), posB.getD2(), builder);
        boundarySingleZ(posB.getD0(), posB.getD1(), posA.getD2(), posB.getD2(), builder);
    }

    private static void boundarySingleX(double xMin, double xMax, double y, double z, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0, 0, 0> <").append(Math.abs(xMax - xMin)).append(", 0, 0> 0.3\n");
        builder.append("    translate <").append(xMin).append(", ").append(y).append(", ").append(z).append(">\n");

        boundaryFinish(builder);
    }

    private static void boundarySingleY(double x, double yMin, double yMax, double z, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0, 0, 0> <0, ").append(Math.abs(yMax - yMin)).append(", 0> 0.3\n");
        builder.append("    translate <").append(x).append(", ").append(yMin).append(", ").append(z).append(">\n");

        boundaryFinish(builder);
    }

    private static void boundarySingleZ(double x, double y, double zMin, double zMax, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0, 0, 0> <0, 0, ").append(Math.abs(zMax - zMin)).append("> 0.3\n");
        builder.append("    translate <").append(x).append(", ").append(y).append(", ").append(zMin).append(">\n");

        boundaryFinish(builder);
    }

    private static void boundaryFinish(StringBuilder builder) {

            builder.append("""
                    no_shadow
                    pigment {
                        color rgbt <0.3, 0.3, 0.3, 0>
                    }
                    finish {
                        ambient     0.2
                        diffuse     0.8
                        phong       0.1
                        phong_size  3
                    }
                }
    
                """);
    }

    private static void boundaryVisio(FAggregate aggregate, StringBuilder builder) {

        builder.append("// BOUNDARY\n\n");

        FPairPos3D boundary = aggregate.getBoundary();
        FPos3D posA = boundary.getPosA();

        double cutoffOuter = aggregate.getLength(Length.MAX);
        double cutoffInner = aggregate.getFStatParticleRadius().mean() * 2;

        double step = cutoffOuter * 0.5;

        while (step > cutoffInner) {
            boundaryVisioSingle(posA.getD0(), posA.getD1(), posA.getD2(), step, builder);
            step *= 0.5;
        }
    }

    private static void boundaryVisioSingle(double x, double y, double z, double length, StringBuilder builder) {

        boundaryVisioSingleX(x, y, z, length, builder);
        boundaryVisioSingleX(x, y + length, z, length, builder);
        boundaryVisioSingleX(x, y, z + length, length, builder);
        boundaryVisioSingleX(x, y + length, z + length, length, builder);

        boundaryVisioSingleY(x, y, z, length, builder);
        boundaryVisioSingleY(x + length, y, z, length, builder);
        boundaryVisioSingleY(x, y, z + length, length, builder);
        boundaryVisioSingleY(x + length, y, z + length, length, builder);

        boundaryVisioSingleZ(x, y, z, length, builder);
        boundaryVisioSingleZ(x + length, y, z, length, builder);
        boundaryVisioSingleZ(x, y + length, z, length, builder);
        boundaryVisioSingleZ(x + length, y + length, z, length, builder);
    }

    private static void boundaryVisioSingleX(double x, double y, double z, double length, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0, 0, 0> <").append(length).append(", 0, 0> 0.2\n");
        builder.append("    translate <").append(x).append(", ").append(y).append(", ").append(z).append(">\n");

        boundaryVisioFinish(builder);
    }

    private static void boundaryVisioSingleY(double x, double y, double z, double length, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0, 0, 0> <0, ").append(length).append(", 0> 0.2\n");
        builder.append("    translate <").append(x).append(", ").append(y).append(", ").append(z).append(">\n");

        boundaryVisioFinish(builder);
    }

    private static void boundaryVisioSingleZ(double x, double y, double z, double length, StringBuilder builder) {

        builder.append("cylinder {\n");
        builder.append("    <0, 0, 0> <0, 0, ").append(length).append("> 0.2\n");
        builder.append("    translate <").append(x).append(", ").append(y).append(", ").append(z).append(">\n");

        boundaryVisioFinish(builder);
    }

    private static void boundaryVisioFinish(StringBuilder builder) {

        builder.append("""
                no_shadow
                pigment {
                    color rgbt <0.8, 0.8, 0.8 ,0>
                }
                finish {
                    ambient     0.2
                    diffuse     0.8
                    phong       0.1
                    phong_size  3
                }
            }
            
            """);
    }

    private static void particles(FAggregate aggregate, StringBuilder builder, boolean monochromatic) {
        Map<String, String> material;

        if (monochromatic) {
            material = getMaterialMonochromatic(aggregate);
        } else {
            material = getMaterial(aggregate);
        }

        builder.append("// PARTICLES\n\n");

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                toFSphere(fSphere, material, builder);
            }
        });
    }

    private static void toFSphere(FSphere shape, Map<String, String> material, StringBuilder builder) {

        builder.append("sphere {\n");
        builder.append("    <0, 0, 0>, ").append(shape.getRadius()).append("\n");
        builder.append("    translate <").append(shape.getCenterX()).append(", ").append(shape.getCenterY()).append(", ").append(shape.getCenterZ()).append(">\n");

        builder.append("""
                    pigment {
                """);
        builder.append(material.get(shape.getMeta()));
        builder.append("""
                    }
                    finish {
                        ambient     0.2
                        diffuse     0.4
                        phong       0.8
                        phong_size  150
                    }
                }
                
                """);
    }

    //--------------------------------------------------

    private static Map<String, String> getMaterialMonochromatic(FAggregate aggregate) {
        Map<String, String> map = new HashMap<>();

        for (Shape particle : aggregate) {
            for (int i = 0 ; i < particle.getLayerCount() ; i++) {
                map.put(particle.getMeta(i), "        color rgbt <0.9, 0.9, 0.9, 0.0>\n");
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
            map.put(key, "        color rgbt <" + pigment + ", " + pigment + ", " + pigment + ", 0.0>\n");
        }

        return map;
    }
}
