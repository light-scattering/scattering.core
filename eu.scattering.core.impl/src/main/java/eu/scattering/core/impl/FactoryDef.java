package eu.scattering.core.impl;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import eu.scattering.core.design.engine.prototype.FProtoEngine;
import eu.scattering.core.design.helper.statistics.FStatHelper;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.rotate.generator.FRotGenerator;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.impl.component.geometry.GeometryParserDef;
import eu.scattering.core.impl.component.geometry.base.FPointProducerDef;
import eu.scattering.core.impl.component.geometry.base.FVectorProducerDef;
import eu.scattering.core.impl.component.geometry.construct.*;
import eu.scattering.core.impl.component.geometry.container.FAssemblyDef;
import eu.scattering.core.impl.component.geometry.container.FAssemblyProducerDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereProducerDef;
import eu.scattering.core.impl.component.number.FComplexProducerDef;
import eu.scattering.core.impl.component.number.FQuaternionProducerDef;
import eu.scattering.core.impl.engine.prototype.FProtoEngineDef;
import eu.scattering.core.impl.engine.randomize.FRandEngineDef;
import eu.scattering.core.impl.engine.randomize.FRandGeneratorDef;
import eu.scattering.core.impl.engine.rotate.FRotEngineDef;
import eu.scattering.core.impl.engine.rotate.FRotProcessorDef;
import eu.scattering.core.impl.helper.FStatHelperDef;
import eu.scattering.core.impl.helper.FTrigHelperDef;
import eu.scattering.core.impl.component.geometry.base.FPointDef;
import eu.scattering.core.impl.component.geometry.base.FVectorDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereDef;
import eu.scattering.core.impl.component.number.FComplexDef;
import eu.scattering.core.impl.component.number.FQuaternionDef;

public final class FactoryDef extends FactoryDesignConcrete {
    private final GeometryParser fGeometryParser;

    private final FRandGenerator fRandGenerator;
    private final FRotGenerator fRotGenerator;

    private final FProtoEngine fProtoEngine;
    private final FRandEngine fRandEngine;
    private final FRotEngine fRotEngine;

    private final FTrigHelper fTrigHelper;
    private final FStatHelper fStatHelper;

    private FactoryDef() {
        this.fRandGenerator = FRandGeneratorDef.create();

        this.fRandEngine = FRandEngineDef.create(this.fRandGenerator);
        this.fProtoEngine = FProtoEngineDef.get();
        this.fRotEngine = FRotEngineDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);
    }

    private FactoryDef(long seed) {
        this.fRandGenerator = FRandGeneratorDef.create(seed);

        this.fRandEngine = FRandEngineDef.create(this.fRandGenerator);
        this.fProtoEngine = FProtoEngineDef.get();
        this.fRotEngine = FRotEngineDef.create(FRotProcessorDef.get());

        this.fRotGenerator = FRotProcessorDef.get();

        this.fTrigHelper = FTrigHelperDef.get();
        this.fStatHelper = FStatHelperDef.get();

        this.fGeometryParser = GeometryParserDef.get(this);
    }

    public static FactoryDesignConcrete create() {

        return new FactoryDef();
    }

    public static FactoryDesignConcrete create(long seed) {

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
    public FPointProducer getFPointProducer() {

        return FPointProducerDef.create(this, this.fRandGenerator);
    }

    @Override
    public FPoint getFPoint() {

        return FPointDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FVectorProducer getFVectorProducer() {

        return FVectorProducerDef.create(this, this.fRandGenerator);
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

        return FDraftProducerDef.create(this, this.fRandGenerator);
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

        return FPlaneProducerDef.create(this, this.fRandGenerator);
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

        return FRayProducerDef.create(this, this.fRandGenerator);
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

        return FLineProducerDef.create(this, this.fRandGenerator);
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

        return FSegmentProducerDef.create(this, this.fRandGenerator);
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
    public FSphereProducer getFSphereProducer() {

        return FSphereProducerDef.create(this, this.fRandGenerator);
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

        return FAssemblyProducerDef.create(this, this.fRandGenerator);
    }

    @Override
    public <T extends Geometry> FAssembly<T> getFAssembly() {

        return FAssemblyDef.create(this);
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator getFRandGen(long seed) {

        return FRandGeneratorDef.create(seed);
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator getFRandGenShared() {

        return this.fRandGenerator;
    }

    @Override
    public FRotGenerator getFRotGenShared() {

        return this.fRotGenerator;
    }


    //--------------------------------------------------

    @Override
    public FProtoEngine getFProtoEngShared() {

        return this.fProtoEngine;
    }

    @Override
    public FRandEngine getFRandEngShared() {

        return this.fRandEngine;
    }

    @Override
    public FRotEngine getFRotEngShared() {

        return this.fRotEngine;
    }

    //--------------------------------------------------

    @Override
    public FStatHelper getFStatHelperShared() {

        return this.fStatHelper;
    }

    @Override
    public FTrigHelper getFTrigHelperShared() {

        return this.fTrigHelper;
    }

    //--------------------------------------------------

    @Override
    public GeometryParser getGeometryParserShared() {

        return this.fGeometryParser;
    }
}
