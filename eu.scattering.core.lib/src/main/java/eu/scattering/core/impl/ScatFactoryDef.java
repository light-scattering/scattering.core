package eu.scattering.core.impl;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.save.FSaveAspect;
import eu.scattering.core.design.aspect.load.FLoadAspect;
import eu.scattering.core.design.aspect.prototype.FProtoAspect;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.aspect.rotate.generator.FRotGenerator;
import eu.scattering.core.design.aspect.rotate.transfer.variant.FRotQt;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContext;
import eu.scattering.core.design.component.aggregate.config.bc.FConfigBC;
import eu.scattering.core.design.component.aggregate.meta.bc.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;
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
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.storage.transfer.TransferHelper;
import eu.scattering.core.design.mathematics.helper.FTrigHelper;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.physics.material.data.FMaterialData;
import eu.scattering.core.design.statistics.StatisticsHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.base.FStatMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMetaGlobal;
import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.cache.FCache;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos2DI;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos3DI;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos4DI;
import eu.scattering.core.design.storage.transfer.position.p2.variant.*;
import eu.scattering.core.design.storage.transfer.position.p1.variant.*;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos2DI;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos3DI;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos4DI;
import eu.scattering.core.impl.aspect.save.FSaveAspectDef;
import eu.scattering.core.impl.aspect.load.FLoadAspectDef;
import eu.scattering.core.impl.aspect.prototype.FProtoAspectDef;
import eu.scattering.core.impl.aspect.randomize.FRandAspectDef;
import eu.scattering.core.impl.aspect.randomize.FRandGeneratorDef;
import eu.scattering.core.impl.aspect.rotate.FRotAspectDef;
import eu.scattering.core.impl.aspect.rotate.FRotProcessorDef;
import eu.scattering.core.impl.aspect.rotate.transfer.FRotQtDef;
import eu.scattering.core.impl.component.aggregate.FAggregateDef;
import eu.scattering.core.impl.component.aggregate.FAggregateFactoryContextDef;
import eu.scattering.core.impl.component.aggregate.config.FConfigBCDef;
import eu.scattering.core.impl.component.aggregate.meta.FMetaBCDef;
import eu.scattering.core.impl.component.aggregate.meta.FMetaDCDef;
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
import eu.scattering.core.impl.statistics.construct.plot.FPlotMetaDef;
import eu.scattering.core.impl.storage.transfer.TransferHelperDef;
import eu.scattering.core.impl.mathematics.FTrigHelperDef;
import eu.scattering.core.impl.physics.FMaterialDataDef;
import eu.scattering.core.impl.physics.FMaterialDef;
import eu.scattering.core.impl.statistics.FStatHelperDef;
import eu.scattering.core.impl.statistics.base.FStatDef;
import eu.scattering.core.impl.statistics.base.FStatMetaDef;
import eu.scattering.core.impl.statistics.construct.plot.FPlotMetaGlobalDef;
import eu.scattering.core.impl.statistics.construct.plotbar.FPlotBarDef;
import eu.scattering.core.impl.statistics.construct.plot.FPlotDef;
import eu.scattering.core.impl.statistics.construct.plotbar.FPlotBarMetaDef;
import eu.scattering.core.impl.storage.buffer.FBufferDataDef;
import eu.scattering.core.impl.storage.buffer.FBufferDef;
import eu.scattering.core.impl.storage.cache.FCacheDef;
import eu.scattering.core.impl.storage.layer.FLayerDef;
import eu.scattering.core.impl.storage.mesh.FMeshDef;
import eu.scattering.core.impl.storage.transfer.box.FBoxDoubleDef;
import eu.scattering.core.impl.storage.transfer.box.FBoxStringDef;
import eu.scattering.core.impl.storage.transfer.matrix.FMatrix3x3DDef;
import eu.scattering.core.impl.storage.transfer.polynomial.FPolyDef;
import eu.scattering.core.impl.storage.transfer.position.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class ScatFactoryDef implements ScatFactory {
    private final GeometryParser fGeometryParser;

    private final FRandGenerator fRandGenerator;
    private final FRotGenerator fRotGenerator;

    private final FSaveAspect fAspectExport;
    private final FLoadAspect fAspectLoad;
    private final FProtoAspect fAspectProto;
    private final FRandAspect fAspectRand;
    private final FRotAspect fAspectRot;

    private final FTrigHelper fTrigHelper;
    private final TransferHelper fPosHelper;
    private final StatisticsHelper fStatHelper;

    private final FPointHelper fPointHelper;
    private final FRayHelper fRayHelper;
    private final FLineHelper fLineHelper;
    private final FSegmentHelper fSegmentHelper;
    private final FPlaneHelper fPlaneHelper;
    private final FSphereHelper fSphereHelper;

    private ScatFactoryDef() {
        this.fRandGenerator = FRandGeneratorDef.create(this);
        this.fAspectRand = FRandAspectDef.create(this.fRandGenerator, this);
    }

    private ScatFactoryDef(long seed) {
        this.fRandGenerator = FRandGeneratorDef.create(this, seed);
        this.fAspectRand = FRandAspectDef.create(this.fRandGenerator, this);
    }

    {
        this.fAspectExport = FSaveAspectDef.create(this);
        this.fAspectLoad = FLoadAspectDef.create(this);

        this.fRotGenerator = FRotProcessorDef.create(this);

        this.fAspectProto = FProtoAspectDef.create();
        this.fAspectRot = FRotAspectDef.create(this.fRotGenerator );

        this.fTrigHelper = FTrigHelperDef.create(this);
        this.fStatHelper = FStatHelperDef.create();
        this.fPosHelper = TransferHelperDef.create(this);

        this.fGeometryParser = GeometryParserDef.create(this);

        this.fPointHelper = FPointHelperDef.create(this);
        this.fRayHelper = FRayHelperDef.create(this);
        this.fLineHelper = FLineHelperDef.create(this);
        this.fSegmentHelper = FSegmentHelperDef.create(this);
        this.fPlaneHelper = FPlaneHelperDef.create(this);
        this.fSphereHelper = FSphereHelperDef.create(this.fPointHelper);
    }

    public static ScatFactory create() {

        return new ScatFactoryDef();
    }

    public static ScatFactory create(long seed) {

        if (seed >= 0) {
            return new ScatFactoryDef(seed);
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

        return FVectorDef.create(this, this, refBase, refHead);
    }

    @Override
    public FVector getRefFVector(FPoint refHead) {

        return FVectorDef.create(this, this, getFPoint(), refHead);
    }

    @Override
    public FVector getFVector() {

        return FVectorDef.create(this, this, getFPoint(), getFPoint());
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

        return FAssemblyDef.create(this, this, elements);
    }

    @Override
    public <T extends Geometry> FAssembly<T> getFAssembly() {

        return FAssemblyDef.create(this, this, new ArrayList<>());
    }

    @Override
    public <T extends Geometry> FAssembly<T> getFAssembly(JSONObject json) {

        return FAssemblyDef.create(this,this, json);
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

    @Override
    public FConfigBC getFConfigBC() {

        return FConfigBCDef.create();
    }

    @Override
    public FConfigBC getFConfigBC(FConfigBC.Preset preset) {

        return FConfigBCDef.create(preset);
    }

    @Override
    public FMetaBC getFMetaBC() {

        return FMetaBCDef.create();
    }

    @Override
    public FMetaDC getFMetaDC() {

        return FMetaDCDef.create();
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
    public FSaveAspect getSaveAspect() {

        return this.fAspectExport;
    }

    @Override
    public FLoadAspect getLoadAspect() {

        return this.fAspectLoad;
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
    public TransferHelper getTransferHelper() {

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
    public FPlotBarMetaGlobal getFPlotBarMetaGlobal() {

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

        return FPlotMetaDef.create();
    }

    @Override
    public FPlotMetaGlobal getFPlotMetaGlobal() {

        return FPlotMetaGlobalDef.create(this);
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

        return FBufferDef.create(this, capacity);
    }

    @Override
    public <T> FMesh<T> getFMesh() {

        return FMeshDef.create(this);
    }

    @Override
    public <T> FMesh<T> getFMesh(int capacity) {

        return FMeshDef.create(this, capacity);
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

    //--------------------------------------------------

    @Override
    public FBoxDouble getFBoxDouble() {

        return FBoxDoubleDef.create();
    }

    @Override
    public FBoxString getFBoxString() {

        return FBoxStringDef.create();
    }

    @Override
    public FPos2D getFPos2D(double d0, double d1) {

        return FPos2DDef.create(d0, d1);
    }

    @Override
    public FPos2D getFPos2D(JSONObject json) {

        return FPos2DDef.create(json);
    }

    @Override
    public FPos2DI getFPos2DI(int d0, int d1) {

        return FPos2DIDef.create(d0, d1);
    }

    @Override
    public FPos2DI getFPos2DI(JSONObject json) {

        return FPos2DIDef.create(json);
    }

    @Override
    public FPos3D getFPos3D(double d0, double d1, double d2) {

        return FPos3DDef.create(d0, d1, d2);
    }

    @Override
    public FPos3D getFPos3D(JSONObject json) {

        return FPos3DDef.create(json);
    }

    @Override
    public FPos3DI getFPos3DI(int d0, int d1, int d2) {

        return FPos3DIDef.create(d0, d1, d2);
    }

    @Override
    public FPos3DI getFPos3DI(JSONObject json) {

        return FPos3DIDef.create(json);
    }

    @Override
    public FPos4D getFPos4D(double d0, double d1, double d2, double d3) {

        return FPos4DDef.create(d0, d1, d2, d3);
    }

    @Override
    public FPos4D getFPos4D(JSONObject json) {

        return FPos4DDef.create(json);
    }

    @Override
    public FPos4DI getFPos4DI(int d0, int d1, int d2, int d3) {

        return FPos4DIDef.create(d0, d1, d2, d3);
    }

    @Override
    public FPos4DI getFPos4DI(JSONObject json) {

        return FPos4DIDef.create(json);
    }

    @Override
    public FPairPos2D getFPairPos2D(double AD0, double AD1, double BD0, double BD1) {

        return FPairPos2DDef.create(this, AD0, AD1, BD0, BD1);
    }

    @Override
    public FPairPos2D getFPairPos2D(FPos2D posA, FPos2D posB) {

        return FPairPos2DDef.create(this, posA, posB);
    }

    @Override
    public FPairPos2D getFPairPos2D(JSONObject json) {

        return FPairPos2DDef.create(this, json);
    }

    @Override
    public FPairPos2DI getFPairPos2DI(int AD0, int AD1, int BD0, int BD1) {

        return FPairPos2DIDef.create(this, AD0, AD1, BD0, BD1);
    }

    @Override
    public FPairPos2DI getFPairPos2DI(FPos2DI posA, FPos2DI posB) {

        return FPairPos2DIDef.create(this, posA, posB);
    }

    @Override
    public FPairPos2DI getFPairPos2DI(JSONObject json) {

        return FPairPos2DIDef.create(this, json);
    }

    @Override
    public FPairPos3D getFPairPos3D(double AD0, double AD1, double AD2, double BD0, double BD1, double BD2) {

        return FPairPos3DDef.create(this, AD0, AD1, AD2, BD0, BD1, BD2);
    }

    @Override
    public FPairPos3D getFPairPos3D(FPos3D posA, FPos3D posB) {

        return FPairPos3DDef.create(this, posA, posB);
    }

    @Override
    public FPairPos3D getFPairPos3D(JSONObject json) {

        return FPairPos3DDef.create(this, json);
    }

    @Override
    public FPairPos3DI getFPairPos3DI(int AD0, int AD1, int AD2, int BD0, int BD1, int BD2) {

        return FPairPos3DIDef.create(this, AD0, AD1, AD2, BD0, BD1, BD2);
    }

    @Override
    public FPairPos3DI getFPairPos3DI(FPos3DI posA, FPos3DI posB) {

        return FPairPos3DIDef.create(this, posA, posB);
    }

    @Override
    public FPairPos3DI getFPairPos3DI(JSONObject json) {

        return FPairPos3DIDef.create(this, json);
    }

    @Override
    public FPairPos4D getFPairPos4D(double AD0, double AD1, double AD2, double AD3, double BD0, double BD1, double BD2, double BD3) {

        return FPairPos4DDef.create(this, AD0, AD1, AD2, AD3, BD0, BD1, BD2, BD3);
    }

    @Override
    public FPairPos4D getFPairPos4D(FPos4D posA, FPos4D posB) {

        return FPairPos4DDef.create(this, posA, posB);
    }

    @Override
    public FPairPos4D getFPairPos4D(JSONObject json) {

        return FPairPos4DDef.create(this, json);
    }

    @Override
    public FPairPos4DI getFPairPos4DI(int AD0, int AD1, int AD2, int AD3, int BD0, int BD1, int BD2, int BD3) {

        return FPairPos4DIDef.create(this, AD0, AD1, AD2, AD3, BD0, BD1, BD2, BD3);
    }

    @Override
    public FPairPos4DI getFPairPos4DI(FPos4DI posA, FPos4DI posB) {

        return FPairPos4DIDef.create(this, posA, posB);
    }

    @Override
    public FPairPos4DI getFPairPos4DI(JSONObject json) {

        return FPairPos4DIDef.create(this, json);
    }

    @Override
    public FPoly getFPoly(double... core) {

        return FPolyDef.create(core);
    }

    @Override
    public FPoly getFPoly(JSONObject json) {

        return FPolyDef.create(json);
    }

    @Override
    public FMatrix3x3D getFMatrix3x3D(double[][] origin) {

        return FMatrix3x3DDef.create(origin);
    }

    @Override
    public FMatrix3x3D getFMatrix3x3D(JSONObject json) {

        return FMatrix3x3DDef.create(json);
    }

    //--------------------------------------------------

    @Override
    public FRotQt getFRotQt(FPos4D qt, FPos3D offset, FMatrix3x3D matrix) {

        return FRotQtDef.create(qt, offset, matrix);
    }

    @Override
    public FRotQt getFRotQt(JSONObject json) {

        return FRotQtDef.create(this, json);
    }

    @Override
    public FBufferData getFBufferData(StorageFactory factory, String tag, int layer) {

        return FBufferDataDef.create(factory, tag, layer);
    }
}
