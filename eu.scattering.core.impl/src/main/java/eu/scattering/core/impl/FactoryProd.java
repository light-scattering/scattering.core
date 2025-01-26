package eu.scattering.core.impl;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.design.mutables.number.quaternion.FQuaternion;
import eu.scattering.core.design.engines.random.processor.FRandomProcessor;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.helpers.auxiliary.FTrigHelper;
import eu.scattering.core.design.engines.random.FRandomEngine;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.impl.engines.random.FRandomEngineDef;
import eu.scattering.core.impl.engines.random.FRandomProcessorDef;
import eu.scattering.core.impl.engines.rotation.FRotationEngineDef;
import eu.scattering.core.impl.engines.rotation.FRotationProcessorDef;
import eu.scattering.core.impl.helpers.FTrigHelperDef;
import eu.scattering.core.impl.mutables.geometry.construct.FLineDef;
import eu.scattering.core.impl.mutables.geometry.construct.FPlaneDef;
import eu.scattering.core.impl.mutables.geometry.construct.FRayDef;
import eu.scattering.core.impl.mutables.geometry.construct.FSegmentDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FPointDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FVectorDef;
import eu.scattering.core.impl.mutables.number.FComplexDef;
import eu.scattering.core.impl.mutables.number.FQuaternionDef;

import java.util.function.Supplier;

public final class FactoryProd extends FactoryDesignConcrete {
    private final static double epsilon = 1E-8;
    private final static double proximityLimit = 1E-6;

    private final FTrigHelper fAngleHelper;
    private final FRandomEngine fRandomHelper;
    private final FRotationEngine fRotationHelper;

    private FactoryProd() {
        FRandomProcessor fRandomInternal = FRandomProcessorDef.create();

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomEngineDef.create(fRandomInternal);
        this.fRotationHelper = FRotationEngineDef.create(getFRotationProcessor());
        this.fAngleHelper = FTrigHelperDef.create();
    }

    private FactoryProd(long seed) {
        FRandomProcessor fRandomInternal = FRandomProcessorDef.create(seed);

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomEngineDef.create(fRandomInternal);
        this.fRotationHelper = FRotationEngineDef.create(getFRotationProcessor());
        this.fAngleHelper = FTrigHelperDef.create();
    }

    public static FactoryDesignConcrete create() {

        return new FactoryProd();
    }

    public static FactoryDesignConcrete create(long seed) {

        return new FactoryProd(seed);
    }

    //--------------------------------------------------

    @Override
    public void initialize() {
        Supplier<FLine> fLineSupplier = this::getFLine;
        Supplier<FPoint> fPointSupplier = this::getFPoint;
        Supplier<FVector> fVectorSupplier = this::getFVector;

        FComplexDef.initialize(epsilon);
        FQuaternionDef.initialize(epsilon);

        FPointDef.initialize(epsilon);
        FVectorDef.initialize(epsilon);

        FLineDef.initialize(epsilon, fPointSupplier, fVectorSupplier);
        FRayDef.initialize(epsilon, fPointSupplier, fVectorSupplier);
        FSegmentDef.initialize(epsilon, fVectorSupplier);
        FPlaneDef.initialize(epsilon, fLineSupplier, fVectorSupplier);
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

    @Override
    public FLine getFLine() {

        return FLineDef.create(getFVector());
    }

    @Override
    public FLine getRefFLine(FVector refOrigin) {

        return FLineDef.create(refOrigin);
    }

    @Override
    public FRay getFRay() {

        return FRayDef.create(getFVector());
    }

    @Override
    public FRay getRefFRay(FVector refOrigin) {

        return FRayDef.create(refOrigin);
    }

    @Override
    public FSegment getFSegment() {

        return FSegmentDef.create(getFVector());
    }

    @Override
    public FSegment getRefFSegment(FVector refOrigin) {

        return FSegmentDef.create(refOrigin);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDef.create(getFVector());
    }

    @Override
    public FPlane getRefFPlane(FVector refOrigin) {

        return FPlaneDef.create(refOrigin);
    }

    @Override
    public FSphere getFSphere() {

        return null;
    }

//--------------------------------------------------

    @Override
    public FComplex getFComplex() {

        return FComplexDef.create(0, 0);
    }

    public FComplex getFComplex(double re, double im) {

        return FComplexDef.create(re, im);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDef.create(0, 0, 0, 0);
    }

    public FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return FQuaternionDef.create(re, i, j, k);
    }

//--------------------------------------------------

    @Override
    public FTrigHelper getFTrigHelper() {

        return fAngleHelper;
    }

    @Override
    public FRandomEngine getFRandomEngine() {

        return fRandomHelper;
    }

    @Override
    public FRotationEngine getFRotationEngine() {

        return fRotationHelper;
    }

//--------------------------------------------------

    @Override
    public FRandomProcessor getFRandomProcessor() {

        return FRandomProcessorDef.create();
    }

    @Override
    public FRandomProcessor getFRandomProcessor(long seed) {

        return FRandomProcessorDef.create(seed);
    }

    @Override
    public FRotationProcessor getFRotationProcessor() {

        return FRotationProcessorDef.create();
    }
}
