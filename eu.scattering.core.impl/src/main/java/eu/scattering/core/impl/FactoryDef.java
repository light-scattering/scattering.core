package eu.scattering.core.impl;

import eu.scattering.core.design.ScatFactoryConcrete;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
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
import eu.scattering.core.design.helper.statistics.FStatHelper;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.impl.component.aggregate.FAggregateDef;
import eu.scattering.core.impl.component.aggregate.model.FModelBallistic2DDef;
import eu.scattering.core.impl.component.aggregate.model.FModelBallistic3DDef;
import eu.scattering.core.impl.component.aggregate.model.FModelRLA3DDef;
import eu.scattering.core.impl.component.aggregate.model.FModelRLA2DDef;
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
import eu.scattering.core.impl.helper.FStatHelperDef;
import eu.scattering.core.impl.helper.FTrigHelperDef;
import eu.scattering.core.transfer.container.buffer.array.FArray;

import java.util.ArrayList;
import java.util.List;

public final class FactoryDef extends ScatFactoryConcrete {
    private final GeometryParser fGeometryParser;

    private final FRandGenerator fRandGenerator;
    private final FRotGenerator fRotGenerator;

    private final FExportEngine fExportEngine;
    private final FProtoEngine fProtoEngine;
    private final FRandEngine fRandEngine;
    private final FRotEngine fRotEngine;

    private final FTrigHelper fTrigHelper;
    private final FStatHelper fStatHelper;

    private final FPointHelper fPointHelper;
    private final FSphereHelper fSphereHelper;

    private FactoryDef() {
        this.fRandGenerator = FRandGeneratorDef.create();

        this.fExportEngine = FExportEngineDef.get();
        this.fRandEngine = FRandEngineDef.create(this.fRandGenerator, this);
        this.fProtoEngine = FProtoEngineDef.get();
        this.fRotEngine = FRotEngineDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);

        this.fPointHelper = FPointHelperDef.get();
        this.fSphereHelper = FSphereHelperDef.get(this.fPointHelper);
    }

    private FactoryDef(long seed) {
        this.fRandGenerator = FRandGeneratorDef.create(seed);

        this.fExportEngine = FExportEngineDef.get();
        this.fRandEngine = FRandEngineDef.create(this.fRandGenerator, this);
        this.fProtoEngine = FProtoEngineDef.get();
        this.fRotEngine = FRotEngineDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);

        this.fPointHelper = FPointHelperDef.get();
        this.fSphereHelper = FSphereHelperDef.get(this.fPointHelper);
    }

    public static ScatFactoryConcrete create() {

        return new FactoryDef();
    }

    public static ScatFactoryConcrete create(long seed) {

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

    //--------------------------------------------------

    @Override
    public FAggregate getFAggregate() {

        return FAggregateDef.create(this, getFAssembly(), getFArray());
    }

    @Override
    public FAggregate getFAggregate(int capacity) {

        return FAggregateDef.create(this, getFAssembly(), getFArray(capacity));
    }

    @Override
    public FAggregate getFAggregate(FAssembly<Shape> particles) {

        return FAggregateDef.create(this, particles.copy(), getFArray());
    }

    @Override
    public FAggregate getFAggregate(FAssembly<Shape> particles, int capacity) {

        return FAggregateDef.create(this, particles.copy(), getFArray(capacity));
    }

    @Override
    public FAggregate getRefFAggregate(FAssembly<Shape> refParticles) {

        return FAggregateDef.create(this, refParticles, getFArray());
    }

    @Override
    public FAggregate getRefFAggregate(FAssembly<Shape> refParticles, int capacity) {

        return FAggregateDef.create(this, refParticles, getFArray(capacity));
    }

    @Override
    public FAggregate getRefFAggregate(FAssembly<Shape> refParticles, FArray<FMetaData> refElements) {

        return FAggregateDef.create(this, refParticles, refElements);
    }

    @Override
    public FModel createFModelRLA3D(FAggregate aggregate) {

        return FModelRLA3DDef.create(aggregate, this.fRandEngine);
    }

    @Override
    public FModel createFModelRLA2D(FAggregate aggregate) {

        return FModelRLA2DDef.create(aggregate, this.fRandEngine);
    }

    @Override
    public FModel createFModelBallistic3D(FAggregate aggregate) {

        return FModelBallistic3DDef.create(aggregate, this);
    }

    @Override
    public FModel createFModelBallistic2D(FAggregate aggregate) {

        return FModelBallistic2DDef.create(aggregate, this);
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
    public FStatHelper getFStatHelper() {

        return this.fStatHelper;
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
}
