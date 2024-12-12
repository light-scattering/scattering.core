package eu.scattering.core.impl.production;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.core.algebra.geometry.construct.line.FLine;
import eu.scattering.core.design.core.algebra.geometry.construct.plane.FPlane;
import eu.scattering.core.design.core.algebra.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.core.algebra.number.complex.FComplex;
import eu.scattering.core.design.core.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.core.engine.rotation.FRotation;
import eu.scattering.core.design.helpers.angle.FAngleHelper;
import eu.scattering.core.design.helpers.random.FRandomHelper;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.point.FPointProd;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.vector.FVectorProd;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.line.FLineProd;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.plane.FPlaneProd;
import eu.scattering.core.impl.production.core.mutable.geometry.shape.sphere.FSphereProd;
import eu.scattering.core.impl.production.core.mutable.number.complex.FComplexProd;
import eu.scattering.core.impl.production.core.mutable.number.quaternion.FQuaternionProd;
import eu.scattering.core.impl.production.core.immutable.rotation.FRotationProd;
import eu.scattering.core.impl.production.support.helper.AngleHelperProd;
import eu.scattering.core.impl.production.support.helper.RandomHelperProd;

public final class FactoryProd implements Factory {

    private final FRandomHelper helperRandom = RandomHelperProd.create(this);
    private final FAngleHelper helperAngle = AngleHelperProd.create();
    private double jitter = 1E-8;

    private FactoryProd() { }

    public static Factory create() {

        return new FactoryProd();
    }

    @Override
    public double getJitter() {

        return jitter;
    }

    @Override
    public Factory setJitter(double jitter) {

        this.jitter = jitter;

        return this;
    }

    @Override
    public FPoint getFPoint() {

        return FPointProd.create(this);
    }

    @Override
    public FVector getFVector() {

        return FVectorProd.create(this);
    }

    @Override
    public FLine getFLine() {

        return FLineProd.create(this);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneProd.create(this);
    }

    @Override
    public FSphere getFSphere() {

        return FSphereProd.create(this);
    }

    @Override
    public FComplex getFComplex() {

        return FComplexProd.create(this);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionProd.create(this);
    }

    @Override
    public FRotation getFRotation(FVector axis, double angle) {

        return FRotationProd.create(this, axis, angle);
    }

    @Override
    public FRotation getFRotation(FPoint axis, double angle) {

        return FRotationProd.create(this, axis, angle);
    }

    @Override
    public FRotation getFRotation(String structure) {

        return FRotationProd.parse(this, structure);
    }

    @Override
    public FAngleHelper getFAngleHelper() {

        return helperAngle;
    }

    @Override
    public FRandomHelper getFRandomHelper() {

        return helperRandom;
    }


}
