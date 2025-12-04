package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONObject;

import java.util.Iterator;
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
    public double getLength(Axis type) {

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
    public double getRadiusFromOrigin() {

        return this.moduleGeometry.getRadiusFromOrigin();
    }

    @Override
    public void getMassCenter(FPoint in) {

        this.moduleCenter.getMassCenter(in);
    }

    @Override
    public FPos3D getMassCenter() {

        return this.moduleCenter.getMassCenter();
    }

    @Override
    public void getSpatialCenter(FPoint in) {

        this.moduleCenter.getSpatialCenter(in);
    }

    @Override
    public FPos3D getSpatialCenter() {

        return this.moduleCenter.getSpatialCenter();
    }

    @Override
    public void getSphericalCenter(FPoint in) {

        this.moduleCenter.getSphericalCenter(in);
    }

    @Override
    public FPos3D getSphericalCenter() {

        return this.moduleCenter.getSphericalCenter();
    }

    @Override
    public void setCenter(FPoint center) {

        this.moduleCenter.positionCenter(center);
    }

    @Override
    public void setCenter(FPos3D center) {

        this.moduleCenter.positionCenter(center);
    }

    @Override
    public double getRadiusOfGyration(RadiusOfGyration type) {

        return this.moduleRadiusOfGyration.get(type);
    }

    @Override
    public double getFractalDimension(Dimension type) {

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
    public FStat getParticleRadius() {

        return this.moduleMorphology.getParticleRadius();
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

    // -------------------------------------------------------------------------------------------------

    private FBuffer<FBufferData> supplyFBuffer(int capacity) {

        return factory.getFBuffer(capacity);
    }

    private FMaterial supplyFMaterial() {

        return factory.getFMaterial();
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
}

// https://charmm-gui.org/?doc=lecture&module=scientific&lesson=10
