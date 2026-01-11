package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.FractalDimension;
import eu.scattering.core.design.type.LinearDimension;
import eu.scattering.core.design.type.RadiusOfGyration;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FAggregateDef implements FAggregate {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "aggregate";
    private static final String JSON_PARTICLES = "particles";
    private static final String JSON_CAPACITY = "capacity";
    private static final String JSON_MATERIAL = "material";

    private final ScatFactory factory;
    private final FAssembly<Shape> particles;

    private final FAggregateModuleRadiusOfGyrationDef moduleRadiusOfGyration;
    private final FAggregateModuleTopologyDef moduleTopology;
    private final FAggregateModuleCenterDef moduleCenter;
    private final FAggregateModuleOverlapDef moduleOverlap;
    private final FAggregateModuleGeometryDef moduleGeometry;
    private final FAggregateModuleMorphologyDef moduleMorphology;

    private FMaterial material;
    private FBuffer<FBufferData> buffer;

    private FAggregateDef(ScatFactory factory, FAssembly<Shape> refParticles) {

        this.factory = factory;
        this.particles = refParticles;

        this.moduleRadiusOfGyration = new FAggregateModuleRadiusOfGyrationDef(this.factory, this);
        this.moduleTopology = new FAggregateModuleTopologyDef(this.factory, this);
        this.moduleCenter = new FAggregateModuleCenterDef(this.factory, this);
        this.moduleOverlap = new FAggregateModuleOverlapDef(this.factory, this);
        this.moduleGeometry = new FAggregateModuleGeometryDef(this.factory, this);
        this.moduleMorphology = new FAggregateModuleMorphologyDef(this.factory, this);
    }

    public static FAggregate create(ScatFactory factory, FAssembly<Shape> refParticles) {

        return new FAggregateDef(factory, refParticles);
    }

    public static FAggregate create(ScatFactory factory, List<Shape> refParticles) {

        return new FAggregateDef(factory, factory.getFAssembly(refParticles));
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
    public double getSurface() {

        return this.moduleGeometry.getSurface();
    }

    @Override
    public double getSurface(double[] layers) {

        return this.moduleGeometry.getSurface(layers);
    }

    @Override
    public double getSurfaceRadius() {

        return this.moduleGeometry.getSurfaceRadius();
    }

    @Override
    public double getSurfaceRadius(double[] layers) {

        return this.moduleGeometry.getSurfaceRadius(layers);
    }

    @Override
    public double getVolume() {

        return this.moduleGeometry.getVolume();
    }

    @Override
    public double getVolume(double[] layers) {

        return this.moduleGeometry.getVolume(layers);
    }

    @Override
    public double getVolumeRadius() {

        return this.moduleGeometry.getVolumeRadius();
    }

    @Override
    public double getVolumeRadius(double[] layers) {

        return this.moduleGeometry.getVolumeRadius(layers);
    }

    @Override
    public FMesh<FBufferData> getVolumeMesh() {

        return this.moduleGeometry.getVolumeMesh();
    }

    @Override
    public FPairPos3D getBoundary() {

        return this.moduleGeometry.getBoundary();
    }

    @Override
    public FPos3D getLength() {

        return this.moduleGeometry.getLength();
    }

    @Override
    public double getLength(LinearDimension type) {

        return this.moduleGeometry.getLength(type);
    }

    @Override
    public double getRadius(double x, double y, double z) {

        return this.moduleGeometry.getRadius(x, y, z);
    }

    @Override
    public double getRadius(FPoint center) {

        return this.moduleGeometry.getRadius(center);
    }

    @Override
    public double getRadius(FPos3D center) {

        return this.moduleGeometry.getRadius(center);
    }

    @Override
    public double getRadius(Center type) {

        return this.moduleGeometry.getRadius(type);
    }

    @Override
    public FPoint getCenter(FPoint in, Center type) {

        this.moduleCenter.getCenter(in, type);

        return in;
    }

    @Override
    public FPos3D getCenter(Center type) {

        return this.moduleCenter.getCenter(type);
    }

    @Override
    public FPoint getMassCenter(FPoint in) {

        this.moduleCenter.getMassCenter(in);

        return in;
    }

    @Override
    public FPos3D getMassCenter() {

        return this.moduleCenter.getMassCenter();
    }

    @Override
    public FPoint getSpatialCenter(FPoint in) {

        this.moduleCenter.getSpatialCenter(in);

        return in;
    }

    @Override
    public FPos3D getSpatialCenter() {

        return this.moduleCenter.getSpatialCenter();
    }

    @Override
    public FPoint getSphericalCenter(FPoint in) {

        this.moduleCenter.getSphericalCenter(in);

        return in;
    }

    @Override
    public FPos3D getSphericalCenter() {

        return this.moduleCenter.getSphericalCenter();
    }

    @Override
    public void resetPosition(FPoint center) {

        this.moduleCenter.positionCenter(center);
    }

    @Override
    public void resetPosition(FPos3D center) {

        this.moduleCenter.positionCenter(center);
    }

    @Override
    public void setCenter(Center type, double x, double y, double z) {

        this.moduleCenter.setCenter(type, x, y, z);
    }

    @Override
    public void setCenter(Center type, FPoint position) {

        this.moduleCenter.setCenter(type, position);
    }

    @Override
    public void setCenter(Center type, FPos3D position) {

        this.moduleCenter.setCenter(type, position);
    }

    @Override
    public void resetCenter(Center type) {

        this.moduleCenter.resetCenter(type);
    }

    @Override
    public double getRadiusOfGyration(RadiusOfGyration type) {

        return this.moduleRadiusOfGyration.get(type);
    }

    @Override
    public double getFractalDimension(FractalDimension type) {

        return this.moduleTopology.getFractalDimension(type);
    }

    @Override
    public FPlot getBoxCoverageFunction(boolean log) {

        return this.moduleTopology.getBoxCoverageFunction(log);
    }

    @Override
    public FPlot getDensityCorrelationFunction(boolean log) {

        return this.moduleTopology.getDensityCorrelationFunction(log);
    }

    @Override
    public double getVolumetricOverlapFactor() {

        return this.moduleOverlap.getVolumetricOverlapFactor();
    }

    @Override
    public double getLinearOverlapFactor() {

        return this.moduleOverlap.getLinearOverlapFactor();
    }

    @Override
    public int size() {

        return this.moduleMorphology.size();
    }

    @Override
    public void addParticles(Shape particle, double quantity) {

        this.moduleMorphology.addParticles(particle, quantity);
    }

    @Override
    public boolean addRefParticle(Shape particle) {

        return this.moduleMorphology.addRefParticle(particle);
    }

    @Override
    public boolean delRefParticle(Shape particle) {

        return this.moduleMorphology.delRefParticle(particle);
    }

    @Override
    public FStat getTripletAngle() {

        return this.moduleMorphology.getTripletAngle();
    }

    @Override
    public FPlot getTripletAngleFunction() {

        return this.moduleMorphology.getTripletAngleFunction();
    }

    @Override
    public FStat getPairDistance() {

        return this.moduleMorphology.getPairDistance();
    }

    @Override
    public FPlot getPairDistanceFunction() {

        return this.moduleMorphology.getPairDistanceFunction();
    }

    @Override
    public FStat getCoordinationNumber() {

        return this.moduleMorphology.getCoordinationNumber();
    }

    @Override
    public FPlot getCoordinationNumberFunction() {

        return this.moduleMorphology.getCoordinationNumberFunction();
    }

    @Override
    public FStat getFStatParticleRadius() {

        return this.moduleMorphology.getFStatParticleRadius();
    }

    @Override
    public FStat getFStatDistance(Center type) {

        return this.moduleMorphology.getFStatDistance(type);
    }

    @Override
    public void setParticleDelta(double delta) {

        this.moduleMorphology.setDelta(delta);
    }

    @Override
    public void setParticleEpsilon(double epsilon) {

        this.moduleMorphology.setEpsilon(epsilon);
    }

    @Override
    public boolean isSparse() {

        return this.moduleMorphology.isSparse();
    }

    @Override
    public boolean isCompact() {

        return this.moduleMorphology.isCompact();
    }

    @Override
    public void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {

        this.moduleMorphology.forEachPairInContact(consumer);
    }

    @Override
    public boolean overlaps(FAggregate arg) {
        FPos3D centerRef = getSpatialCenter();
        FPos3D centerArg = arg.getSpatialCenter();

        double radiusRef = getRadius(centerRef);
        double radiusArg = arg.getRadius(centerArg);

        List<Shape> particlesRef = new ArrayList<>(size());
        List<Shape> particlesArg = new ArrayList<>(arg.size());

        for (Shape shape : getRefParticles()) {
            if (shape.getDistCenter(centerArg) < radiusArg + shape.getRadius()) {
                particlesRef.add(shape);
            }
        }

        for (Shape shape : arg.getRefParticles()) {
            if (shape.getDistCenter(centerRef) < radiusRef + shape.getRadius()) {
                particlesArg.add(shape);
            }
        }

        for (Shape shapeRef : particlesRef) {
            for (Shape shapeArg : particlesArg) {
                if (shapeRef.overlaps(shapeArg)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean touches(FAggregate arg) {
        FPos3D centerRef = getSpatialCenter();
        FPos3D centerArg = arg.getSpatialCenter();

        double radiusRef = getRadius(centerRef);
        double radiusArg = arg.getRadius(centerArg);

        List<Shape> particlesRef = new ArrayList<>(size());
        List<Shape> particlesArg = new ArrayList<>(arg.size());

        for (Shape shape : getRefParticles()) {
            if (shape.getDistCenter(centerArg) <= radiusArg + shape.getRadius()) {
                particlesRef.add(shape);
            }
        }

        for (Shape shape : arg.getRefParticles()) {
            if (shape.getDistCenter(centerRef) <= radiusRef + shape.getRadius()) {
                particlesArg.add(shape);
            }
        }

        boolean touches = false;
        for (Shape shapeRef : particlesRef) {
            for (Shape shapeArg : particlesArg) {
                if (shapeRef.touches(shapeArg)) {
                    touches = true;
                }

                if (shapeRef.overlaps(shapeArg)) {
                    return false;
                }
            }
        }

        return touches;
    }

    @Override
    public boolean overlapsWithShift(FAggregate arg, FVector shift) {
        FPos3D centerRef = getSpatialCenter();
        FPos3D centerArg = arg.getSpatialCenter();

        double radiusRef = getRadius(centerRef);
        double radiusArg = arg.getRadius(centerArg);

        List<Shape> particlesRef = new ArrayList<>(size());
        List<Shape> particlesArg = new ArrayList<>(arg.size());

        FVector translator = shift.copy();

        for (Shape shape : getRefParticles()) {
            translator.moveBase(shape.getRefCenter());

            if (translator.getRefHead().getDistance(centerArg) < radiusArg + shape.getRadius()) {
                particlesRef.add(shape);
            }
        }

        for (Shape shape : arg.getRefParticles()) {
            translator.moveBase(centerRef);

            if (shape.getDistCenter(translator.getRefHead()) < radiusRef + shape.getRadius()) {
                particlesArg.add(shape);
            }
        }

        double memoX, memoY, memoZ;
        for (Shape shapeRef : particlesRef) {
            memoX = shapeRef.getCenterX();
            memoY = shapeRef.getCenterY();
            memoZ = shapeRef.getCenterZ();

            translator.moveBase(memoX, memoY, memoZ);

            shapeRef.setCenter(translator.getRefHead());

            boolean stop = false;

            for (Shape shapeArg : particlesArg) {
                if (shapeRef.overlaps(shapeArg)) {
                    stop = true;

                    break;
                }
            }

            shapeRef.setCenter(memoX, memoY, memoZ);

            if (stop) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean overlapsWithRotation(FAggregate arg, FVector axis, double angle) {
        FSphere dummy = supplyFSphere();

        for (Shape shapeRef : getRefParticles()) {
            dummy.setRadius(shapeRef.getRadius());
            dummy.setCenter(shapeRef.getRefCenter());

            factory.getRotAspect().rotRgAround(dummy.getRefCenter(), axis, angle);

            for (Shape shapeArg : arg.getRefParticles()) {
                if (dummy.overlaps(shapeArg)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void merge(FAggregate arg, boolean removeParticles) {

        for (Shape shape : arg.getRefParticles()) {
            getRefParticles().register(shape);
        }

        if (removeParticles) {
            arg.getRefParticles().clear();
        }
    }

    @Override
    public void translate(double x, double y, double z) {

        this.moduleMorphology.translate(x, y, z);
    }

    @Override
    public void translate(FPoint offset) {

        this.moduleMorphology.translate(offset);

    }

    @Override
    public void translate(FPos3D offset) {

        this.moduleMorphology.translate(offset);
    }

    @Override
    public void translate(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        this.moduleMorphology.translate(bX, bY, bZ, hX, hY, hZ);
    }

    @Override
    public void translate(FVector offset) {

        this.moduleMorphology.translate(offset);
    }

    @Override
    public void translate(FPairPos3D offset) {

        this.moduleMorphology.translate(offset);
    }

    @Override
    public void index() {

        int i = 0;
        for (Shape shape : getRefParticles()) {
            shape.setIndex(i++);
        }
    }

    // -------------------------------------------------------------------------------------------------

    private FBuffer<FBufferData> supplyFBuffer(int capacity) {

        return factory.getFBuffer(capacity);
    }

    private FMaterial supplyFMaterial() {

        return factory.getFMaterial();
    }

    private FPoint supplyFPoint() {

        return factory.getFPoint();
    }

    private FRay supplyFRay() {

        return factory.getFRay();
    }

    private FSphere supplyFSphere() {

        return factory.getFSphere();
    }

    //--------------------------------------------------

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
    public FAssembly<Shape> getRefParticles() {

        return this.particles;
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

    @Override
    public Iterator<Shape> iterator() {

        return getRefParticles().iterator();
    }

    @Override
    public void forEach(Consumer<? super Shape> action) {

        getRefParticles().forEach(action);
    }

    //--------------------------------------------------

    @Override
    public double project(FAggregate target, FVector dir) {
        FRay translator = supplyFRay();
        translator.getRefOrigin().set(dir);
        List<Shape> candidates = new ArrayList<>(getRefParticles().asList());

        FPoint centerArg = target.getCenter(supplyFPoint(), Center.SPATIAL);

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(centerArg)));

        for (Shape candidate : candidates) {
            translator.getRefOrigin().moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(target, translator);

            if (shift >= 0) {
                boolean overlaps = overlapsWithShift(target, translator.toFVector(shift));

                if (!overlaps) {
                    for (Shape particle : getRefParticles()) {
                        translator.getRefOrigin().set(dir);
                        translator.shiftForward(particle, shift);
                    }

                    return shift;
                }
            }

        }

        return -1;
    }

    @Override
    public double project(FAggregate target, FVector dir, double distLimit) {
        FPoint centerRef = getCenter(supplyFPoint(), Center.SPATIAL);
        FPoint centerArg = target.getCenter(supplyFPoint(), Center.SPATIAL);

        if (centerRef.getDistance(centerArg) > getRadius(centerRef) + getRadius(centerArg) + distLimit) {
            return -1;
        }

        FRay translator = supplyFRay();
        translator.getRefOrigin().set(dir);
        List<Shape> candidates = new ArrayList<>(getRefParticles().asList());

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(centerArg)));

        for (Shape candidate : candidates) {
            translator.getRefOrigin().moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(target, translator);

            if (shift >= 0 && shift <= distLimit) {
                boolean overlaps = overlapsWithShift(target, translator.toFVector(shift));

                if (!overlaps) {
                    for (Shape particle : getRefParticles()) {
                        translator.getRefOrigin().set(dir);
                        translator.shiftForward(particle, shift);
                    }

                    return shift;
                }
            }

        }

        return -1;
    }
}

// https://charmm-gui.org/?doc=lecture&module=scientific&lesson=10
