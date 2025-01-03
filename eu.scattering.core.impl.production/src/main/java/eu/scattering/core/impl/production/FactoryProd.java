package eu.scattering.core.impl.production;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.elements.algebra.geometry.construct.line.FLine;
import eu.scattering.core.design.elements.algebra.geometry.construct.plane.FPlane;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.random.FRandom;
import eu.scattering.core.design.elements.engine.rotation.FRotation;
import eu.scattering.core.design.helpers.auxiliary.FAngleHelper;
import eu.scattering.core.design.helpers.engine.FRandomHelper;
import eu.scattering.core.design.helpers.engine.FRotationHelper;
import eu.scattering.core.impl.production.core.engine.random.FRandomProd;
import eu.scattering.core.impl.production.core.immutable.rotation.FRotationProd;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.line.FLineProd;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.plane.FPlaneProd;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.point.FPointProd;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.vector.FVectorProd;
import eu.scattering.core.impl.production.core.mutable.number.complex.FComplexProd;
import eu.scattering.core.impl.production.core.mutable.number.quaternion.FQuaternionProd;
import eu.scattering.core.impl.production.support.helper.FAngleHelperProd;
import eu.scattering.core.impl.production.support.helper.FRandomHelperProd;
import eu.scattering.core.impl.production.support.helper.FRotationHelperProd;

public final class FactoryProd extends FactoryDesignConcrete {
    private final double epsilon = 1E-8;
    private final double proximityLimit = 1E-6;

    private final FAngleHelper fAngleHelper;
    private final FRandomHelper fRandomHelper;
    private final FRotationHelper fRotationHelper;

    private FactoryProd() {
        var fRandomInternal = FRandomProd.create();

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomHelperProd.create(fRandomInternal);
        this.fRotationHelper = FRotationHelperProd.create(getFRotation());
        this.fAngleHelper = FAngleHelperProd.create();
    }

    private FactoryProd(long seed) {
        var fRandomInternal = FRandomProd.create(seed);

        fRandomInternal.setProximityLimit(proximityLimit);

        this.fRandomHelper = FRandomHelperProd.create(fRandomInternal);
        this.fRotationHelper = FRotationHelperProd.create(getFRotation());
        this.fAngleHelper = FAngleHelperProd.create();
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

        return FPointProd.create(epsilon);
    }

    @Override
    public FVector getFVector() {

        return FVectorProd.create(epsilon, this::getFPoint);
    }

    @Override
    public FLine getFLine() {

        return FLineProd.create(this, epsilon);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneProd.create(this, epsilon);
    }

    @Override
    public FSphere getFSphere() {

        return null;
    }

//--------------------------------------------------

    @Override
    public FComplex getFComplex() {

        return FComplexProd.create(this, epsilon);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionProd.create(this, epsilon);
    }

//--------------------------------------------------

    @Override
    public FAngleHelper getFAngleHelper() {

        return fAngleHelper;
    }

    @Override
    public FRandomHelper getFRandomHelper() {

        return fRandomHelper;
    }

    @Override
    public FRotationHelper getFRotationHelper() {

        return fRotationHelper;
    }

//--------------------------------------------------

    @Override
    public FRandom getFRandom() {

        return FRandomProd.create();
    }

    @Override
    public FRandom getFRandom(long seed) {

        return FRandomProd.create(seed);
    }

    @Override
    public FRotation getFRotation() {

        return FRotationProd.create(this::getFVector);
    }
}
