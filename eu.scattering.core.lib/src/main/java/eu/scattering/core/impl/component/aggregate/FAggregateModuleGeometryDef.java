package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.Surface;
import eu.scattering.core.design.utility.type.method.Volume;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.method.MassCenter;

import java.util.*;

public class FAggregateModuleGeometryDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleGeometryDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected int size() {

        return this.aggregate.getRefParticles().size();
    }

    // -------------------------------------------------------------------------------------------------

    protected double getSurface(Surface type) {
        double surface = 0;

        FLayer fLayer = switch (type) {
            case ADAPTIVE, DISCRETE -> this.factory.getFLayer();
            case SIMPLE -> null;
        };

        List<Shape> field = getUniqueShapes();

        for (Shape shape : field) {

            surface += switch (type) {
                case ADAPTIVE -> getParticleSurfaceAdaptive(field, fLayer, shape);
                case SIMPLE -> getParticleSurfaceSimple(shape);
                case DISCRETE -> getParticleSurfaceComplex(field, fLayer, shape);
            };
        }

        return surface;
    }

    private double getParticleSurfaceAdaptive(List<Shape> field, FLayer fLayer, Shape shape) {

        if (shape.overlaps(field) == 0) {
            return getParticleSurfaceSimple(shape);
        }

        return getParticleSurfaceComplex(field, fLayer, shape);
    }

    private double getParticleSurfaceSimple(Shape shape) {

        return shape.getSurfaceAlgebraic();
    }

    private double getParticleSurfaceComplex(List<Shape> field, FLayer fLayer, Shape shape) {

        fLayer.reset();

        double surfaceUnit = shape.fillSurfaceLayerOverlap(fLayer, field);

        return fLayer.get(0) * surfaceUnit;
    }

    protected double getSurface(double[] layers, Surface type) {
        double surface = 0;

        Arrays.fill(layers, 0);

        List<Shape> field = getUniqueShapes();

        for (Shape shape : field) {

            switch (type) {
                case ADAPTIVE -> getParticleSurfaceAdaptive(field, shape, layers);
                case SIMPLE -> getParticleSurfaceSimple(shape, layers);
                case DISCRETE -> getParticleSurfaceComplex(field, shape, layers);
            }
        }

        for (double layer : layers) {
            surface += layer;
        }

        return surface;
    }

    private void getParticleSurfaceAdaptive(List<Shape> field, Shape shape, double[] layers) {

        if (shape.overlaps(field) == 0) {
            getParticleSurfaceSimple(shape, layers);
        } else {
            getParticleSurfaceComplex(field, shape, layers);
        }
    }

    private void getParticleSurfaceSimple(Shape shape, double[] layers) {

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            layers[i] += shape.getLayerSurface(i);
        }
    }

    private void getParticleSurfaceComplex(List<Shape> field, Shape shape, double[] layers) {
        FLayer fLayer = this.factory.getFLayer();

        double surfaceUnit = shape.fillSurfaceLayer(fLayer, field);

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            layers[i] += fLayer.get(i) * surfaceUnit;
        }
    }

    protected double getSurfaceRadius(Surface type) {

        return this.factory.getFSphereHelper().getSurfaceRadius(getSurface(type));
    }

    protected double getSurfaceRadius(double[] layers, Surface type) {
        double resSurface = getSurface(layers, type);

        int i = 0;
        for (; i < layers.length ; i++) {
            layers[i] = this.factory.getFSphereHelper().getSurfaceRadius(layers[i]);
        }

        return this.factory.getFSphereHelper().getSurfaceRadius(resSurface);
    }

    private List<Shape> getUniqueShapes() {
        ArrayList<Shape> results = new ArrayList<>();

        this.aggregate.forEach(e -> {
            if (results.stream().noneMatch(e::isExact)) {
                results.add(e);
            }
        });

        return results;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getVolume(Volume type) {
        double volume = 0;

        FLayer fLayer = switch (type) {
            case ADAPTIVE, DISCRETE -> this.factory.getFLayer();
            case SIMPLE -> null;
        };

        Queue<Shape> queue = new LinkedList<>(this.aggregate.getRefParticles().asList());

        queue.poll();

        for (Shape shape : this.aggregate) {

            volume += switch (type) {
                case ADAPTIVE -> getParticleVolumeAdaptive(queue, fLayer, shape);
                case SIMPLE -> getParticleVolumeSimple(shape);
                case DISCRETE -> getParticleVolumeComplex(queue, fLayer, shape);
            };

            queue.poll();
        }

        return volume;
    }

    private double getParticleVolumeAdaptive(Queue<Shape> queue, FLayer fLayer, Shape shape) {

        if (shape.overlaps(queue) == 0) {
            return getParticleVolumeSimple(shape);
        }

        return getParticleVolumeComplex(queue, fLayer, shape);
    }

    private double getParticleVolumeSimple(Shape shape) {

        return shape.getVolumeAlgebraic();
    }

    private double getParticleVolumeComplex(Queue<Shape> queue, FLayer fLayer, Shape shape) {

        fLayer.reset();

        double volumeUnit = shape.fillVolumeLayerOverlap(fLayer, queue);

        return fLayer.get() * volumeUnit;
    }

    protected double getVolume(double[] layers, Volume type) {
        double volume = 0;

        Arrays.fill(layers, 0);

        for (Shape shape : this.aggregate) {

            switch (type) {
                case ADAPTIVE -> getParticleVolumeAdaptive(shape, layers);
                case SIMPLE -> getParticleVolumeSimple(shape, layers);
                case DISCRETE -> getParticleVolumeComplex(shape, layers);
            }
        }

        for (double layer : layers) {
            volume += layer;
        }

        return volume;
    }

    private void getParticleVolumeAdaptive(Shape shape, double[] volume) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            getParticleVolumeSimple(shape, volume);
        } else {
            getParticleVolumeComplex(shape, volume);
        }
    }

    private void getParticleVolumeSimple(Shape shape, double[] volume) {

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume[i] += shape.getLayerVolume(i);
        }
    }

    private void getParticleVolumeComplex(Shape shape, double[] volume) {
        FLayer fLayer = this.factory.getFLayer();

        shape.fillVolumeLayer(fLayer, this.aggregate.getRefParticles().asList());
        double volUnit = Math.pow(shape.getDelta(), 3);

        for (int i = 0; i < fLayer.size() ; i++) {
            volume[i] += fLayer.get(i) * volUnit;
        }
    }

    protected double getVolumeRadius(Volume type) {

        return this.factory.getFSphereHelper().getVolumeRadius(getVolume(type));
    }

    protected double getVolumeRadius(double[] layers, Volume type) {
        double volume = 0;

        double resVolume = getVolume(layers, type);

        int i = 0;
        for (; i < layers.length ; i++) {
            volume += layers[i];
            layers[i] = this.factory.getFSphereHelper().getVolumeRadius(volume);
        }

        return factory.getFSphereHelper().getVolumeRadius(resVolume);
    }

    // -------------------------------------------------------------------------------------------------

    protected FMesh<FBufferData> getVolumeMesh() {

        if (this.aggregate.getRefFExtension().getRefFBuffer() == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        this.aggregate.getRefFExtension().getRefFBuffer().clear();

        for (Shape shape : this.aggregate) {
            shape.fillVolumeArray(this.aggregate.getRefFExtension().getRefFBuffer(), this.aggregate.getRefParticles().asList());
        }

        FMesh<FBufferData> mesh = this.aggregate.getRefFExtension().getRefFBuffer().toFArrayMesh();

        mesh.deduplicate((a, b) -> b.getLayerIndex() < a.getLayerIndex());

        return mesh;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPairPos3D getBoundary() {

        return this.aggregate.getRefParticles().getBoundary();
    }

    protected FPos3D getLength() {
        FPairPos3D range = getBoundary();

        double lengthX = range.getPosB().getD0() - range.getPosA().getD0();
        double lengthY = range.getPosB().getD1() - range.getPosA().getD1();
        double lengthZ = range.getPosB().getD2() - range.getPosA().getD2();

        return factory.getFPos3D(lengthX, lengthY, lengthZ);
    }

    protected double getLength(Length type) {
        FPos3D length = getLength();

        return switch (type) {
            case X -> length.getD0();
            case Y -> length.getD1();
            case Z -> length.getD2();
            case MAX -> Math.max(length.getD0(), Math.max(length.getD1(), length.getD2()));
            case MIN -> Math.min(length.getD0(), Math.min(length.getD1(), length.getD2()));
        };
    }

    protected double getDiameter() {
        int size = this.aggregate.size();
        double diameter = Double.NEGATIVE_INFINITY;

        Shape pA, pB;
        double distance;
        for (int i = 0 ; i < size ; i++) {
            pA = this.aggregate.getRefParticles().asList().get(i);

            for (int j = i + 1 ; j < size ; j++) {
                pB = this.aggregate.getRefParticles().asList().get(j);

                distance = pA.getDistCenter(pB) + pA.getRadius() + pB.getRadius();
                if (distance > diameter) {
                    diameter = distance;
                }
            }
        }

        return diameter;
    }

    protected double getRadiusFrom(double x, double y, double z) {
        double maxRadius = -1;

        for (Shape shape : this.aggregate) {
            double radius = shape.getDistCenter(x, y, z) + shape.getRadius();

            if (radius > maxRadius) {
                maxRadius = radius;
            }
        }

        return maxRadius;
    }

    protected double getRadiusFrom(FPoint center) {

        return getRadiusFrom(center.getX(), center.getY(), center.getZ());
    }

    protected double getRadiusFrom(FPos3D center) {

        return getRadiusFrom(center.getD0(), center.getD1(), center.getD2());
    }

    protected double getRadiusFrom(Center type) {

        return switch(type) {
            case ORIGIN -> getRadiusFrom(0, 0, 0);
            case SPHERE -> getRadiusFrom(aggregate.getSphereCenter(100));
            case MASS -> getRadiusFrom(aggregate.getMassCenter(MassCenter.ADAPTIVE));
            case BOX -> getRadiusFrom(aggregate.getBoxCenter());
        };
    }

    // -------------------------------------------------------------------------------------------------

    protected void getRadiusFrom(double x, double y, double z, double radius) {

        this.aggregate.translate(-x, -y, -z);

        double radiusCurrent = this.aggregate.getRadiusFrom(0, 0, 0);

        double factor = radius / radiusCurrent;

        this.aggregate.getRefParticles().scale(factor);
        this.aggregate.getRefParticles().forEach(p -> p.setRadius(p.getRadius() * factor));

        this.aggregate.translate(x, y, z);
    }

    protected void getRadiusFrom(FPoint center, double radius) {

        getRadiusFrom(center.getX(), center.getY(), center.getZ(), radius);
    }

    protected void getRadiusFrom(FPos3D center, double radius) {

        getRadiusFrom(center.getD0(), center.getD1(), center.getD2(), radius);
    }

    protected void getRadiusFrom(Center type, double radius) {

        getRadiusFrom(this.aggregate.getCenter(type), radius);
    }

    // -------------------------------------------------------------------------------------------------

    protected FStat getFStatParticleRadius() {
        FStat particles = this.factory.getFStat();

        this.aggregate.getRefParticles().forEach(e -> particles.add(e.getRadius()));

        return particles;
    }

    protected FStat getFStatParticleDistance(Center type) {
        FStat distances = this.factory.getFStat();

        FPos3D center = this.aggregate.getCenter(type);

        for (Shape particle : this.aggregate) {
            distances.add(particle.getDistCenter(center));
        }

        return distances;
    }
}
