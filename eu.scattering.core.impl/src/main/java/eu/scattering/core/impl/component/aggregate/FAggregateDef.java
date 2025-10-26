package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.box.FBoxDouble;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.complex.FMaterial;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;

public class FAggregateDef implements FAggregate {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "aggregate";
    private static final String JSON_PARTICLES = "particles";
    private static final String JSON_CAPACITY = "capacity";
    private static final String JSON_MATERIAL = "material";

    private final ScatFactory factory;

    private FMaterial material;

    private FAssembly<Shape> particles;
    private FBuffer<FBufferData> buffer;

    private FAggregateDef(ScatFactory factory, FAssembly<Shape> particles, FBuffer<FBufferData> buffer) {

        this.factory = factory;

        this.particles = particles;
        this.buffer = buffer;

        this.material = FMaterial.create();
    }

    public static FAggregate create(ScatFactory factory, FAssembly<Shape> particles, FBuffer<FBufferData> buffer) {

        return new FAggregateDef(factory, particles, buffer);
    }

    public static FAggregate create(ScatFactory factory, String json) {

        try {
            return FAggregateDef.create(factory, new JSONObject(json));
        } catch (JSONException err){
            throw new IllegalArgumentException("Invalid json type");
        }
    }

    public static FAggregate create(ScatFactory factory, JSONObject json) {

        if (!json.getString(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("Invalid json header");
        }

        int capacity = json.getInt(JSON_CAPACITY);

        FBuffer<FBufferData> dipoles = factory.getFBuffer(capacity);
        FAssembly<Shape> particles = factory.getFAssembly();

        particles.set(json.getJSONObject(JSON_PARTICLES));

        FAggregate fAggregate = new FAggregateDef(factory, particles, dipoles);

        fAggregate.setRefMaterial(FMaterial.create(json.getJSONObject(JSON_MATERIAL)));

        return fAggregate;
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
        json.put(JSON_CAPACITY, this.buffer.capacity());
        json.put(JSON_MATERIAL, this.material.toJSON());
        json.put(JSON_PARTICLES, this.particles.toJSON());

        return json;
    }

    @Override
    public double getSurface() {
        FLayer fLayer = factory.getFLayerCounter();

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

    @Override
    public double getSurface(double[] layers) {
        FLayer fLayer = factory.getFLayerCounter();
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

    @Override
    public double getSurfaceRadius() {

        return factory.getFSphereHelper().getSurfaceRadius(getSurface());
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
        FLayer fLayer = factory.getFLayerCounter();
        double volume = 0;

        Queue<Shape> queue = new LinkedList<>(this.particles.asList());

        queue.poll();

        for (Shape shape : this.particles.asList()) {

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

        return factory.getFSphereHelper().getVolumeRadius(getVolume());
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
    public FMesh<FBufferData> getVolumeMesh() {
        this.buffer.clear();

        for (Shape shape : this.particles.asList()) {
            shape.fillVolumeArray(this.buffer, this.particles.asList());
        }

        FMesh<FBufferData> mesh = this.buffer.toFArrayMesh();

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
        FLayer fLayer = factory.getFLayerCounter();

        shape.fillVolumeLayer(fLayer, this.particles.asList());
        double volUnit = Math.pow(shape.getDelta(), 3);

        for (int i = 0; i < fLayer.size() ; i++) {
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
    public FPos3D getSpatialCenter() {
        FPoint center = factory.getFPoint();

        getSpatialCenter(center);

        return center.toFPos3D();
    }

    @Override
    public void getSphericalCenter(FPoint center) {

        this.particles.getSphericalCenter(center);
    }

    @Override
    public FPos3D getSphericalCenter() {
        FPoint center = factory.getFPoint();

        getSphericalCenter(center);

        return center.toFPos3D();
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

    @Override
    public FPos3D getMassCenter() {
        FPoint center = factory.getFPoint();

        getMassCenter(center);

        return center.toFPos3D();
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

            mass += shape.getLayerVolume(i) * this.material.getDensity(meta);
        }

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterApproximate(FPoint center, Shape shape) {
        this.buffer.clear();

        double unitVolume = shape.fillVolumeArray(this.buffer, this.particles.asList());

        FBoxDouble mass = factory.getFBoxDouble();

        this.buffer.forEach((index, d0, d1, d2, data, meta) -> {
            double unitMass = unitVolume * this.material.getDensity(meta.getMeta());

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
    public void positionCenter(FPos3D center) {

        this.particles.translate(-center.getD0(), -center.getD1(), -center.getD2());
    }

    @Override
    public double getRadius(double x, double y, double z) {

        double maxRadius = -1;

        for (Shape shape : this.particles) {
            double radius = shape.getDistCenter(x, y, z) + shape.getRadius();

            if (radius > maxRadius) {
                maxRadius = radius;
            }
        }

        return maxRadius;
    }

    @Override
    public double getRadius(FPoint center) {

        return getRadius(center.getX(), center.getY(), center.getZ());
    }

    @Override
    public double getRadius(FPos3D center) {

        return getRadius(center.getD0(), center.getD1(), center.getD2());
    }

    @Override
    public double getRadiusFromOrigin() {
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
        this.buffer.clear();

        double unitVolume = shape.fillVolumeArray(this.buffer, this.particles.asList());

        this.buffer.forEach((index, d0, d1, d2, data, meta) -> {
            double mass = unitVolume * this.material.getDensity(meta.getMeta());

            numerator.setValue(numerator.getValue() + (mass * Math.pow(center.getDistance(d0, d1, d2), 2)));
            denominator.setValue(denominator.getValue() + mass);
        });
    }

    @Override
    public double getRadiusOfGyrationMonodisperse() {
        double radius = 0;

        for (Shape shape: this.particles) {
            radius += shape.getRadius();
        }

        radius = radius / this.particles.size();

        FPoint massCenter = factory.getFPoint();

        for (Shape shape: this.particles) {
            massCenter.setX(massCenter.getX() + shape.getCenterX());
            massCenter.setY(massCenter.getY() + shape.getCenterY());
            massCenter.setZ(massCenter.getZ() + shape.getCenterZ());
        }

        massCenter.setX(massCenter.getX() / this.particles.size());
        massCenter.setY(massCenter.getY() / this.particles.size());
        massCenter.setZ(massCenter.getZ() / this.particles.size());

        double numerator = 0;

        for (Shape shape: this.particles) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / this.particles.size()) + (0.6 * radius));
    }

    @Override
    public double getRadiusOfGyrationPolydisperse() {
        double avgRadius = 0;

        for (Shape shape: this.particles) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / this.particles.size();

        FPoint massCenter = factory.getFPoint();

        double massTotal = 0;
        for (Shape shape: this.particles) {
            double massParticle = getParticleMass(shape);

            massCenter.setX(massCenter.getX() + (massParticle * shape.getCenterX()));
            massCenter.setY(massCenter.getY() + (massParticle * shape.getCenterY()));
            massCenter.setZ(massCenter.getZ() + (massParticle * shape.getCenterZ()));

            massTotal += massParticle;
        }

        massCenter.setX(massCenter.getX() / massTotal);
        massCenter.setY(massCenter.getY() / massTotal);
        massCenter.setZ(massCenter.getZ() / massTotal);

        double numerator = 0;

        for (Shape shape: this.particles) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / this.particles.size()) + (0.6 * avgRadius));
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
        FLayer fLayer = factory.getFLayerCounter();

        shape.fillVolumeLayerOverlap(fLayer, this.particles.asList());

        double volUnit = Math.pow(shape.getDelta(), 3);

        while (fLayer.size() > volume.size()) {
            volume.add(0d);
        }

        for (int i = 0; i < fLayer.size() ; i++) {
            volume.set(i, volume.get(i) + (fLayer.get(i) * volUnit));
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
    public double getOverlapFactorLinear() {
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

    @Override
    public double getBoxDimension() {
        FPairPos3D range = getRange();

        FPlot2D sData = factory.getFPlot2D();
        FStat1D sParticles = factory.getFStat1D();

        getParticles().forEach(e -> sParticles.add(e.getRadius()));

        FPos3D center = getSpatialCenter();

        double cutoffOuter = getRadius(center) * 2;
        double cutoffInner = sParticles.min() * 0.25;

        double maxRadius = sParticles.max();

        double step = sParticles.min() * 0.25;

        double size = cutoffOuter;
        while (size >= cutoffInner) {
            getBDimStep(sData, range, maxRadius, size);
            size *= 0.5;
        }
        return getBDimAnalyze(sData);
    }

    private void getBDimStep(FPlot2D data, FPairPos3D range, double rMax, double step) {
        FSphereHelper helper = factory.getFSphereHelper();

        double hStep = step * 0.5;
        double cutoff = Math.pow((hStep * Math.cbrt(3)) + rMax, 2);

        double initX = Math.ceil(range.getPosA().getD0() / step) * step;
        double initY = Math.ceil(range.getPosA().getD1() / step) * step;
        double initZ = Math.ceil(range.getPosA().getD2() / step) * step;

//        double initX = range.getPosA().getD0();
//        double initY = range.getPosA().getD1();
//        double initZ = range.getPosA().getD2();

        int sum = 0;
        for (double x = initX + hStep; x <= range.getPosB().getD0() + hStep ; x += step) {
            for (double y = initY + hStep; y <= range.getPosB().getD1() + hStep ; y += step) {
                for (double z = initZ + hStep; z <= range.getPosB().getD2() + hStep ; z += step) {
                    for (Shape shape : getRefParticles()) {
                        if (shape.getDistCenterP2(x, y, z) > cutoff) {
                            continue;
                        }
                        if (helper.isIntersecting(shape, x, y, z, step)) {
                            sum++;
                            break;
                        }
                    }
                }
            }
        }

//        if (data.size() > 1 && data.getY(data.size() - 1) > sum) {
//            data.add(step, data.getY(data.size() - 1));
//        } else {
            data.add(step, sum);
//        }
    }

    private double getBDimAnalyze(FPlot2D data) {



        data.mutateY((x, y) -> Math.log(y));
        data.mutateX((x, y) -> Math.log(1 / x));

//        data.filter((x, y) -> y > 1);

//        data.getInterpolator().setMethod(FPlot2DInterpolator.Method.LINEAR);
//        data.interpolate(data.size());


        FPlot2D ref = data.copy();
        FPos2D reg = ref.simpleLinearRegression();

        String plot = factory.getStatisticsExporter().toPythonPlotlyLinear(data, ref);
        return reg.getD0();

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

    private List<Shape> getUniqueShapes() {
        ArrayList<Shape> results = new ArrayList<>();

        this.particles.forEach(e -> {
            if (results.stream().noneMatch(e::isExact)) {
                results.add(e);
            }
        });

        return results;
    }

    private double getParticleMass(Shape shape) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            FBufferData meta = shape.getMetaData().get(i);

            mass += shape.getLayerVolume(i) * this.material.getDensity(meta.getMeta());
        }

        return mass;
    }

    //--------------------------------------------------

    @Override
    public FAggregate copy() {
        FAggregate copy = factory.getFAggregate(getRefBuffer().capacity());

        copy.setRefParticles(getRefParticles().copy());
        copy.setRefMaterial(getRefMaterial().copy());

        return null;
    }

    @Override
    public boolean isExact(FAggregate aggregate) {

        if (!isExactData(aggregate)) {
            return false;
        }

        if (!getRefMaterial().isEqual(aggregate.getRefMaterial())) {
            return false;
        }

        return getRefBuffer().capacity() == aggregate.getRefBuffer().capacity();
    }

    @Override
    public boolean isExactData(FAggregate aggregate) {

        return getRefParticles().isExact(aggregate.getRefParticles());
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
    public FBuffer<FBufferData> getRefBuffer() {

        return this.buffer;
    }

    @Override
    public FAggregate setRefBuffer(FBuffer<FBufferData> dipoles) {

        this.buffer = dipoles;

        return this;
    }

    @Override
    public FMaterial getRefMaterial() {

        return this.material;
    }

    @Override
    public FAggregate setRefMaterial(FMaterial material) {

        this.material = material;

        return this;
    }
}

// https://charmm-gui.org/?doc=lecture&module=scientific&lesson=10
