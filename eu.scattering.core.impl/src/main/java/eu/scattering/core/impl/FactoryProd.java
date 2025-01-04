package eu.scattering.core.impl;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.design.mutables.number.quaternion.FQuaternion;
import eu.scattering.core.design.engines.random.processor.FRandomProcessor;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.helpers.auxiliary.FAngleHelper;
import eu.scattering.core.design.engines.random.FRandomEngine;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.impl.engines.random.FRandomEngineDef;
import eu.scattering.core.impl.engines.random.FRandomProcessorDef;
import eu.scattering.core.impl.engines.rotation.FRotationEngineDef;
import eu.scattering.core.impl.engines.rotation.FRotationProcessorDef;
import eu.scattering.core.impl.helpers.FAngleHelperDef;
import eu.scattering.core.impl.mutables.geometry.construct.line.FLineDef;
import eu.scattering.core.impl.mutables.geometry.construct.plane.FPlaneDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FPointDef;
import eu.scattering.core.impl.mutables.geometry.primitive.FVectorDef;
import eu.scattering.core.impl.mutables.number.FComplexDef;
import eu.scattering.core.impl.mutables.number.FQuaternionDef;

public final class FactoryProd extends FactoryDesignConcrete {
    private final double epsilon = 1E-8;
    private final double proximityLimit = 1E-6;

    private final FAngleHelper fAngleHelper;
    private final FRandomEngine fRandomHelper;
    private final FRotationEngine fRotationHelper;

    private FactoryProd() {
        var fRandomInternal = FRandomProcessorDef.create();

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomEngineDef.create(fRandomInternal);
        this.fRotationHelper = FRotationEngineDef.create(getFRotationProcessor());
        this.fAngleHelper = FAngleHelperDef.create();
    }

    private FactoryProd(long seed) {
        var fRandomInternal = FRandomProcessorDef.create(seed);

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomEngineDef.create(fRandomInternal);
        this.fRotationHelper = FRotationEngineDef.create(getFRotationProcessor());
        this.fAngleHelper = FAngleHelperDef.create();
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
    public FVector getRefFVector(FPoint refBase, FPoint refHead) {

        return FVectorDef.create(epsilon, this::getFPoint, refBase, refHead);
    }

    @Override
    public FVector getRefFVector(FPoint refHead) {

        return FVectorDef.create(epsilon, this::getFPoint, refHead);
    }

    @Override
    public FVector getFVector() {

        return FVectorDef.create(epsilon, this::getFPoint);
    }

    @Override
    public FLine getFLine() {

        return FLineDef.create(this, epsilon);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDef.create(this, epsilon);
    }

    @Override
    public FSphere getFSphere() {

        return null;
    }

//--------------------------------------------------

    @Override
    public FComplex getFComplex() {

        return FComplexDef.create(epsilon);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDef.create(epsilon);
    }

//--------------------------------------------------

    @Override
    public FAngleHelper getFAngleHelper() {

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
