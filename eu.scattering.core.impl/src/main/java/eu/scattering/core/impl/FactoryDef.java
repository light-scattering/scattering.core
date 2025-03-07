package eu.scattering.core.impl;

import eu.scattering.core.design.FactoryDesignConcrete;
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
import eu.scattering.core.impl.engine.prototype.FProtoEngineDef;
import eu.scattering.core.impl.engine.randomize.FRandEngineDef;
import eu.scattering.core.impl.engine.randomize.FRandProcessorDef;
import eu.scattering.core.impl.engine.rotate.FRotEngineDef;
import eu.scattering.core.impl.engine.rotate.FRotProcessorDef;
import eu.scattering.core.impl.helper.FStatHelperDef;
import eu.scattering.core.impl.helper.FTrigHelperDef;
import eu.scattering.core.impl.component.geometry.construct.FLineDef;
import eu.scattering.core.impl.component.geometry.construct.FPlaneDef;
import eu.scattering.core.impl.component.geometry.construct.FRayDef;
import eu.scattering.core.impl.component.geometry.construct.FSegmentDef;
import eu.scattering.core.impl.component.geometry.base.FPointDef;
import eu.scattering.core.impl.component.geometry.base.FVectorDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereDef;
import eu.scattering.core.impl.component.number.FComplexDef;
import eu.scattering.core.impl.component.number.FQuaternionDef;

public final class FactoryDef extends FactoryDesignConcrete {
    private final FProtoEngine fProtEngine;
    private final FRandEngine fRandEngine;
    private final FRotEngine fRotEngine;

    private final FTrigHelper fAngleHelper;
    private final FStatHelper fStatHelper;

    private FactoryDef() {
        FRandGenerator fRandInternal = FRandProcessorDef.create();

        fRandInternal.setProximityLimit(ConfigDef.PROXIMITY_LIMIT);

        this.fRandEngine = FRandEngineDef.create(fRandInternal);
        this.fProtEngine = FProtoEngineDef.create();
        this.fRotEngine = FRotEngineDef.create(getFRotGenerator());

        this.fAngleHelper = FTrigHelperDef.create();
        this.fStatHelper = FStatHelperDef.create();
    }

    private FactoryDef(long seed) {
        FRandGenerator fRandInternal = FRandProcessorDef.create(seed);

        fRandInternal.setProximityLimit(ConfigDef.PROXIMITY_LIMIT);

        this.fRandEngine = FRandEngineDef.create(fRandInternal);
        this.fProtEngine = FProtoEngineDef.create();
        this.fRotEngine = FRotEngineDef.create(getFRotGenerator());

        this.fAngleHelper = FTrigHelperDef.create();
        this.fStatHelper = FStatHelperDef.create();
    }

    public static FactoryDesignConcrete create() {

        return new FactoryDef();
    }

    public static FactoryDesignConcrete create(long seed) {

        return new FactoryDef(seed);
    }

    //--------------------------------------------------

    @Override
    public FPoint getFPoint() {

        return FPointDef.create(0, 0, 0);
    }

    @Override
    public FPoint getFPoint(double x, double y, double z) {

        return FPointDef.create(x, y, z);
    }

    @Override
    public FVector getFVector() {

        return FVectorDef.create(getFPoint(), getFPoint());
    }

    @Override
    public FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return FVectorDef.create(getFPoint(bX, bY, bZ), getFPoint(hX, hY, hZ));
    }

    @Override
    public FVector getRefFVector(FPoint refHead) {

        return FVectorDef.create(getFPoint(), refHead);
    }

    @Override
    public FVector getRefFVector(FPoint refBase, FPoint refHead) {

        return FVectorDef.create(refBase, refHead);
    }

    //--------------------------------------------------

    @Override
    public FPlane getFPlane() {

        return FPlaneDef.create(this, getFVector());
    }

    @Override
    public FPlane getRefFPlane(FVector refOrigin) {

        return FPlaneDef.create(this, refOrigin);
    }

    @Override
    public FRay getFRay() {

        return FRayDef.create(this, getFVector());
    }

    @Override
    public FRay getRefFRay(FVector refOrigin) {

        return FRayDef.create(this, refOrigin);
    }

    @Override
    public FLine getFLine() {

        return FLineDef.create(this, getFVector());
    }

    @Override
    public FLine getRefFLine(FVector refOrigin) {

        return FLineDef.create(this, refOrigin);
    }

    @Override
    public FSegment getFSegment() {

        return FSegmentDef.create(this, getFVector());
    }

    @Override
    public FSegment getRefFSegment(FVector refOrigin) {

        return FSegmentDef.create(this, refOrigin);
    }

    //--------------------------------------------------

    @Override
    public FSphere getFSphere() {

        return FSphereDef.create(this, getFPoint());
    }

    @Override
    public FSphere getFSphere(double radius) {

        return FSphereDef.create(this, getFPoint(), radius);
    }

    @Override
    public FSphere getFSphere(double x, double y, double z) {

        return FSphereDef.create(this, getFPoint(x, y, z));
    }

    @Override
    public FSphere getFSphere(double x, double y, double z, double radius) {

        return FSphereDef.create(this, getFPoint(x, y, z), radius);
    }

    @Override
    public FSphere getRefFSphere(FPoint refCenter) {

        return FSphereDef.create(this, refCenter);
    }

    @Override
    public FSphere getRefFSphere(FPoint refCenter, double radius) {

        return FSphereDef.create(this, refCenter, radius);
    }

    //--------------------------------------------------

    @Override
    public FComplex getFComplex() {

        return FComplexDef.create(0, 0);
    }

    @Override
    public FComplex getFComplex(double re, double im) {

        return FComplexDef.create(re, im);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDef.create(0, 0, 0, 0);
    }

    @Override
    public FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return FQuaternionDef.create(re, i, j, k);
    }

    //--------------------------------------------------

    @Override
    public FProtoEngine getFProtoEngine() {

        return fProtEngine;
    }

    @Override
    public FRandEngine getFRandEngine() {

        return fRandEngine;
    }

    @Override
    public FRotEngine getFRotEngine() {

        return fRotEngine;
    }

    //--------------------------------------------------

    @Override
    public FTrigHelper getFTrigHelper() {

        return fAngleHelper;
    }

    @Override
    public FStatHelper getFStatHelper() {

        return fStatHelper;
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator getFRandGenerator() {

        return FRandProcessorDef.create();
    }

    @Override
    public FRandGenerator getFRandGenerator(long seed) {

        return FRandProcessorDef.create(seed);
    }

    @Override
    public FRotGenerator getFRotGenerator() {

        return FRotProcessorDef.create();
    }
}
