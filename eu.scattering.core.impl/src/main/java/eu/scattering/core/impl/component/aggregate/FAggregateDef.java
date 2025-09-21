package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.transfer.container.box.FBoxDouble.FBoxDouble;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FAggregateDef implements FAggregate {
    private static final String JSON_MAIN = "aggregate";
    private static final String JSON_PARTICLES = "particles";

    private final ScatFactory factory;

    private final FLayerCounter fLayer;

    private final Map<String, Double> density = new HashMap<>();

    private FAssembly<Shape> particles;
    private FArray<FMetaData> dipoles;

    private FAggregateDef(ScatFactory factory, FAssembly<Shape> particles, FArray<FMetaData> dipoles) {

        this.factory = factory;

        this.fLayer = factory.getFLayerCounter();

        this.particles = particles;
        this.dipoles = dipoles;

        this.density.put("", 1d);
    }

    public static FAggregate create(ScatFactory factory, FAssembly<Shape> particles, FArray<FMetaData> dipoles) {

        return new FAggregateDef(factory, particles, dipoles);
    }

    @Override
    public FAssembly<Shape> getParticles() {

        return this.particles.copy();
    }

    @Override
    public FAggregate setParticles(FAssembly<Shape> particles) {

        this.particles = particles.copy();

        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_PARTICLES, this.particles.toJSON());

        return json;
    }

    @Override
    public double getSurface() {
        List<Shape> field = getUniqueShapes();

        double surface = 0;
        for (Shape element : field) {

            if (element.overlaps(field) == 0) {
                surface += element.getSurfaceAlgebraic();
            } else {
                this.fLayer.reset();

                double surfaceUnit = element.fillSurfaceLayerOverlap(this.fLayer, field);

                surface += this.fLayer.get(0) * surfaceUnit;
            }
        }

        return surface;
    }

    @Override
    public double getSurface(double[] layers) {
        double surface = 0;

        Arrays.fill(layers, 0);

        List<Shape> field = getUniqueShapes();

        for (Shape element : field) {

            if (element.overlaps(field) == 0) {
                for (int i = 0 ; i < element.getLayerCount() ; i++) {
                    layers[i] += element.getLayerSurface(i);
                }
            } else {
                this.fLayer.reset();

                double surfaceUnit = element.fillSurfaceLayer(this.fLayer, field);

                for (int i = 0 ; i < element.getLayerCount() ; i++) {
                    layers[i] += this.fLayer.get(i) * surfaceUnit;
                }
            }
        }

        for (double layer : layers) {
            surface += layer;
        }

        return surface;
    }

    @Override
    public double getSurfaceRadius(double[] layers) {
        double resSurface = getSurface(layers);

        int i = 0;
        for (; i < layers.length ; i++) {
            layers[i] = factory.getFSphereHelper().getSurfaceRadius(layers[i]);
        }

        return factory.getFSphereHelper().getSurfaceRadius(resSurface);
    }

    @Override
    public double getVolume() {
        double volume = 0;

        Queue<Shape> queue = new LinkedList<>(this.particles.asList());

        queue.poll();

        for (Shape element : this.particles.asList()) {

            if (element.overlaps(queue) == 0) {
                volume += element.getVolumeAlgebraic();
            } else {
                this.fLayer.reset();

                double volumeUnit = element.fillVolumeLayerOverlap(fLayer, queue);

                volume += this.fLayer.get() * volumeUnit;
            }

            queue.poll();
        }

        return volume;
    }

    @Override
    public double getVolume(double[] layers) {
        double volume = 0;

        Arrays.fill(layers, 0);

        for (Shape shape : this.particles.asList()) {
            getVolumeSwitch(shape, layers);
        }

        for (double layer : layers) {
            volume += layer;
        }

        return volume;
    }

    @Override
    public double getVolumeRadius() {
        return 0;
    }

    @Override
    public double getVolumeRadius(double[] layers) {
        double volume = 0;

        double resVolume = getVolume(layers);

        int i = 0;
        for (; i < layers.length ; i++) {
            volume += layers[i];
            layers[i] = factory.getFSphereHelper().getVolumeRadius(volume);
        }

        return factory.getFSphereHelper().getVolumeRadius(resVolume);
    }

    @Override
    public FArrayMesh<FMetaData> getVolumeMesh() {
        this.dipoles.clear();

        for (Shape shape : this.particles.asList()) {
            shape.fillVolumeArray(this.dipoles, this.particles.asList());
        }

        FArrayMesh<FMetaData> mesh = this.dipoles.toFArrayMesh();

        mesh.deduplicate((a, b) -> b.getLayerIndex() < a.getLayerIndex());

        return mesh;
    }

    private void getVolumeSwitch(Shape shape, double[] volume) {

        if (shape.overlaps(this.particles.asList()) != 0) {
            getVolumeApproximate(shape, volume);
        } else {
            getVolumePrecise(shape, volume);
        }
    }

    private void getVolumePrecise(Shape shape, double[] volume) {

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume[i] += shape.getLayerVolume(i);
        }
    }

    private void getVolumeApproximate(Shape shape, double[] volume) {
        this.fLayer.reset();

        shape.fillVolumeLayer(this.fLayer, this.particles.asList());
        double volUnit = Math.pow(shape.getDelta(), 3);

        for (int i = 0 ; i < this.fLayer.size() ; i++) {
            volume[i] += fLayer.get(i) * volUnit;
        }
    }

    @Override
    public FPairPos3D getRange() {

        return this.particles.getRange();
    }

    @Override
    public void getSpatialCenter(FPoint center) {

        this.particles.getSpatialCenter(center);
    }

    @Override
    public void getSphericalCenter(FPoint center) {

        this.particles.getSphericalCenter(center);
    }

    @Override
    public void getMassCenter(FPoint center) {
        double volume = 0;

        center.set(0, 0, 0);

        for (Shape shape : this.particles.asList()) {
            volume += getMassCenterSwitch(center, shape);
        }

        center.setX(center.getX() / volume);
        center.setY(center.getY() / volume);
        center.setZ(center.getZ() / volume);
    }

    private double getMassCenterSwitch(FPoint center, Shape shape) {

        if (shape.overlaps(this.particles.asList()) == 0) {
            return getMassCenterPrecise(center, shape);
        }

        return getMassCenterApproximate(center, shape);
    }

    private double getMassCenterPrecise(FPoint center, Shape shape) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            String meta = shape.getMetaData().get(i).getMeta();

            if (!this.density.containsKey(shape.getMetaData().get(i).getMeta())) {
                throw new IllegalStateException("The density of '" + meta + "' is not defined");
            }

            mass += shape.getLayerVolume(i) * this.density.get(meta);
        }

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterApproximate(FPoint center, Shape shape) {
        this.dipoles.clear();

        double unitVolume = shape.fillVolumeArray(this.dipoles, this.particles.asList());

        FBoxDouble mass = factory.getFBoxDouble();

        this.dipoles.forEach((index, d0, d1, d2, data, meta) -> {

            if (!this.density.containsKey(meta.getMeta())) {
                throw new IllegalStateException("The density of '" + meta.getMeta() + "' is not defined");
            }

            double unitMass = unitVolume * this.density.get(meta.getMeta());

            center.setX(center.getX() + (d0 * unitMass));
            center.setY(center.getY() + (d1 * unitMass));
            center.setZ(center.getZ() + (d2 * unitMass));

            mass.setValue(mass.getValue() + unitMass);
        });

        return mass.getValue();
    }

    @Override
    public void positionCenter(FPoint center) {

        this.particles.translate(-center.getX(), -center.getY(), -center.getZ());
    }

    @Override
    public double getRadiusFrom(FPoint center) {
        double maxRadius = -1;

        for (Shape shape : this.particles) {
            double radius = shape.getDistCenter(center) + shape.getRadius();

            if (radius > maxRadius) {
                maxRadius = radius;
            }
        }
        return maxRadius;
    }

    @Override
    public double getRadiusFromZero() {
        double maxRadius = -1;

        for (Shape shape : this.particles) {
            double radius = shape.getDistCenter(0, 0, 0) + shape.getRadius();

            if (radius > maxRadius) {
                maxRadius = radius;
            }
        }
        return maxRadius;
    }

    @Override
    public double getRadiusOfGyration() {
        FPoint center = factory.getFPoint();
        FBoxDouble numerator = factory.getFBoxDouble();
        FBoxDouble denominator = factory.getFBoxDouble();

        getMassCenter(center);

        for (Shape shape : this.particles.asList()) {
            getRadiusOfGyrationShape(numerator, denominator, center, shape);
        }

        return Math.sqrt(numerator.getValue() / denominator.getValue());
    }

    private void getRadiusOfGyrationShape(FBoxDouble numerator, FBoxDouble denominator, FPoint center, Shape shape) {
        this.dipoles.clear();

        double unitVolume = shape.fillVolumeArray(this.dipoles, this.particles.asList());

        this.dipoles.forEach((index, d0, d1, d2, data, meta) -> {

            if (!this.density.containsKey(meta.getMeta())) {
                throw new IllegalStateException("The density of '" + meta.getMeta() + "' is not defined");
            }

            double mass = unitVolume * this.density.get(meta.getMeta());

            numerator.setValue(numerator.getValue() + (mass * Math.pow(center.getDistance(d0, d1, d2), 2)));
            denominator.setValue(denominator.getValue() + mass);
        });
    }

    @Override
    public double getOverlapFactor() {
        List<Double> layer = new ArrayList<>();

        for (Shape shape : this.particles) {
            getOverlapFactorSwitch(shape, layer);
        }

        return getOverlapFactorProcess(layer);
    }

    private void getOverlapFactorSwitch(Shape shape, List<Double> volume) {

        if (shape.overlaps(this.particles.asList()) == 0) {
            getOverlapFactorPrecise(shape, volume);
        } else {
            getOverlapFactorApproximate(shape, volume);
        }
    }

    private void getOverlapFactorPrecise(Shape shape, List<Double> layer) {

        if (layer.size() < 1) {
            layer.add(0d);
        }

        layer.set(0, layer.get(0) + shape.getVolumeAlgebraic());
    }

    private void getOverlapFactorApproximate(Shape shape, List<Double> volume) {
        this.fLayer.reset();

        shape.fillVolumeLayerOverlap(this.fLayer, this.particles.asList());

        double volUnit = Math.pow(shape.getDelta(), 3);

        while (fLayer.size() > volume.size()) {
            volume.add(0d);
        }

        for (int i = 0 ; i < fLayer.size() ; i++) {
            volume.set(i, volume.get(i) + (this.fLayer.get(i) * volUnit));
        }
    }

    private double getOverlapFactorProcess(List<Double> volume) {
        double volTmp;
        double volTotal = 0;
        double volOverlap = 0;

        for (int i = 0 ; i < volume.size() ; i++) {
            volTmp = volume.get(i) / (i + 1);

            volTotal += volTmp;

            if (i > 0) {
                volOverlap += volTmp;
            }
        }

        return volOverlap / volTotal;
    }

    @Override
    public double getOverlapFactorLegacy() {
        int oFacCount = 0;
        double oFacTotal = 0;
        Shape shapeA, shapeB;
        for (int i = 0 ; i < this.particles.size() ; i++) {
            shapeA = this.particles.asList().get(i);

            for (int j = i + 1 ; j < this.particles.size() ; j++) {
                shapeB = this.particles.asList().get(j);

                if (shapeA == shapeB) {
                    continue;
                }

                if (shapeA.repels(shapeB)) {
                    continue;
                }

                oFacTotal += getOverlapFactorLegacyPair(shapeA, shapeB);
                oFacCount += 1;
            }
        }

        if (oFacCount == 0) {
            return 0;
        }

        return oFacTotal / oFacCount;
    }

    private double getOverlapFactorLegacyPair(Shape shapeA, Shape shapeB) {

        double dist = shapeA.getDistCenter(shapeB);
        double oFacRaw = 1 - (dist / (shapeA.getRadius() + shapeB.getRadius()));

        if (oFacRaw > 1) {
            return 1;
        }

        if (oFacRaw < 0) {
            return 0;
        }

        return oFacRaw;
    }

    @Override
    public boolean isCompact() {

        if (this.particles.size() == 0) {
            return false;
        }

        List<Shape> processed = new ArrayList<>();

        isCompactRecurrence(this.particles.asList().get(0), processed);

        return this.particles.asList().size() == processed.size();
    }

    private void isCompactRecurrence(Shape shape, List<Shape> processed) {

        if (processed.contains(shape)) {
            return;
        }

        processed.add(shape);

        List<Shape> candidates = new ArrayList<>();
        shape.touchesOrOverlaps(this.particles.asList(), candidates);

        for (Shape candidate : candidates) {
            isCompactRecurrence(candidate, processed);
        }
    }

    @Override
    public boolean isSparse() {

        if (this.particles.size() == 0) {
            return false;
        }

        Queue<Shape> queue = new LinkedList<>(this.particles.asList());

        while (!queue.isEmpty()) {
            if (queue.poll().overlaps(queue) != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {
        List<Shape> candidates = new ArrayList<>();

        Queue<Shape> queue = new LinkedList<>(this.particles.asList());

        queue.poll();

        for (Shape shape : this.particles) {
            candidates.clear();

            shape.touchesOrOverlaps(queue, candidates);

            candidates.forEach(e -> consumer.accept(shape, e));

            queue.poll();
        }
    }

    @Override
    public FAggregate setMaterialDensity(String material, double density) {

        if (density <= 0) {
            throw new IllegalArgumentException("The density cannot be lower than zero");
        }

        this.density.put(material, density);

        return this;
    }

    private List<Shape> getUniqueShapes() {

        return this.particles.asList().stream()
                .distinct()
                .toList();
    }

    //--------------------------------------------------

    @Override
    public FAssembly<Shape> getRefParticles() {

        return this.particles;
    }

    @Override
    public FAggregate setRefParticles(FAssembly<Shape> particles) {

        this.particles = particles;

        return this;
    }

    @Override
    public FArray<FMetaData> getRefDipoles() {

        return this.dipoles;
    }

    @Override
    public FAggregate setRefDipoles(FArray<FMetaData> dipoles) {

        this.dipoles = dipoles;

        return this;
    }
}

// https://charmm-gui.org/?doc=lecture&module=scientific&lesson=10
