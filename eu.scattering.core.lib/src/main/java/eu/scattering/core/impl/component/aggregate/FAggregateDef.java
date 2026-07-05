package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.extension.FExtension;
import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.type.method.*;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;
import eu.scattering.core.impl.component.aggregate.extension.FExtensionDef;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FAggregateDef implements FAggregate {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "aggregate";
    private static final String JSON_PARTICLES = "particles";
    private static final String JSON_EXTENSION = "extension";

    private final ScatFactory factory;
    private final FAssembly<Shape> particles;

    private final FAggregateModuleGyrationDef moduleGyration;
    private final FAggregateModuleFractalDimensionDef moduleFractalDimension;
    private final FAggregateModuleCenterDef moduleCenter;
    private final FAggregateModuleOverlapDef moduleOverlap;
    private final FAggregateModuleGeometryDef moduleGeometry;
    private final FAggregateModuleMorphologyDef moduleMorphology;
    private final FAggregateModuleSupportDef moduleSupport;

    private final FExtension extension;

    private FAggregateDef(ScatFactory factory, FAssembly<Shape> refParticles, FExtension refExtension) {

        this.factory = factory;
        this.particles = refParticles;

        this.moduleGyration = new FAggregateModuleGyrationDef(this.factory, this);
        this.moduleFractalDimension = new FAggregateModuleFractalDimensionDef(this.factory, this);
        this.moduleCenter = new FAggregateModuleCenterDef(this.factory, this);
        this.moduleOverlap = new FAggregateModuleOverlapDef(this.factory, this);
        this.moduleGeometry = new FAggregateModuleGeometryDef(this.factory, this);
        this.moduleMorphology = new FAggregateModuleMorphologyDef(this.factory, this);
        this.moduleSupport = new FAggregateModuleSupportDef(this.factory, this);

        this.extension = Objects.requireNonNullElseGet(refExtension, () -> FExtensionDef.create(this.factory));
    }

    public static FAggregate create(ScatFactory factory, FAssembly<Shape> refParticles, FExtension extension) {

        return new FAggregateDef(factory, refParticles, extension);
    }

    public static FAggregate create(ScatFactory factory, FAssembly<Shape> refParticles) {

        return new FAggregateDef(factory, refParticles, null);
    }

    public static FAggregate create(ScatFactory factory, List<Shape> refParticles) {

        return new FAggregateDef(factory, factory.getFAssembly(refParticles), null);
    }

    public static FAggregate create(ScatFactory factory, JSONObject json) {

        if (!json.getString(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("Invalid JSON header (FAggregate)");
        }

        FAssembly<Shape> particles = factory.getFAssembly(json.getJSONObject(JSON_PARTICLES));
        FExtension extension = null;

        if (json.has(JSON_EXTENSION)) {
            extension = FExtensionDef.create(factory, json.getJSONObject(JSON_EXTENSION));
        }

        return new FAggregateDef(factory, particles, extension);
    }

    @Override
    public int size() {

        return this.moduleGeometry.size();
    }

    @Override
    public double getSurface(Surface type) {

        return this.moduleGeometry.getSurface(type);
    }

    @Override
    public double getSurface(double[] layers, Surface type) {

        return this.moduleGeometry.getSurface(layers, type);
    }

    @Override
    public double getSurfaceRadius(Surface type) {

        return this.moduleGeometry.getSurfaceRadius(type);
    }

    @Override
    public double getSurfaceRadius(double[] layers, Surface type) {

        return this.moduleGeometry.getSurfaceRadius(layers, type);
    }

    @Override
    public double getVolume(Volume type) {

        return this.moduleGeometry.getVolume(type);
    }

    @Override
    public double getVolume(double[] layers, Volume type) {

        return this.moduleGeometry.getVolume(layers, type);
    }

    @Override
    public double getVolumeRadius(Volume type) {

        return this.moduleGeometry.getVolumeRadius(type);
    }

    @Override
    public double getVolumeRadius(double[] layers, Volume type) {

        return this.moduleGeometry.getVolumeRadius(layers, type);
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
    public double getLength(Length type) {

        return this.moduleGeometry.getLength(type);
    }

    @Override
    public double getDiameter() {

        return this.moduleGeometry.getDiameter();
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
    public FPos3D getMassCenter(MassCenter type, List<Double> massFragments, List<FPos3D> centerFragments) {

        return this.moduleCenter.getMassCenter(type, massFragments, centerFragments);
    }

    @Override
    public FPos3D getMassCenter(MassCenter type) {

        return this.moduleCenter.getMassCenter(type, null, null);
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

        return this.moduleGyration.getRadiusOfGyration(type);
    }

    @Override
    public FMatrix3x3D getGyrationTensor(GyrationTensor type) {

        return this.moduleGyration.getGyrationTensor(type);
    }

    @Override
    public double getRadiusOfGyration(RadiusOfGyration type, FPoint massCenter, List<Double> massFragments, List<FPos3D> centerFragments) {

        return this.moduleGyration.getRadiusOfGyration(type, massCenter, massFragments, centerFragments);
    }

    @Override
    public double getFractalDimension(FractalDimension type) {

        return this.moduleFractalDimension.getFractalDimension(type, null);
    }

    @Override
    public double getFractalDimension(FractalDimension type, FBoxString plot) {

        return this.moduleFractalDimension.getFractalDimension(type, plot);
    }

    @Override
    public double getFractalDimensionMassRadius(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return this.moduleFractalDimension.getFractalDimensionMassRadius(window, method, stepFactor, rangeLimit);
    }

    @Override
    public double getFractalDimensionBoxCounting(double window, double step, int shift, boolean reposition, boolean pca) {

        return this.moduleFractalDimension.getFractalDimensionBoxCounting(window, step, shift, reposition, pca);
    }

    @Override
    public double getFractalDimensionDensityCorrelation(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return getFractalDimensionDensityCorrelation(window, method, stepFactor, rangeLimit, null);
    }

    @Override
    public double getFractalDimensionDensityCorrelation(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit, FMetaDC meta) {

        return this.moduleFractalDimension.getFractalDimensionDensityCorrelation(window, method, stepFactor, rangeLimit, meta);
    }

    @Override
    public FPlot getBoxCoverageFunction(double step, int shift, boolean reposition, boolean pca) {

        return this.moduleFractalDimension.getBoxCoverageFunction(step, shift, reposition, pca);
    }

    @Override
    public FPlot getDensityCorrelationFunction(double stepFactor) {

        return this.moduleFractalDimension.getDensityCorrelationFunction(stepFactor);
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
    public boolean deleteRefParticle(Shape particle) {

        return this.moduleSupport.deleteRefParticle(particle);
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
    public double project(FAggregate arg, FVector dir) {

        return this.moduleSupport.project(arg, dir);
    }

    @Override
    public double project(FAggregate arg, FVector dir, double distLimit) {

        return this.moduleSupport.project(arg, dir, distLimit);
    }

    @Override
    public void shiftBoundaryToZero() {

        this.moduleSupport.shiftBoundaryToZero();
    }

    @Override
    public void rotate(FMatrix3x3D matrix) {

        this.moduleSupport.rotate(matrix);
    }

    @Override
    public void pca() {

        this.moduleSupport.pca();
    }

    @Override
    public void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {

        this.moduleSupport.forEachPairInContact(consumer);
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_PARTICLES, getRefParticles().toJSON());
        json.put(JSON_EXTENSION, getRefFExtension().toJSON());

        return json;
    }

    @Override
    public FAggregate copy(boolean deep) {

        if (deep) {
            return FAggregateDef.create(this.factory, getRefParticles().copy(), getRefFExtension().copy());
        }

        return FAggregateDef.create(this.factory, getRefParticles().copy());
    }

    @Override
    public boolean isExact(FAggregate aggregate) {

        if (!isExactData(aggregate)) {
            return false;
        }

        return getRefFExtension().isExact(aggregate.getRefFExtension());
    }

    @Override
    public boolean isExactData(FAggregate aggregate) {

        return getRefParticles().isExact(aggregate.getRefParticles());
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
    public FAggregate addFBuffer(int capacity) {

        getRefFExtension().addFBuffer(capacity);

        return this;
    }

    @Override
    public FAggregate addFMaterial() {

        getRefFExtension().addFMaterial();

        return this;
    }

    @Override
    public FAggregate setRefFBuffer(FBuffer<FBufferData> buffer) {

        getRefFExtension().setRefFBuffer(buffer);

        return this;
    }

    @Override
    public FAggregate setRefFMaterial(FMaterial material) {

        getRefFExtension().setRefFMaterial(material);

        return this;
    }

    @Override
    public FAssembly<Shape> getRefParticles() {

        return this.particles;
    }

    @Override
    public FExtension getRefFExtension() {

        return this.extension;
    }
}

// https://charmm-gui.org/?doc=lecture&module=scientific&lesson=10
