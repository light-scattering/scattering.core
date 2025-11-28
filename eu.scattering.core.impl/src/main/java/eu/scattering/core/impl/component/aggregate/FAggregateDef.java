package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.box.FBoxDouble;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FAggregateDef implements FAggregate {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "aggregate";
    private static final String JSON_PARTICLES = "particles";
    private static final String JSON_CAPACITY = "capacity";
    private static final String JSON_MATERIAL = "material";

    private final ScatFactory factory;

    private FAssembly<Shape> particles;

    private FMaterial material;
    private FBuffer<FBufferData> buffer;

    private FAggregateDef(ScatFactory factory, FAssembly<Shape> refParticles) {

        this.factory = factory;
        this.particles = refParticles;
    }

    public static FAggregate create(ScatFactory factory, FAssembly<Shape> refParticles) {

        return new FAggregateDef(factory, refParticles);
    }

    public static FAggregate create(ScatFactory factory, JSONObject json) {

        if (!json.getString(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("Invalid JSON header (FAggregate)");
        }

        FAggregate fAggregate = new FAggregateDef(factory, factory.getFAssembly(json.getJSONObject(JSON_PARTICLES)));

        if (json.has(JSON_MATERIAL)) {
            fAggregate.setRefFMaterial(factory.getFMaterial(json.getJSONObject(JSON_MATERIAL)));
        }

        if (json.has(JSON_CAPACITY)) {
            fAggregate.addFBuffer(json.getInt(JSON_CAPACITY));
        }

        return fAggregate;
    }

    @Override
    public int size() {

        return getRefParticles().size();
    }

    @Override
    public FAggregate addFBuffer(int capacity) {

        if (capacity < 1) {
            throw new IllegalArgumentException("The buffer must consist of at least one element");
        }

        setRefFBuffer(supplyFBuffer(capacity));

        return this;
    }

    @Override
    public FAggregate addFMaterial() {

        setRefFMaterial(supplyFMaterial());

        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        if (getRefFBuffer() != null) {
            json.put(JSON_CAPACITY, getRefFBuffer().capacity());
        }

        if (getRefFMaterial() != null) {
            json.put(JSON_MATERIAL, getRefFMaterial().toJSON());
        }

        json.put(JSON_PARTICLES, getRefParticles().toJSON());

        return json;
    }

    @Override
    public double getSurface() {
        FLayer fLayer = supplyFLayer();

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
        FLayer fLayer = supplyFLayer();
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

        return getFSphereHelper().getSurfaceRadius(getSurface());
    }

    @Override
    public double getSurfaceRadius(double[] layers) {
        double resSurface = getSurface(layers);

        int i = 0;
        for (; i < layers.length ; i++) {
            layers[i] = getFSphereHelper().getSurfaceRadius(layers[i]);
        }

        return getFSphereHelper().getSurfaceRadius(resSurface);
    }

    @Override
    public double getVolume() {
        FLayer fLayer = supplyFLayer();
        double volume = 0;

        Queue<Shape> queue = new LinkedList<>(getRefParticles().asList());

        queue.poll();

        for (Shape shape : getRefParticles().asList()) {

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

        for (Shape shape : getRefParticles()) {
            getVolumeMethod(shape, layers);
        }

        for (double layer : layers) {
            volume += layer;
        }

        return volume;
    }

    @Override
    public double getVolumeRadius() {

        return getFSphereHelper().getVolumeRadius(getVolume());
    }

    @Override
    public double getVolumeRadius(double[] layers) {
        double volume = 0;

        double resVolume = getVolume(layers);

        int i = 0;
        for (; i < layers.length ; i++) {
            volume += layers[i];
            layers[i] = getFSphereHelper().getVolumeRadius(volume);
        }

        return factory.getFSphereHelper().getVolumeRadius(resVolume);
    }

    @Override
    public FMesh<FBufferData> getVolumeMesh() {

        validateFBuffer();

        getRefFBuffer().clear();

        for (Shape shape : getRefParticles()) {
            shape.fillVolumeArray(getRefFBuffer(), getRefParticles().asList());
        }

        FMesh<FBufferData> mesh = getRefFBuffer().toFArrayMesh();

        mesh.deduplicate((a, b) -> b.getLayerIndex() < a.getLayerIndex());

        return mesh;
    }

    private void getVolumeMethod(Shape shape, double[] volume) {

        if (shape.overlaps(getRefParticles().asList()) != 0) {
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
        FLayer fLayer = supplyFLayer();

        shape.fillVolumeLayer(fLayer, getRefParticles().asList());
        double volUnit = Math.pow(shape.getDelta(), 3);

        for (int i = 0; i < fLayer.size() ; i++) {
            volume[i] += fLayer.get(i) * volUnit;
        }
    }

    @Override
    public FPairPos3D getBoundary() {

        return getRefParticles().getBoundary();
    }

    @Override
    public FPos3D getLength() {
        FPairPos3D range = getBoundary();

        double lengthX = range.getPosB().getD0() - range.getPosA().getD0();
        double lengthY = range.getPosB().getD1() - range.getPosA().getD1();
        double lengthZ = range.getPosB().getD2() - range.getPosA().getD2();

        return factory.getFPos3D(lengthX, lengthY, lengthZ);
    }

    @Override
    public double getLength(Axis type) {
        FPos3D length = getLength();

        return switch (type) {
            case X -> length.getD0();
            case Y -> length.getD1();
            case Z -> length.getD2();
            case MAX -> Math.max(length.getD0(), Math.max(length.getD1(), length.getD2()));
            case MIN -> Math.min(length.getD0(), Math.min(length.getD1(), length.getD2()));
        };
    }

    @Override
    public void getSpatialCenter(FPoint in) {

        getRefParticles().getSpatialCenter(in);
    }

    @Override
    public FPos3D getSpatialCenter() {
        FPoint center = supplyFPoint();

        getSpatialCenter(center);

        return center.toFPos3D();
    }

    @Override
    public void getSphericalCenter(FPoint in) {

        getRefParticles().getSphericalCenter(in);
    }

    @Override
    public FPos3D getSphericalCenter() {
        FPoint center = supplyFPoint();

        getSphericalCenter(center);

        return center.toFPos3D();
    }

    @Override
    public void getMassCenter(FPoint in) {
        double volume = 0;

        in.set(0, 0, 0);

        for (Shape shape : getRefParticles().asList()) {
            volume += getMassCenterMethod(in, shape);
        }

        in.setX(in.getX() / volume);
        in.setY(in.getY() / volume);
        in.setZ(in.getZ() / volume);
    }

    @Override
    public FPos3D getMassCenter() {
        FPoint center = supplyFPoint();

        getMassCenter(center);

        return center.toFPos3D();
    }

    private double getMassCenterMethod(FPoint center, Shape shape) {

        if (shape.overlaps(getRefParticles().asList()) == 0) {
            return getMassCenterMethodPrecise(center, shape);
        }

        return getMassCenterMethodApprox(center, shape);
    }

    private double getMassCenterMethodPrecise(FPoint center, Shape shape) {

        if (getRefFMaterial() == null) {
            return getMassCenterMethodPreciseMath(center, shape);
        }

        return getMassCenterMethodPrecisePhys(center, shape);
    }

    private double getMassCenterMethodPreciseMath(FPoint center, Shape shape) {
        double volume = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume += shape.getLayerVolume(i);
        }

        center.setX(center.getX() + (shape.getCenterX() * volume));
        center.setY(center.getY() + (shape.getCenterY() * volume));
        center.setZ(center.getZ() + (shape.getCenterZ() * volume));

        return volume;
    }

    private double getMassCenterMethodPrecisePhys(FPoint center, Shape shape) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            String meta = shape.getMetaData().get(i).getMeta();

            mass += shape.getLayerVolume(i) * getRefFMaterial().getDensity(meta);
        }

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterMethodApprox(FPoint center, Shape shape) {

        if (getRefFMaterial() == null) {
            return getMassCenterMethodApproxMath(center, shape);
        }

        return getMassCenterMethodApproxPhys(center, shape);
    }

    private double getMassCenterMethodApproxMath(FPoint center, Shape shape) {
        getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(getRefFBuffer(), getRefParticles().asList());

        FBoxDouble volume = supplyFBoxDouble();

        getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            center.setX(center.getX() + (d0 * unitVolume));
            center.setY(center.getY() + (d1 * unitVolume));
            center.setZ(center.getZ() + (d2 * unitVolume));

            volume.setValue(volume.getValue() + unitVolume);
        });

        return volume.getValue();
    }

    private double getMassCenterMethodApproxPhys(FPoint center, Shape shape) {
        getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(getRefFBuffer(), getRefParticles().asList());

        FBoxDouble mass = supplyFBoxDouble();

        getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            double unitMass = unitVolume * getRefFMaterial().getDensity(meta.getMeta());

            center.setX(center.getX() + (d0 * unitMass));
            center.setY(center.getY() + (d1 * unitMass));
            center.setZ(center.getZ() + (d2 * unitMass));

            mass.setValue(mass.getValue() + unitMass);
        });

        return mass.getValue();
    }

    @Override
    public void positionCenter(FPoint center) {

        getRefParticles().translate(-center.getX(), -center.getY(), -center.getZ());
    }

    @Override
    public void positionCenter(FPos3D center) {

        getRefParticles().translate(-center.getD0(), -center.getD1(), -center.getD2());
    }

    @Override
    public double getRadius(double x, double y, double z) {
        double maxRadius = -1;

        for (Shape shape : getRefParticles()) {
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

        for (Shape shape : getRefParticles()) {
            double radius = shape.getDistCenter(0, 0, 0) + shape.getRadius();

            if (radius > maxRadius) {
                maxRadius = radius;
            }
        }
        return maxRadius;
    }

    @Override
    public double getRadiusOfGyration(RoG type) {

        return switch (type) {
            case COMPLEX -> getRadiusOfGyrationComplex();
            case SIMPLE_MONO -> getRadiusOfGyrationSimpleMono();
            case SIMPLE_POLY -> getRadiusOfGyrationSimplePoly();
            case SIMPLE_FILIPPOV -> getRadiusOfGyrationSimpleFilippov();
        };
    }

    private double getRadiusOfGyrationComplex() {
        validateFBuffer();

        FBoxDouble numerator = supplyFBoxDouble();
        FBoxDouble denominator = supplyFBoxDouble();

        FPoint center = supplyFPoint();

        getMassCenter(center);

        if (getRefFMaterial() == null) {
            for (Shape shape : getRefParticles()) {
                getRadiusOfGyrationComplexShapeMath(numerator, denominator, center, shape);
            }
        } else {
            for (Shape shape : getRefParticles()) {
                getRadiusOfGyrationComplexShapePhys(numerator, denominator, center, shape);
            }
        }

        return Math.sqrt(numerator.getValue() / denominator.getValue());
    }

    private void getRadiusOfGyrationComplexShapeMath(FBoxDouble numerator, FBoxDouble denominator, FPoint center, Shape shape) {
        getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(getRefFBuffer(), getRefParticles().asList());

        getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            numerator.setValue(numerator.getValue() + (unitVolume * Math.pow(center.getDistance(d0, d1, d2), 2)));
            denominator.setValue(denominator.getValue() + unitVolume);
        });
    }

    private void getRadiusOfGyrationComplexShapePhys(FBoxDouble numerator, FBoxDouble denominator, FPoint center, Shape shape) {
        getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(getRefFBuffer(), getRefParticles().asList());

        getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            double mass = unitVolume * getRefFMaterial().getDensity(meta.getMeta());

            numerator.setValue(numerator.getValue() + (mass * Math.pow(center.getDistance(d0, d1, d2), 2)));
            denominator.setValue(denominator.getValue() + mass);
        });
    }

    private double getRadiusOfGyrationSimpleFilippov() {
        double avgRadius = 0;

        for (Shape shape: getRefParticles()) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / getRefParticles().size();

        FPoint massCenter = supplyFPoint();

        for (Shape shape: getRefParticles()) {
            massCenter.setX(massCenter.getX() + shape.getCenterX());
            massCenter.setY(massCenter.getY() + shape.getCenterY());
            massCenter.setZ(massCenter.getZ() + shape.getCenterZ());
        }

        massCenter.setX(massCenter.getX() / getRefParticles().size());
        massCenter.setY(massCenter.getY() / getRefParticles().size());
        massCenter.setZ(massCenter.getZ() / getRefParticles().size());

        double numerator = 0;

        for (Shape shape: getRefParticles()) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / getRefParticles().size()) + Math.pow(avgRadius, 2));
    }

    private double getRadiusOfGyrationSimpleMono() {
        double avgRadius = 0;

        for (Shape shape: getRefParticles()) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / getRefParticles().size();

        FPoint massCenter = supplyFPoint();

        for (Shape shape: getRefParticles()) {
            massCenter.setX(massCenter.getX() + shape.getCenterX());
            massCenter.setY(massCenter.getY() + shape.getCenterY());
            massCenter.setZ(massCenter.getZ() + shape.getCenterZ());
        }

        massCenter.setX(massCenter.getX() / getRefParticles().size());
        massCenter.setY(massCenter.getY() / getRefParticles().size());
        massCenter.setZ(massCenter.getZ() / getRefParticles().size());

        double numerator = 0;

        for (Shape shape: getRefParticles()) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / getRefParticles().size()) + (0.6 * avgRadius));
    }

    private double getRadiusOfGyrationSimplePoly() {
        double avgRadius = 0;

        for (Shape shape: getRefParticles()) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / getRefParticles().size();

        FPoint massCenter = supplyFPoint();

        double massTotal = getRefFMaterial() == null ?
                getRadiusOfGyrationSimplePolyMath(massCenter) : getRadiusOfGyrationSimplePolyPhys(massCenter);

        massCenter.setX(massCenter.getX() / massTotal);
        massCenter.setY(massCenter.getY() / massTotal);
        massCenter.setZ(massCenter.getZ() / massTotal);

        double numerator = 0;

        for (Shape shape: getRefParticles()) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / getRefParticles().size()) + (0.6 * avgRadius));
    }

    private double getRadiusOfGyrationSimplePolyMath(FPoint massCenter) {
        double volumeTotal = 0;

        for (Shape shape: getRefParticles()) {
            double volumeParticle = shape.getVolumeAlgebraic();

            massCenter.setX(massCenter.getX() + (volumeParticle * shape.getCenterX()));
            massCenter.setY(massCenter.getY() + (volumeParticle * shape.getCenterY()));
            massCenter.setZ(massCenter.getZ() + (volumeParticle * shape.getCenterZ()));

            volumeTotal += volumeParticle;
        }

        return volumeTotal;
    }

    private double getRadiusOfGyrationSimplePolyPhys(FPoint massCenter) {
        double massTotal = 0;

        for (Shape shape: getRefParticles()) {
            double massParticle = getParticleMass(shape);

            massCenter.setX(massCenter.getX() + (massParticle * shape.getCenterX()));
            massCenter.setY(massCenter.getY() + (massParticle * shape.getCenterY()));
            massCenter.setZ(massCenter.getZ() + (massParticle * shape.getCenterZ()));

            massTotal += massParticle;
        }

        return massTotal;
    }

    private double getParticleMass(Shape shape) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            FBufferData meta = shape.getMetaData().get(i);

            mass += shape.getLayerVolume(i) * getRefFMaterial().getDensity(meta.getMeta());
        }

        return mass;
    }

    @Override
    public double getOverlapFactor(OF type) {

        return switch(type) {
            case VOLUMETRIC -> getOverlapFactorVolumetric();
            case LINEAR -> getOverlapFactorLinear();
        };
    }

    private double getOverlapFactorVolumetric() {
        List<Double> layer = new ArrayList<>();

        for (Shape shape : getRefParticles()) {
            getOverlapFactorVolumetricMethod(shape, layer);
        }

        return getOverlapFactorVolumetricProcess(layer);
    }

    private void getOverlapFactorVolumetricMethod(Shape shape, List<Double> volume) {

        if (shape.overlaps(getRefParticles().asList()) == 0) {
            getOverlapFactorVolumetricMethodPrecise(shape, volume);
        } else {
            getOverlapFactorVolumetricMethodApprox(shape, volume);
        }
    }

    private void getOverlapFactorVolumetricMethodPrecise(Shape shape, List<Double> layer) {

        if (layer.size() < 1) {
            layer.add(0d);
        }

        layer.set(0, layer.get(0) + shape.getVolumeAlgebraic());
    }

    private void getOverlapFactorVolumetricMethodApprox(Shape shape, List<Double> volume) {
        FLayer fLayer = supplyFLayer();

        shape.fillVolumeLayerOverlap(fLayer, getRefParticles().asList());

        double volUnit = Math.pow(shape.getDelta(), 3);

        while (fLayer.size() > volume.size()) {
            volume.add(0d);
        }

        for (int i = 0; i < fLayer.size() ; i++) {
            volume.set(i, volume.get(i) + (fLayer.get(i) * volUnit));
        }
    }

    private double getOverlapFactorVolumetricProcess(List<Double> volume) {
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

    private double getOverlapFactorLinear() {
        int oFacCount = 0;
        double oFacTotal = 0;
        Shape shapeA, shapeB;
        for (int i = 0 ; i < getRefParticles().size() ; i++) {
            shapeA = getRefParticles().asList().get(i);

            for (int j = i + 1 ; j < getRefParticles().size() ; j++) {
                shapeB = getRefParticles().asList().get(j);

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
    public FStat getPairDistance() {
        FStat distance = supplyFStat();
        List<Shape> particles = getRefParticles().asList();

        for (int i = 0 ; i < getRefParticles().size() - 1 ; i++) {
            for (int j = i + 1 ; j < getRefParticles().size() ; j++) {
                distance.add(particles.get(i).getDistCenter(particles.get(j)));
            }
        }

        return distance;
    }

    @Override
    public FPlot getPairDistanceFunction() {
        FStat distance = getPairDistance();
        FStat radius = getParticleRadius();

        double max = distance.max();
        int steps = (int) (max / radius.min());

        return distance.toFPlotHistogram(0, max, steps);
    }

    @Override
    public FStat getCoordinationNumber() {
        FStat coordination = factory.getFStat();

        for (Shape shape : getRefParticles()) {
            coordination.add(shape.touchesOrOverlaps(getRefParticles()));
        }

        return coordination;
    }

    @Override
    public FPlot getCoordinationNumberFunction() {
        FStat coordination = getCoordinationNumber();

        double max = coordination.max();

        return coordination.toFPlotHistogram(1, max, (int) max - 1);
    }

    @Override
    public FStat getTripletAngle() {
        FStat angle = supplyFStat();

        List<Shape> neighbours = new LinkedList<>();
        FVector vecA = supplyFVector();
        FVector vecB = supplyFVector();

        for (Shape shape : getRefParticles()) {
            shape.touchesOrOverlaps(getRefParticles(), neighbours);

            if (neighbours.size() < 2) {
                continue;
            }

            for (int i = 0 ; i < neighbours.size() - 1 ; i++) {
                for (int j = i + 1 ; j < neighbours.size() ; j++) {
                    vecA.setBase(shape.getRefCenter());
                    vecB.setBase(shape.getRefCenter());

                    vecA.setHead(neighbours.get(i).getRefCenter());
                    vecB.setHead(neighbours.get(j).getRefCenter());

                    angle.add(vecA.getAngle(vecB));
                }
            }
        }

        return angle;
    }

    @Override
    public FPlot getTripletAngleFunction() {

        return getTripletAngle().toFPlotHistogram(0, Math.PI, 180);
    }

    @Override
    public FPlot getDensityCorrelationFunction(boolean log) {
        FSphereHelper helper = getFSphereHelper();

        FStat distances = getPairDistance();

        double min = distances.min();
        double max = distances.max();
        double delta = min * 0.5;

        FPlot results = supplyFPlot();

        double step = min;
        while (step <= max) {
            results.add(step, 0);
            step = log ? step * 1.1 : step + delta;
        }

        for (double distance : distances) {
            for (int i = 0 ; i < results.size() ; i++) {

                if (Math.abs(distance - results.getX(i)) < delta) {
                    results.setY(i, results.getY(i) + 1);
                }

                if (distance + delta < results.getX(i)) {
                    break;
                }
            }
        }

        for (int i = 0 ; i < results.size() ; i++) {
            double element = results.getX(i);
            double volume = helper.getVolumeRing(element - delta, element + delta);

            results.setY(i, results.getY(i) / volume);
        }

        results.filter((x, y) -> x > 0 && y > 0);

        return results;
    }

    @Override
    public FPlot getBoxCoverageFunction(boolean log) {
        FPlot results = supplyFPlot();

        double radius = getParticleRadius().mean();

        double cutoffInner = radius * 2;
        double cutoffOuter = getLength(Axis.MAX);

        results.add(cutoffOuter, 1);

        double size = log ? cutoffOuter * 0.5 : cutoffOuter - radius;
        while (size >= cutoffInner) {
            getBoxCoverageStep(results, size);
            size = log ? size * 0.5 : size - radius;
        }

        return results;
    }

    @Override
    public double getFractalDimension(Dim type) {

        return switch (type) {
            case BOX -> getBoxCoverageAnalyze(getBoxCoverageFunction(true));
            case CORRELATION -> getDensityCorrelationAnalyze(getDensityCorrelationFunction(true));
        };
    }

    private void getBoxCoverageStep(FPlot data, double step) {
        FSphereHelper helper = getFSphereHelper();

        FPos3D origin = getBoundary().getPosA();
        double scale = 1 / step;

        Queue<Shape> particles = new LinkedList<>(getRefParticles().copy().asList());
        particles.forEach(e -> e.translate(-origin.getD0(), -origin.getD1(), -origin.getD2()));
        particles.forEach(e -> e.scalePosition(scale).scaleSize(scale));

        int sum = 0;
        while (particles.size() > 0) {
            Shape particle = particles.poll();

            List<Shape> neighbours = new ArrayList<>(particles.size());

            particles.forEach(e -> {
                if (e.getDistCenterP2(particle) < Math.pow(e.getRadius() + particle.getRadius() + 2, 2)) {
                    neighbours.add(e);
                }
            });

            double coreMinX = particle.getCenterX() - particle.getRadius() + EPSILON;
            int minX = (int) Math.floor(coreMinX);
            double coreMinY = particle.getCenterY() - particle.getRadius() + EPSILON;
            int minY = (int) Math.floor(coreMinY);
            double coreMinZ = particle.getCenterZ() - particle.getRadius() + EPSILON;
            int minZ = (int) Math.floor(coreMinZ);

            double coreMaxX = particle.getCenterX() + particle.getRadius() - EPSILON;
            int maxX = (int) Math.ceil(coreMaxX);
            double coreMaxY = particle.getCenterY() + particle.getRadius() - EPSILON;
            int maxY = (int) Math.ceil(coreMaxY);
            double coreMaxZ = particle.getCenterZ() + particle.getRadius() - EPSILON;
            int maxZ = (int) Math.ceil(coreMaxZ);

            for (int x = minX ; x < maxX ; x++) {
                for (int y = minY ; y < maxY ; y++) {

                    next:
                    for (int z = minZ ; z < maxZ ; z++) {
                        if (helper.intersectsCube(particle, x + 0.5, y + 0.5, z + 0.5, 1)) {
                            for (Shape neighbour : neighbours) {
                                if (helper.intersectsCube(neighbour, x + 0.5, y + 0.5, z + 0.5, 1)) {
                                    continue next;
                                }
                            }

                            sum ++;
                        }
                    }
                }
            }
        }

        data.add(step, sum);
    }

    private double getBoxCoverageAnalyze(FPlot data) {
        data.mutateY((x, y) -> Math.log(y));
        data.mutateX((x, y) -> Math.log(1 / x));

        data.filter((x, y) -> y > 0);

        FPoly regression = data.reg().poly(1);

        return regression.at(1);
    }

    private double getDensityCorrelationAnalyze(FPlot data) {
        double distMin = data.getX(0);

        data.filter((x, y) -> x > 2.1 * distMin);
        data.filter((x, y) -> y > 0);


        double midpoint = data.getX((int) (data.size() * 0.75));

//        data.filter((x, y) -> x < midpoint);

//        data.mutateY((x, y) -> y * 2);
        data.mutate((a, b) -> {
            a.log(10);
            b.log(10);
        });

//        FStat1D dataY = data.getStatY();
//        double min = dataY.min();
//        dataY.mutate((x) -> x - min);
//
//        dataY.replaceWithNaN(false, (y0, y1) -> {
//
//            return Math.abs((y1 - y0) / y0) < 0.05;
//        }
//        );

//        data.setStatY(dataY);



        FPoly regression = data.reg().poly(1);
        FPoly regOki = data.reg().fitSlope((int) (data.size() * 0.75));
        FPlot reg = data.copy();
        reg.setY(regOki);

        String model = factory.getFExportEngine().getPlotContext()
                .setRangeX(0,3)
                .setRangeY(-5, 2)
                .setAnnotation("Test data")
                .exportPythonPlotlyLinear(data, reg);

        return 3 + regOki.at(1);
    }
    @Override
    public boolean isCompact() {

        if (getRefParticles().size() == 0) {
            return false;
        }

        List<Shape> processed = new ArrayList<>();

        isCompactRecurrence(getRefParticles().asList().get(0), processed);

        return getRefParticles().asList().size() == processed.size();
    }

    private void isCompactRecurrence(Shape shape, List<Shape> processed) {

        if (processed.contains(shape)) {
            return;
        }

        processed.add(shape);

        List<Shape> candidates = new ArrayList<>();
        shape.touchesOrOverlaps(getRefParticles().asList(), candidates);

        for (Shape candidate : candidates) {
            isCompactRecurrence(candidate, processed);
        }
    }

    @Override
    public boolean isSparse() {

        if (getRefParticles().size() == 0) {
            return false;
        }

        Queue<Shape> queue = new LinkedList<>(getRefParticles().asList());

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

        Queue<Shape> queue = new LinkedList<>(getRefParticles().asList());

        queue.poll();

        for (Shape shape : getRefParticles()) {
            candidates.clear();

            shape.touchesOrOverlaps(queue, candidates);

            candidates.forEach(e -> consumer.accept(shape, e));

            queue.poll();
        }
    }

    @Override
    public void setEpsilon(double epsilon) {

        getRefParticles().forEach(e -> e.setEpsilon(epsilon));
    }

    @Override
    public void setDelta(double delta) {

        getRefParticles().forEach(e -> e.setDelta(delta));
    }

    @Override
    public FStat getParticleRadius() {
        FStat particles = supplyFStat();

        getRefParticles().forEach(e -> particles.add(e.getRadius()));

        return particles;
    }

    private List<Shape> getUniqueShapes() {
        ArrayList<Shape> results = new ArrayList<>();

        getRefParticles().forEach(e -> {
            if (results.stream().noneMatch(e::isExact)) {
                results.add(e);
            }
        });

        return results;
    }

    private void validateFBuffer() {

        if (getRefFBuffer() == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }
    }

    // -------------------------------------------------------------------------------------------------

    private FBuffer<FBufferData> supplyFBuffer(int capacity) {

        return factory.getFBuffer(capacity);
    }

    private FMaterial supplyFMaterial() {

        return factory.getFMaterial();
    }

    private FLayer supplyFLayer() {

        return factory.getFLayer();
    }

    private FStat supplyFStat() {

        return factory.getFStat();
    }

    private FPlot supplyFPlot() {

        return factory.getFPlot();
    }

    private FPlot supplyFPlot(FLayer fLayer) {

        return factory.getFPlot(fLayer);
    }

    private FPoint supplyFPoint() {

        return factory.getFPoint();
    }

    private FVector supplyFVector() {

        return factory.getFVector();
    }

    private FBoxDouble supplyFBoxDouble() {

        return factory.getFBoxDouble();
    }

    private FSphereHelper getFSphereHelper() {

        return factory.getFSphereHelper();
    }

    private FTrigHelper getFTrigHelper() {

        return factory.getFTrigHelper();
    }

    //--------------------------------------------------

    @Override
    public FAggregate copy() {
        FAggregate copy = FAggregateDef.create(this.factory, getRefParticles().copy());

        copy.setRefFMaterial(getRefFMaterial().copy());
        copy.setRefFBuffer(supplyFBuffer(getRefFBuffer().capacity()));

        return copy;
    }

    @Override
    public boolean isExact(FAggregate aggregate) {

        if (!isExactData(aggregate)) {
            return false;
        }

        if (getRefFMaterial() == null && aggregate.getRefFMaterial() != null) {
            return false;
        }

        if (getRefFMaterial() != null && aggregate.getRefFMaterial() == null) {
            return false;
        }

        if (getRefFMaterial() != null && aggregate.getRefFMaterial() != null) {
            if (!getRefFMaterial().isEqual(aggregate.getRefFMaterial())) {
                return false;
            }
        }

        if (getRefFBuffer() == null && aggregate.getRefFBuffer() != null) {
            return false;
        }

        if (getRefFBuffer() != null && aggregate.getRefFBuffer() == null) {
            return false;
        }

        if (getRefFBuffer() != null && aggregate.getRefFBuffer() != null) {
            return getRefFBuffer().capacity() == aggregate.getRefFBuffer().capacity();
        }

        return true;
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
    public FBuffer<FBufferData> getRefFBuffer() {

        return this.buffer;
    }

    @Override
    public FAggregate setRefFBuffer(FBuffer<FBufferData> refFBuffer) {

        this.buffer = refFBuffer;

        return this;
    }

    @Override
    public FMaterial getRefFMaterial() {

        return this.material;
    }

    @Override
    public FAggregate setRefFMaterial(FMaterial refMaterial) {

        this.material = refMaterial;

        return this;
    }
}

// https://charmm-gui.org/?doc=lecture&module=scientific&lesson=10
