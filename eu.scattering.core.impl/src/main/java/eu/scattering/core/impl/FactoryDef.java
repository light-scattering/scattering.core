package eu.scattering.core.impl;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelDLA;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelRLA;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.aggregate.monitor.construct.FMonitorConstruct;
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
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
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
import eu.scattering.core.design.engine.export.FExportEngine;
import eu.scattering.core.design.engine.prototype.FProtoEngine;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.design.engine.rotate.generator.FRotGenerator;
import eu.scattering.core.design.helper.transfer.FTransferHelper;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.physics.material.data.FMaterialData;
import eu.scattering.core.design.statistics.StatisticsHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.cache.FCache;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.impl.component.aggregate.FAggregateDef;
import eu.scattering.core.impl.component.aggregate.FAggregateGeoDef;
import eu.scattering.core.impl.component.aggregate.FAggregatePreDef;
import eu.scattering.core.impl.component.aggregate.model.*;
import eu.scattering.core.impl.component.aggregate.monitor.FMonitorRoGDef;
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
import eu.scattering.core.impl.engine.export.FExportEngineDef;
import eu.scattering.core.impl.engine.prototype.FProtoEngineDef;
import eu.scattering.core.impl.engine.randomize.FRandEngineDef;
import eu.scattering.core.impl.engine.randomize.FRandGeneratorDef;
import eu.scattering.core.impl.engine.rotate.FRotEngineDef;
import eu.scattering.core.impl.engine.rotate.FRotProcessorDef;
import eu.scattering.core.impl.helper.FPositionHelperDef;
import eu.scattering.core.impl.helper.FTrigHelperDef;
import eu.scattering.core.impl.physics.FMaterialDataDef;
import eu.scattering.core.impl.physics.FMaterialDef;
import eu.scattering.core.impl.statistics.FStatHelperDef;
import eu.scattering.core.impl.statistics.base.FStatDef;
import eu.scattering.core.impl.statistics.construct.FPlotDef;
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

    private final FExportEngine fExportEngine;
    private final FProtoEngine fProtoEngine;
    private final FRandEngine fRandEngine;
    private final FRotEngine fRotEngine;

    private final FTrigHelper fTrigHelper;
    private final StatisticsHelper fStatHelper;
    private final FTransferHelper fPosHelper;

    private final FPointHelper fPointHelper;
    private final FSphereHelper fSphereHelper;

    private FactoryDef() {
        this.fRandGenerator = FRandGeneratorDef.create();

        this.fExportEngine = FExportEngineDef.get(this);
        this.fRandEngine = FRandEngineDef.create(this.fRandGenerator, this);
        this.fProtoEngine = FProtoEngineDef.get();
        this.fRotEngine = FRotEngineDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();
        this.fPosHelper = FPositionHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);

        this.fPointHelper = FPointHelperDef.get();
        this.fSphereHelper = FSphereHelperDef.get(this.fPointHelper);
    }

    private FactoryDef(long seed) {
        this.fRandGenerator = FRandGeneratorDef.create(seed);

        this.fExportEngine = FExportEngineDef.get(this);
        this.fRandEngine = FRandEngineDef.create(this.fRandGenerator, this);
        this.fProtoEngine = FProtoEngineDef.get();
        this.fRotEngine = FRotEngineDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();
        this.fPosHelper = FPositionHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);

        this.fPointHelper = FPointHelperDef.get();
        this.fSphereHelper = FSphereHelperDef.get(this.fPointHelper);
    }

    public static ScatFactory create() {

        return new FactoryDef();
    }

    public static ScatFactory create(long seed) {

        return new FactoryDef(seed);
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

        return FPointProducerDef.create(this, this.fRandEngine);
    }

    @Override
    public FPoint getFPoint() {

        return FPointDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FVectorProducer getFVectorProducer() {

        return FVectorProducerDef.create(this, this.fRandEngine);
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

        return FDraftProducerDef.create(this, this.fRandEngine);
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
    public FPlaneProducer getFPlaneProducer() {

        return FPlaneProducerDef.create(this, this.fRandEngine);
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
    public FRayProducer getFRayProducer() {

        return FRayProducerDef.create(this, this.fRandEngine);
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
    public FLineProducer getFLineProducer() {

        return FLineProducerDef.create(this, this.fRandEngine);
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
    public FSegmentProducer getFSegmentProducer() {

        return FSegmentProducerDef.create(this, this.fRandEngine);
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

        return FSphereProducerDef.create(this, this.fRandEngine);
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

        return FAssemblyProducerDef.create(this, this.fRandEngine);
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
    public FAggregate getFAggregatePreMono(int quantity, double radius) {

        return FAggregatePreDef.createFAggregatePreMono(this, quantity, radius);
    }

    @Override
    public FAggregate getFAggregatePrePoly(int quantity, double avg, double std, double cutoff) {

        return FAggregatePreDef.createFAggregatePrePoly(this, quantity, avg, std, cutoff);
    }

    @Override
    public FAggregate getFAggregateGeo1d(int d1, double radius) {

        return FAggregateGeoDef.getFAggregateGeo1d(this, d1, radius);
    }

    @Override
    public FAggregate getFAggregateGeo2d(int d1, int d2, double radius) {

        return FAggregateGeoDef.getFAggregateGeo2d(this, d1, d2, radius);
    }

    @Override
    public FAggregate getFAggregateGeo3d(int d1, int d2, int d3, double radius) {

        return FAggregateGeoDef.getFAggregateGeo3d(this, d1, d2, d3, radius);
    }

    @Override
    public FAggregate getFAggregateGeoFullCircle(int layers, double radius) {

        return FAggregateGeoDef.getFAggregateGeoFullCircle(this, layers, radius);
    }

    @Override
    public FAggregate getFAggregateGeoFullSphere(int layers, double radius) {

        return FAggregateGeoDef.getFAggregateGeoFullSphere(this, layers, radius);
    }

    @Override
    public FModelRLA createFModelRLA3D(FAggregate aggregate) {

        return FModelRLA3DDef.create(aggregate, this);
    }

    @Override
    public FModelRLA createFModelRLA2D(FAggregate aggregate) {

        return FModelRLA2DDef.create(aggregate, this);
    }

    @Override
    public FModelPCBallistic createFModelBallistic3D(FAggregate aggregate) {

        return FModelPCBallistic3DDef.create(aggregate, this);
    }

    @Override
    public FModelPCBallistic createFModelBallistic2D(FAggregate aggregate) {

        return FModelPCBallistic2DDef.create(aggregate, this);
    }

    @Override
    public FModelPCTunable createFModelFilippov3D(FAggregate aggregate) {

        return FModelPCFilippov3DDef.create(aggregate, this);
    }

    @Override
    public FModelPCTunable createFModelFilippov2D(FAggregate aggregate) {

        return FModelPCFilippov2DDef.create(aggregate, this);
    }

    @Override
    public FModelPCTunable createFModelFilippov3D(FAggregate aggregate, double df, double kf) {

        return FModelPCFilippov3DDef.create(aggregate, this, df, kf);
    }

    @Override
    public FModelPCTunable createFModelFilippov2D(FAggregate aggregate, double df, double kf) {

        return FModelPCFilippov2DDef.create(aggregate, this, df, kf);
    }

    @Override
    public FModelDLA createFModelDLA3D(FAggregate aggregate) {

        return FModelDLA3DDef.create(aggregate, this);
    }

    @Override
    public FModelDLA createFModelDLA2D(FAggregate aggregate) {

        return FModelDLA2DDef.create(aggregate, this);
    }

    @Override
    public FMonitorConstruct getFMonitorRoG(int skip, FAggregate.RoG type) {

        return FMonitorRoGDef.create(this, skip, type);
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
    public FExportEngine getFExportEngine() {

        return this.fExportEngine;
    }

    @Override
    public FProtoEngine getFProtoEngine() {

        return this.fProtoEngine;
    }

    @Override
    public FRandEngine getFRandEngine() {

        return this.fRandEngine;
    }

    @Override
    public FRotEngine getFRotEngine() {

        return this.fRotEngine;
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
    public FPlot getFPlot() {

        return FPlotDef.create(this);
    }

    @Override
    public FPlot getFPlot(FLayer fLayer) {

        return FPlotDef.create(this, fLayer);
    }

    @Override
    public FPlot getRefFPlot(FStat refDataX, FStat refDataY) {

        return FPlotDef.create(this, refDataX, refDataY);
    }

    @Override
    public FPlot getFPlot(JSONObject json) {

        return FPlotDef.create(this, json);
    }

    @Override
    public FStat getFStat() {

        return FStatDef.create(this);
    }

    @Override
    public FStat getRefFStat(List<Double> refData) {

        return FStatDef.create(this, refData);
    }

    @Override
    public FStat getFStat(JSONObject json) {

        return FStatDef.create(this, json);
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
