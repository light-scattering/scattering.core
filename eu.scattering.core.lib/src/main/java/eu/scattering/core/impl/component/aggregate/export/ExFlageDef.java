package eu.scattering.core.impl.component.aggregate.export;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;

import java.util.HashMap;
import java.util.Map;

public class ExFlageDef {

    public static void core(FAggregate aggregate, StringBuilder builder) {
        builder.append("FLAGE: 1.00\n\n");
        builder.append("ID Radius X Y Z Re(M) Im(M) Type\n");

       particles(aggregate, builder);
    }

    private static void particles(FAggregate aggregate, StringBuilder builder) {
        Map<String, Integer> material = getMaterial(aggregate);

        aggregate.getRefParticles().forEach(e -> {
            if (e instanceof FSphere fSphere) {
                toFSphere(fSphere, material, builder);
            }
        });
    }

    private static void toFSphere(FSphere shape, Map<String, Integer> material, StringBuilder builder) {

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
        builder.append(material.get(shape.getMeta()));
        builder.append(" ");
        builder.append(material.get(shape.getMeta()));
        builder.append(" ");
        builder.append("Type_Sphere\n");
    }

    //--------------------------------------------------

    private static Map<String, Integer> getMaterial(FAggregate aggregate) {
        Map<String, Integer> map = new HashMap<>();

        for (Shape particle : aggregate) {
            for (int i = 0 ; i < particle.getLayerCount() ; i++) {
                map.put(particle.getMeta(i), 1);
            }
        }

        int i = 1;
        for (String key : map.keySet()) {
            int value = i++;
            map.put(key, value);
        }

        return map;
    }
}
