package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.Center;
import eu.scattering.core.design.utility.type.Length;
import eu.scattering.core.design.utility.type.MassCenter;

import java.util.*;

public class FAggregateModuleGeometryDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleGeometryDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected int size() {

        return this.aggregate.getRefParticles().size();
    }

    // -------------------------------------------------------------------------------------------------

    protected double getSurface() {
        FLayer fLayer = this.factory.getFLayer();

        List<Shape> field = getUniqueShapes();

        double surface = 0;
        for (Shape shape : field) {

            if (shape.overlaps(field) == 0) {
                surface += shape.getSurfaceAlgebraic();
            } else {
                fLayer.reset();

                double surfaceUnit = shape.fillSurfaceLayerOverlap(fLayer, field);

                surface += fLayer.get(0) * surfaceUnit;
            }
        }

        return surface;
    }

    protected double getSurface(double[] layers) {
        FLayer fLayer = this.factory.getFLayer();
        double surface = 0;

        Arrays.fill(layers, 0);

        List<Shape> field = getUniqueShapes();

        for (Shape shape : field) {

            if (shape.overlaps(field) == 0) {
                for (int i = 0 ; i < shape.getLayerCount() ; i++) {
                    layers[i] += shape.getLayerSurface(i);
                }
            } else {
                fLayer.reset();

                double surfaceUnit = shape.fillSurfaceLayer(fLayer, field);

                for (int i = 0 ; i < shape.getLayerCount() ; i++) {
                    layers[i] += fLayer.get(i) * surfaceUnit;
                }
            }
        }

        for (double layer : layers) {
            surface += layer;
        }

        return surface;
    }

    protected double getSurfaceRadius() {

        return this.factory.getFSphereHelper().getSurfaceRadius(getSurface());
    }

    protected double getSurfaceRadius(double[] layers) {
        double resSurface = getSurface(layers);

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

    protected double getVolume() {
        FLayer fLayer = this.factory.getFLayer();
        double volume = 0;

        Queue<Shape> queue = new LinkedList<>(this.aggregate.getRefParticles().asList());

        queue.poll();

        for (Shape shape : this.aggregate) {

            if (shape.overlaps(queue) == 0) {
                volume += shape.getVolumeAlgebraic();
            } else {
                fLayer.reset();

                double volumeUnit = shape.fillVolumeLayerOverlap(fLayer, queue);

                volume += fLayer.get() * volumeUnit;
            }

            queue.poll();
        }

        return volume;
    }

    protected double getVolume(double[] layers) {
        double volume = 0;

        Arrays.fill(layers, 0);

        for (Shape shape : this.aggregate) {
            getVolumeMethod(shape, layers);
        }

        for (double layer : layers) {
            volume += layer;
        }

        return volume;
    }

    protected double getVolumeRadius() {

        return this.factory.getFSphereHelper().getVolumeRadius(getVolume());
    }

    protected double getVolumeRadius(double[] layers) {
        double volume = 0;

        double resVolume = getVolume(layers);

        int i = 0;
        for (; i < layers.length ; i++) {
            volume += layers[i];
            layers[i] = this.factory.getFSphereHelper().getVolumeRadius(volume);
        }

        return factory.getFSphereHelper().getVolumeRadius(resVolume);
    }

    private void getVolumeMethod(Shape shape, double[] volume) {

        if (shape.overlaps(this.aggregate.getRefParticles()) != 0) {
            getVolumeMethodApproximate(shape, volume);
        } else {
            getVolumeMethodPrecise(shape, volume);
        }
    }

    private void getVolumeMethodPrecise(Shape shape, double[] volume) {

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume[i] += shape.getLayerVolume(i);
        }
    }

    private void getVolumeMethodApproximate(Shape shape, double[] volume) {
        FLayer fLayer = this.factory.getFLayer();

        shape.fillVolumeLayer(fLayer, this.aggregate.getRefParticles().asList());
        double volUnit = Math.pow(shape.getDelta(), 3);

        for (int i = 0; i < fLayer.size() ; i++) {
            volume[i] += fLayer.get(i) * volUnit;
        }
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
            case MASS -> getRadiusFrom(aggregate.getMassCenter(MassCenter.ADAPTIVE));
            case SPATIAL -> getRadiusFrom(aggregate.getSpatialCenter());
            case SPHERICAL -> getRadiusFrom(aggregate.getSphericalCenter(100));
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

    protected FStat getFStatDistance(Center type) {
        FStat distances = this.factory.getFStat();

        FPos3D center = this.aggregate.getCenter(type);

        for (Shape particle : this.aggregate) {
            distances.add(particle.getDistCenter(center));
        }

        return distances;
    }
}
