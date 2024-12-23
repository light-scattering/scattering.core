package eu.scattering.core.impl.production;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.geometry.construct.line.FLine;
import eu.scattering.core.design.elements.algebra.geometry.construct.plane.FPlane;
import eu.scattering.core.design.elements.algebra.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.random.FRandom;
import eu.scattering.core.design.elements.engine.rotation.FRotation;
import eu.scattering.core.design.helpers.auxiliary.FAngleHelper;
import eu.scattering.core.design.helpers.transfer.FPositionHelper;
import eu.scattering.core.impl.production.core.engine.random.FRandomProd;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.point.FPointProd;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.vector.FVectorProd;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.line.FLineProd;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.plane.FPlaneProd;
import eu.scattering.core.impl.production.core.mutable.number.complex.FComplexProd;
import eu.scattering.core.impl.production.core.mutable.number.quaternion.FQuaternionProd;
import eu.scattering.core.impl.production.core.immutable.rotation.FRotationProd;
import eu.scattering.core.impl.production.support.helper.FAngleHelperProd;
import eu.scattering.core.impl.production.support.helper.FPositionHelperProd;

public final class FactoryProd implements Factory {
    private final FPositionHelper fPositionHelper;
    private final FAngleHelper fAngleHelper;

    private final FRandom fRandomInternal;

    private final double proximityThreshold = 1E-6;
    private final double epsilon = 1E-8;

    private FactoryProd() {

        fRandomInternal = FRandomProd.create();
        fRandomInternal.setProximityThreshold(proximityThreshold);
    }

    private FactoryProd(long seed) {

        fRandomInternal = FRandomProd.create(seed);
        fRandomInternal.setProximityThreshold(proximityThreshold);
    }

    {
        fPositionHelper = FPositionHelperProd.create();
        fAngleHelper = FAngleHelperProd.create();
    }

    public static Factory create() {

        return new FactoryProd();
    }

    public static Factory create(long seed) {

        return new FactoryProd(seed);
    }

    //--------------------------------------------------

    @Override
    public FPoint getFPoint() {

        return FPointProd.create(this, fRandomInternal, epsilon);
    }

    @Override
    public FVector getFVector() {

        return FVectorProd.create(this, fRandomInternal, epsilon);
    }

    @Override
    public FLine getFLine() {

        return FLineProd.create(this, fRandomInternal, epsilon);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneProd.create(this, fRandomInternal, epsilon);
    }

    @Override
    public FSphere getFSphere() {

        return null;
    }

//--------------------------------------------------

    @Override
    public FComplex getFComplex() {

        return FComplexProd.create(this, fRandomInternal, epsilon);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionProd.create(this, fRandomInternal, epsilon);
    }

//--------------------------------------------------

    @Override
    public FAngleHelper getFAngleHelper() {

        return fAngleHelper;
    }

    @Override
    public FPositionHelper getFPositionHelper() {

        return fPositionHelper;
    }

//--------------------------------------------------

    //TODO Maybe this one should be removed...
    @Override
    public FRotation getFRotation(FPoint axis, double angle) {

        return FRotationProd.create(this, axis, angle);
    }

    @Override
    public FRotation getFRotation(FVector axis, double angle) {

        return FRotationProd.create(this, axis, angle);
    }

    @Override
    public FRandom getFRandom() {

        return FRandomProd.create();
    }

    @Override
    public FRandom getFRandom(long seed) {

        return FRandomProd.create(seed);
    }
}
