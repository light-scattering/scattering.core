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
import eu.scattering.core.impl.mutables.geometry.construct.line.FLineDef;
import eu.scattering.core.impl.mutables.geometry.construct.plane.FPlaneDef;
import eu.scattering.core.impl.mutables.geometry.construct.ray.FRayDef;
import eu.scattering.core.impl.mutables.geometry.construct.segment.FSegmentDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FPointDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FVectorDef;
import eu.scattering.core.impl.mutables.number.FComplexDef;
import eu.scattering.core.impl.mutables.number.FQuaternionDef;

public final class FactoryProd extends FactoryDesignConcrete {
    private final double epsilon = 1E-8;
    private final double proximityLimit = 1E-6;

    private final FTrigHelper fAngleHelper;
    private final FRandomEngine fRandomHelper;
    private final FRotationEngine fRotationHelper;

    private FactoryProd() {
        var fRandomInternal = FRandomProcessorDef.create();

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomEngineDef.create(fRandomInternal);
        this.fRotationHelper = FRotationEngineDef.create(getFRotationProcessor());
        this.fAngleHelper = FTrigHelperDef.create();
    }

    private FactoryProd(long seed) {
        var fRandomInternal = FRandomProcessorDef.create(seed);

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
    public FPoint getFPoint() {

        return FPointDef.create(epsilon);
    }

    @Override
    public FVector getFVector() {

        return FVectorDef.create(epsilon, getFPoint(), getFPoint());
    }

    @Override
    public FVector getRefFVector(FPoint refHead) {

        return FVectorDef.create(epsilon, getFPoint(), refHead);
    }

    @Override
    public FVector getRefFVector(FPoint refBase, FPoint refHead) {

        return FVectorDef.create(epsilon, refBase, refHead);
    }

    public FLine getFLine() {

        return FLineDef.create(epsilon, this::getFVector);
    }

    @Override
    public FLine getRefFLine(FVector refOrigin) {

        return FLineDef.create(epsilon, this::getFVector, refOrigin);
    }

    @Override
    public FRay getFRay() {

        return FRayDef.create(epsilon, this::getFVector);
    }

    @Override
    public FRay getRefFRay(FVector refOrigin) {

        return FRayDef.create(epsilon, this::getFVector, refOrigin);
    }

    @Override
    public FSegment getFSegment() {

        return FSegmentDef.create(epsilon, this::getFVector);
    }

    @Override
    public FSegment getRefFSegment(FVector refOrigin) {

        return FSegmentDef.create(epsilon, this::getFVector, refOrigin);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDef.create(epsilon, this::getFLine, this::getFVector);
    }

    @Override
    public FPlane getRefFPlane(FVector refOrigin) {

        return FPlaneDef.create(epsilon, this::getFLine, this::getFVector, refOrigin);
    }

    @Override
    public FSphere getFSphere() {

        return null;
    }

//--------------------------------------------------

    @Override
    public FComplex getFComplex() {

        return FComplexDef.create(epsilon, 0, 0);
    }

    public FComplex getFComplex(double re, double im) {

        return FComplexDef.create(epsilon, re, im);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDef.create(epsilon, 0, 0, 0, 0);
    }

    public FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return FQuaternionDef.create(epsilon, re, i, j, k);
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

        return FRotationProcessorDef.create(this::getFVector);
    }
}
