package eu.scattering.core.impl;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.engines.prot.FProtEngine;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.design.mutables.number.quaternion.FQuaternion;
import eu.scattering.core.design.engines.rand.processor.FRandProcessor;
import eu.scattering.core.design.engines.rot.processor.FRotProcessor;
import eu.scattering.core.design.helpers.auxiliary.FTrigHelper;
import eu.scattering.core.design.engines.rand.FRandEngine;
import eu.scattering.core.design.engines.rot.FRotEngine;
import eu.scattering.core.impl.engines.prot.FProtEngineDef;
import eu.scattering.core.impl.engines.rand.FRandEngineDef;
import eu.scattering.core.impl.engines.rand.FRandProcessorDef;
import eu.scattering.core.impl.engines.rot.FRotEngineDef;
import eu.scattering.core.impl.engines.rot.FRotProcessorDef;
import eu.scattering.core.impl.helpers.FTrigHelperDef;
import eu.scattering.core.impl.mutables.geometry.construct.FLineDef;
import eu.scattering.core.impl.mutables.geometry.construct.FPlaneDef;
import eu.scattering.core.impl.mutables.geometry.construct.FRayDef;
import eu.scattering.core.impl.mutables.geometry.construct.FSegmentDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FPointDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FVectorDef;
import eu.scattering.core.impl.mutables.number.FComplexDef;
import eu.scattering.core.impl.mutables.number.FQuaternionDef;

public final class FactoryDef extends FactoryDesignConcrete {
    private final FTrigHelper fAngleHelper;
    private final FRandEngine fRandHelper;
    private final FRotEngine fRotEngine;
    private final FProtEngine fProtEngine;

    private FactoryDef() {
        FRandProcessor fRandInternal = FRandProcessorDef.create();

        fRandInternal.setProximityLimit(ConfigDef.PROXIMITY_LIMIT);

        this.fRandHelper = FRandEngineDef.create(fRandInternal);
        this.fRotEngine = FRotEngineDef.create(getFRotProcessor());
        this.fProtEngine = FProtEngineDef.create();

        this.fAngleHelper = FTrigHelperDef.create();
    }

    private FactoryDef(long seed) {
        FRandProcessor fRandInternal = FRandProcessorDef.create(seed);

        fRandInternal.setProximityLimit(ConfigDef.PROXIMITY_LIMIT);

        this.fRandHelper = FRandEngineDef.create(fRandInternal);
        this.fRotEngine = FRotEngineDef.create(getFRotProcessor());
        this.fProtEngine = FProtEngineDef.create();

        this.fAngleHelper = FTrigHelperDef.create();
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
    public FRandEngine getFRandEngine() {

        return fRandHelper;
    }

    @Override
    public FRotEngine getFRotEngine() {

        return fRotEngine;
    }

    @Override
    public FProtEngine getFProtEngine() {

        return fProtEngine;
    }

//--------------------------------------------------

    @Override
    public FRandProcessor getFRandProcessor() {

        return FRandProcessorDef.create();
    }

    @Override
    public FRandProcessor getFRandProcessor(long seed) {

        return FRandProcessorDef.create(seed);
    }

    @Override
    public FRotProcessor getFRotProcessor() {

        return FRotProcessorDef.create();
    }


}
