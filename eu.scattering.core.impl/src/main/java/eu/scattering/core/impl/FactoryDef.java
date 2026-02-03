package eu.scattering.core.impl;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.export.FExportAspect;
import eu.scattering.core.design.aspect.prototype.FProtoAspect;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.aspect.rotate.generator.FRotGenerator;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContext;
import eu.scattering.core.design.component.aggregate.model.FModelFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorFactoryContext;
import eu.scattering.core.design.component.aggregate.validator.FValidatorFactoryContext;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineHelper;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneHelper;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayHelper;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentHelper;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.ShapeProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import eu.scattering.core.design.helper.transfer.FTransferHelper;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.physics.material.data.FMaterialData;
import eu.scattering.core.design.statistics.StatisticsHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.base.FStatMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMeta;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.cache.FCache;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.impl.aspect.export.FExportAspectDef;
import eu.scattering.core.impl.aspect.prototype.FProtoAspectDef;
import eu.scattering.core.impl.aspect.randomize.FRandAspectDef;
import eu.scattering.core.impl.aspect.randomize.FRandGeneratorDef;
import eu.scattering.core.impl.aspect.rotate.FRotAspectDef;
import eu.scattering.core.impl.aspect.rotate.FRotProcessorDef;
import eu.scattering.core.impl.component.aggregate.FAggregateDef;
import eu.scattering.core.impl.component.aggregate.FAggregateFactoryContextDef;
import eu.scattering.core.impl.component.aggregate.model.FModelFactoryContextDef;
import eu.scattering.core.impl.component.aggregate.monitor.FMonitorFactoryContextDef;
import eu.scattering.core.impl.component.aggregate.validator.FValidatorFactoryContextDef;
import eu.scattering.core.impl.component.geometry.GeometryParserDef;
import eu.scattering.core.impl.component.geometry.base.*;
import eu.scattering.core.impl.component.geometry.construct.*;
import eu.scattering.core.impl.component.geometry.container.FAssemblyDef;
import eu.scattering.core.impl.component.geometry.container.FAssemblyProducerDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereHelperDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereProducerDef;
import eu.scattering.core.impl.component.geometry.shape.ShapeProducerDef;
import eu.scattering.core.impl.component.number.FComplexDef;
import eu.scattering.core.impl.component.number.FComplexProducerDef;
import eu.scattering.core.impl.component.number.FQuaternionDef;
import eu.scattering.core.impl.component.number.FQuaternionProducerDef;
import eu.scattering.core.impl.helper.FPositionHelperDef;
import eu.scattering.core.impl.helper.FTrigHelperDef;
import eu.scattering.core.impl.physics.FMaterialDataDef;
import eu.scattering.core.impl.physics.FMaterialDef;
import eu.scattering.core.impl.statistics.FStatHelperDef;
import eu.scattering.core.impl.statistics.base.FStatDef;
import eu.scattering.core.impl.statistics.base.FStatMetaDef;
import eu.scattering.core.impl.statistics.construct.plot.FPlotMetaDef;
import eu.scattering.core.impl.statistics.construct.plotbar.FPlotBarDef;
import eu.scattering.core.impl.statistics.construct.plot.FPlotDef;
import eu.scattering.core.impl.statistics.construct.plotbar.FPlotBarMetaDef;
import eu.scattering.core.impl.storage.FBufferDef;
import eu.scattering.core.impl.storage.FCacheDef;
import eu.scattering.core.impl.storage.FLayerDef;
import eu.scattering.core.impl.storage.FMeshDef;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class FactoryDef implements ScatFactory {
    private final GeometryParser fGeometryParser;

    private final FRandGenerator fRandGenerator;
    private final FRotGenerator fRotGenerator;

    private final FExportAspect fAspectExport;
    private final FProtoAspect fAspectProto;
    private final FRandAspect fAspectRand;
    private final FRotAspect fAspectRot;

    private final FTrigHelper fTrigHelper;
    private final FTransferHelper fPosHelper;
    private final StatisticsHelper fStatHelper;

    private final FPointHelper fPointHelper;
    private final FRayHelper fRayHelper;
    private final FLineHelper fLineHelper;
    private final FSegmentHelper fSegmentHelper;
    private final FPlaneHelper fPlaneHelper;
    private final FSphereHelper fSphereHelper;

    private FactoryDef() {
        this.fRandGenerator = FRandGeneratorDef.create();
        this.fAspectRand = FRandAspectDef.create(this.fRandGenerator, this);
    }

    private FactoryDef(long seed) {
        this.fRandGenerator = FRandGeneratorDef.create(seed);
        this.fAspectRand = FRandAspectDef.create(this.fRandGenerator, this);
    }

    {
        this.fAspectExport = FExportAspectDef.get(this);

        this.fAspectProto = FProtoAspectDef.get();
        this.fAspectRot = FRotAspectDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();
        this.fPosHelper = FPositionHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);

        this.fPointHelper = FPointHelperDef.get();
        this.fRayHelper = FRayHelperDef.get(this);
        this.fLineHelper = FLineHelperDef.get(this);
        this.fSegmentHelper = FSegmentHelperDef.get(this);
        this.fPlaneHelper = FPlaneHelperDef.get(this);
        this.fSphereHelper = FSphereHelperDef.get(this.fPointHelper);
    }

    public static ScatFactory create() {

        return new FactoryDef();
    }

    public static ScatFactory create(long seed) {

        if (seed >= 0) {
            return new FactoryDef(seed);
        }

        long timestamp = System.currentTimeMillis();

        System.out.println("Timestamp: " + timestamp);

        return create(timestamp);
    }

    //--------------------------------------------------

    @Override
    public FComplexProducer getFComplexProducer() {

        return FComplexProducerDef.create(this, this.fRandGenerator);
    }

    @Override
    public FComplex getFComplex() {

        return FComplexDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FQuaternionProducer getFQuaternionProducer() {

        return FQuaternionProducerDef.create(this, this.fRandGenerator);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FPointHelper getFPointHelper() {

        return this.fPointHelper;
    }

    @Override
    public FPointProducer getFPointProducer() {

        return FPointProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FPoint getFPoint() {

        return FPointDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FVectorProducer getFVectorProducer() {

        return FVectorProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FVector getRefFVector(FPoint refBase, FPoint refHead) {

        return FVectorDef.create(this, refBase, refHead);
    }

    @Override
    public FVector getRefFVector(FPoint refHead) {

        return FVectorDef.create(this, getFPoint(), refHead);
    }

    @Override
    public FVector getFVector() {

        return FVectorDef.create(this, getFPoint(), getFPoint());
    }

    //--------------------------------------------------

    @Override
    public FDraftProducer getFDraftProducer() {

        return FDraftProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FDraft getRefFDraft(FVector refOrigin) {

        return FDraftDef.create(this, refOrigin);
    }

    @Override
    public FDraft getFDraft() {

        return FDraftDef.create(this, getFVector());
    }

    //--------------------------------------------------

    @Override
    public FPlaneHelper getFPlaneHelper() {

        return this.fPlaneHelper;
    }

    @Override
    public FPlaneProducer getFPlaneProducer() {

        return FPlaneProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FPlane getRefFPlane(FVector refOrigin) {

        return FPlaneDef.create(this, refOrigin);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDef.create(this, getFVector());
    }

    //--------------------------------------------------

    @Override
    public FRayHelper getFRayHelper() {

        return this.fRayHelper;
    }

    @Override
    public FRayProducer getFRayProducer() {

        return FRayProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FRay getRefFRay(FVector refOrigin) {

        return FRayDef.create(this, refOrigin);
    }

    @Override
    public FRay getFRay() {

        return FRayDef.create(this, getFVector());
    }

    //--------------------------------------------------

    @Override
    public FLineHelper getFLineHelper() {

        return this.fLineHelper;
    }

    @Override
    public FLineProducer getFLineProducer() {

        return FLineProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FLine getRefFLine(FVector refOrigin) {

        return FLineDef.create(this, refOrigin);
    }

    @Override
    public FLine getFLine() {

        return FLineDef.create(this, getFVector());
    }

    //--------------------------------------------------

    @Override
    public FSegmentHelper getFSegmentHelper() {

        return this.fSegmentHelper;
    }

    @Override
    public FSegmentProducer getFSegmentProducer() {

        return FSegmentProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FSegment getRefFSegment(FVector refOrigin) {

        return FSegmentDef.create(this, refOrigin);
    }

    @Override
    public FSegment getFSegment() {

        return FSegmentDef.create(this, getFVector());
    }

    //--------------------------------------------------

    @Override
    public ShapeProducer getShapeProducer() {

        return ShapeProducerDef.create(this.fRandGenerator);
    }

    //--------------------------------------------------


    @Override
    public FSphereHelper getFSphereHelper() {

        return this.fSphereHelper;
    }

    @Override
    public FSphereProducer getFSphereProducer() {

        return FSphereProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public FSphere getRefFSphere(FPoint refCenter) {

        return FSphereDef.create(this, refCenter);
    }

    @Override
    public FSphere getFSphere() {

        return FSphereDef.create(this, getFPoint());
    }

    //--------------------------------------------------

    @Override
    public <T extends Geometry> FAssemblyProducer<T> getFAssemblyProducer() {

        return FAssemblyProducerDef.create(this, this.fAspectRand);
    }

    @Override
    public <T extends Geometry> FAssembly<T> getFAssembly(List<? extends T> elements) {

        return FAssemblyDef.create(this, elements);
    }

    @Override
    public <T extends Geometry> FAssembly<T> getFAssembly() {

        return FAssemblyDef.create(this, new ArrayList<>());
    }

    @Override
    public <T extends Geometry> FAssembly<T> getFAssembly(JSONObject json) {

        return FAssemblyDef.create(this, json);
    }

    //--------------------------------------------------

    @Override
    public FAggregate getFAggregate() {

        return FAggregateDef.create(this, getFAssembly());
    }

    @Override
    public FAggregate getFAggregate(JSONObject json) {

        return FAggregateDef.create(this, json);
    }

    @Override
    public FAggregate getRefFAggregate(FAssembly<Shape> refParticles) {

        return FAggregateDef.create(this, refParticles);
    }

    @Override
    public FAggregate getRefFAggregate(List<Shape> refParticles) {

        return FAggregateDef.create(this, refParticles);
    }

    @Override
    public FAggregateFactoryContext getFAggregateContext() {

        return FAggregateFactoryContextDef.create(this);
    }

    @Override
    public FModelFactoryContext getFModelContext() {

        return FModelFactoryContextDef.create(this);
    }

    @Override
    public FMonitorFactoryContext getFMonitorContext() {

        return FMonitorFactoryContextDef.create(this);
    }

    @Override
    public FValidatorFactoryContext getFValidatorContext() {

        return FValidatorFactoryContextDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator getFRand() {

        return this.fRandGenerator;
    }

    @Override
    public FRotGenerator getFRot() {

        return this.fRotGenerator;
    }

    //--------------------------------------------------

    @Override
    public FExportAspect getExportAspect() {

        return this.fAspectExport;
    }

    @Override
    public FProtoAspect getProtoAspect() {

        return this.fAspectProto;
    }

    @Override
    public FRandAspect getRandAspect() {

        return this.fAspectRand;
    }

    @Override
    public FRotAspect getRotAspect() {

        return this.fAspectRot;
    }

    //--------------------------------------------------

    @Override
    public StatisticsHelper getStatisticsHelper() {

        return this.fStatHelper;
    }

    @Override
    public FTransferHelper getFTransferHelper() {

        return this.fPosHelper;
    }

    @Override
    public FTrigHelper getFTrigHelper() {

        return this.fTrigHelper;
    }

    //--------------------------------------------------

    @Override
    public GeometryParser getGeometryParser() {

        return this.fGeometryParser;
    }

    //--------------------------------------------------

    @Override
    public FPlotBar getFPlotBar() {

        return FPlotBarDef.create(this);
    }

    @Override
    public FPlotBar getFPlotBar(JSONObject json) {

        return FPlotBarDef.create(this, json);
    }

    @Override
    public FPlotBarMeta getFPlotBarMeta() {

        return FPlotBarMetaDef.create(this);
    }

    @Override
    public FPlotBar getRefFPlotBar(FStat refDataX, List<FStat> refDataY) {

        return FPlotBarDef.create(this, refDataX, refDataY);
    }

    @Override
    public FPlot getFPlot() {

        return FPlotDef.create(this);
    }

    @Override
    public FPlot getFPlot(FLayer fLayer) {

        return FPlotDef.create(this, fLayer);
    }

    @Override
    public FPlot getFPlot(JSONObject json) {

        return FPlotDef.create(this, json);
    }

    @Override
    public FPlotMeta getFPlotMeta() {

        return FPlotMetaDef.create(this);
    }

    @Override
    public FPlot getRefFPlot(FStat refDataX, FStat refDataY) {

        return FPlotDef.create(this, refDataX, refDataY);
    }

    @Override
    public FStat getFStat() {

        return FStatDef.create(this);
    }

    @Override
    public FStat getFStat(double... data) {

        return FStatDef.create(this, data);
    }

    @Override
    public FStat getFStat(JSONObject json) {

        return FStatDef.create(this, json);
    }

    @Override
    public FStatMeta getFStatMeta() {

        return FStatMetaDef.create(this);
    }

    @Override
    public FStat getRefFStat(List<Double> refData) {

        return FStatDef.create(this, refData);
    }

    //--------------------------------------------------

    @Override
    public <T> FBuffer<T> getFBuffer(int capacity) {

        return FBufferDef.create(capacity);
    }

    @Override
    public <T> FMesh<T> getFMesh() {

        return FMeshDef.create();
    }

    @Override
    public <T> FMesh<T> getFMesh(int capacity) {

        return FMeshDef.create(capacity);
    }

    @Override
    public FCache getFCache(boolean multi) {

        return FCacheDef.create(multi);
    }

    @Override
    public FCache getFCache(JSONObject json) {

        return FCacheDef.create(json);
    }

    @Override
    public FLayer getFLayer() {

        return FLayerDef.create();
    }

    @Override
    public FLayer getFLayer(JSONObject json) {

        return FLayerDef.create(json);
    }

    //--------------------------------------------------

    @Override
    public FMaterial getFMaterial() {

        return FMaterialDef.create(this);
    }

    @Override
    public FMaterial getFMaterial(JSONObject json) {

        return FMaterialDef.create(this, json);
    }

    @Override
    public FMaterialData getFMaterialData() {

        return FMaterialDataDef.create();
    }

    @Override
    public FMaterialData getFMaterialData(JSONObject json) {

        return FMaterialDataDef.create(json);
    }
}
