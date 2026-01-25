package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
    private final FAggregateModuleFractalDimensionDef moduleFractalDimension;
    private final FAggregateModuleCenterDef moduleCenter;
    private final FAggregateModuleOverlapDef moduleOverlap;
    private final FAggregateModuleGeometryDef moduleGeometry;
    private final FAggregateModuleMorphologyDef moduleMorphology;
    private final FAggregateModuleSupportDef moduleSupport;

    private FMaterial material;
    private FBuffer<FBufferData> buffer;

    private FAggregateDef(ScatFactory factory, FAssembly<Shape> refParticles) {

        this.factory = factory;
        this.particles = refParticles;

        this.moduleRadiusOfGyration = new FAggregateModuleRadiusOfGyrationDef(this.factory, this);
        this.moduleFractalDimension = new FAggregateModuleFractalDimensionDef(this.factory, this);
        this.moduleCenter = new FAggregateModuleCenterDef(this.factory, this);
        this.moduleOverlap = new FAggregateModuleOverlapDef(this.factory, this);
        this.moduleGeometry = new FAggregateModuleGeometryDef(this.factory, this);
        this.moduleMorphology = new FAggregateModuleMorphologyDef(this.factory, this);
        this.moduleSupport = new FAggregateModuleSupportDef(this.factory, this);
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
    public int size() {

        return this.moduleGeometry.size();
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
    public double getRadiusFrom(double x, double y, double z) {

        return this.moduleGeometry.getRadiusFrom(x, y, z);
    }

    @Override
    public double getRadiusFrom(FPoint center) {

        return this.moduleGeometry.getRadiusFrom(center);
    }

    @Override
    public double getRadiusFrom(FPos3D center) {

        return this.moduleGeometry.getRadiusFrom(center);
    }

    @Override
    public double getRadiusFrom(Center type) {

        return this.moduleGeometry.getRadiusFrom(type);
    }

    @Override
    public void setRadiusFrom(double x, double y, double z, double radius) {

        this.moduleGeometry.getRadiusFrom(x, y, z, radius);
    }

    @Override
    public void setRadiusFrom(FPoint center, double radius) {

        this.moduleGeometry.getRadiusFrom(center, radius);
    }

    @Override
    public void setRadiusFrom(FPos3D center, double radius) {

        this.moduleGeometry.getRadiusFrom(center, radius);
    }

    @Override
    public void setRadiusFrom(Center type, double radius) {

        this.moduleGeometry.getRadiusFrom(type, radius);
    }

    @Override
    public FStat getFStatParticleRadius() {

        return this.moduleGeometry.getFStatParticleRadius();
    }

    @Override
    public FStat getFStatDistance(Center type) {

        return this.moduleGeometry.getFStatDistance(type);
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
    public FPoint getMassCenter(FPoint in, MassCenter type) {

        this.moduleCenter.getMassCenter(in, type);

        return in;
    }

    @Override
    public FPos3D getMassCenter(MassCenter type) {

        return this.moduleCenter.getMassCenter(type);
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
    public FPoint getSphericalCenter(FPoint in, int steps) {

        this.moduleCenter.getSphericalCenter(in, steps);

        return in;
    }

    @Override
    public FPos3D getSphericalCenter(int steps) {

        return this.moduleCenter.getSphericalCenter(steps);
    }

    @Override
    public void setPositionAsZero(FPoint center) {

        this.moduleCenter.setPositionAsZero(center);
    }

    @Override
    public void setPositionAsZero(FPos3D center) {

        this.moduleCenter.setPositionAsZero(center);
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
    public void setMassCenter(double x, double y, double z, MassCenter type) {

        this.moduleCenter.setMassCenter(x, y, z, type);
    }

    @Override
    public void setMassCenter(FPoint position, MassCenter type) {

        this.moduleCenter.setMassCenter(position, type);
    }

    @Override
    public void setMassCenter(FPos3D position, MassCenter type) {

        this.moduleCenter.setMassCenter(position, type);
    }

    @Override
    public void setSpatialCenter(double x, double y, double z) {

        this.moduleCenter.setSpatialCenter(x, y, z);
    }

    @Override
    public void setSpatialCenter(FPoint position) {

        this.moduleCenter.setSpatialCenter(position);
    }

    @Override
    public void setSpatialCenter(FPos3D position) {

        this.moduleCenter.setSpatialCenter(position);
    }

    @Override
    public void setSphericalCenter(double x, double y, double z, int steps) {

        this.moduleCenter.setSphericalCenter(x, y, z, steps);
    }

    @Override
    public void setSphericalCenter(FPoint position, int steps) {

        this.moduleCenter.setSphericalCenter(position, steps);
    }

    @Override
    public void setSphericalCenter(FPos3D position, int steps) {

        this.moduleCenter.setSphericalCenter(position, steps);
    }

    @Override
    public void setCenterAsZero(Center type) {

        this.moduleCenter.setCenterAsZero(type);
    }

    @Override
    public void setMassCenterAsZero(MassCenter type) {

        this.moduleCenter.setMassCenterAsZero(type);
    }

    @Override
    public void setSpatialCenterAsZero() {

        this.moduleCenter.setSpatialCenterAsZero();
    }

    @Override
    public void setSphericalCenterAsZero(int steps) {

        this.moduleCenter.setSphericalCenterAsZero(steps);
    }

    @Override
    public double getRadiusOfGyration(RadiusOfGyration type) {

        return this.moduleRadiusOfGyration.getRadiusOfGyration(type);
    }

    @Override
    public double getFractalDimension(FractalDimension type) {

        return this.moduleFractalDimension.getFractalDimension(type);
    }

    @Override
    public FPlot getBoxCoverageFunction(boolean log) {

        return this.moduleFractalDimension.getBoxCoverageFunction(log);
    }

    @Override
    public FPlot getDensityCorrelationFunction(boolean log) {

        return this.moduleFractalDimension.getDensityCorrelationFunction(log);
    }

    @Override
    public boolean isNonOverlapping() {

        return this.moduleOverlap.isNonOverlapping();
    }

    @Override
    public boolean isPointConnected() {

        return this.moduleOverlap.isPointConnected();
    }

    @Override
    public boolean isConnected() {

        return this.moduleOverlap.isConnected();
    }

    @Override
    public FStat getOverlapFactor(OverlapFactor type) {

        return this.moduleOverlap.getOverlapFactor(type);
    }

    @Override
    public boolean overlaps(FAggregate arg) {

        return this.moduleOverlap.overlaps(arg);
    }

    @Override
    public boolean touches(FAggregate arg) {

        return this.moduleOverlap.touches(arg);
    }

    @Override
    public boolean overlapsWithShift(FAggregate arg, FVector shift) {

        return this.moduleOverlap.overlapsWithShift(arg, shift);
    }

    @Override
    public boolean overlapsWithRotation(FAggregate arg, FVector axis, double angle) {

        return this.moduleOverlap.overlapsWithRotation(arg, axis, angle);
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
    public void addParticles(Shape particle, double quantity) {

        this.moduleSupport.addParticles(particle, quantity);
    }

    @Override
    public boolean addRefParticle(Shape particle) {

        return this.moduleSupport.addRefParticle(particle);
    }

    @Override
    public boolean delRefParticle(Shape particle) {

        return this.moduleSupport.delRefParticle(particle);
    }

    @Override
    public void index() {

        this.moduleSupport.index();
    }

    @Override
    public void merge(FAggregate arg, boolean removeParticles) {

        this.moduleSupport.merge(arg, removeParticles);
    }

    @Override
    public void setParticleDelta(double delta) {

        this.moduleSupport.setParticleDelta(delta);
    }

    @Override
    public void setParticleEpsilon(double epsilon) {

        this.moduleSupport.setParticleEpsilon(epsilon);
    }

    @Override
    public void translate(double x, double y, double z) {

        this.moduleSupport.translate(x, y, z);
    }

    @Override
    public void translate(FPoint offset) {

        this.moduleSupport.translate(offset);
    }

    @Override
    public void translate(FPos3D offset) {

        this.moduleSupport.translate(offset);
    }

    @Override
    public void translate(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        this.moduleSupport.translate(bX, bY, bZ, hX, hY, hZ);
    }

    @Override
    public void translate(FVector offset) {

        this.moduleSupport.translate(offset);
    }

    @Override
    public void translate(FPairPos3D offset) {

        this.moduleSupport.translate(offset);
    }

    @Override
    public void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {

        this.moduleSupport.forEachPairInContact(consumer);
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
    public FAggregate copy(boolean deep) {
        FAggregate copy = FAggregateDef.create(this.factory, getRefParticles().copy());

        if (deep) {
            copy.setRefFMaterial(getRefFMaterial().copy());
            copy.setRefFBuffer(supplyFBuffer(getRefFBuffer().capacity()));
        }

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

        if (centerRef.getDistance(centerArg) > getRadiusFrom(centerRef) + getRadiusFrom(centerArg) + distLimit) {
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
