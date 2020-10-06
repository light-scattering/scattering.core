package eu.scattering.core.impl.production;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.geometry.extension.line.FLine;
import eu.scattering.core.design.main.mutable.geometry.extension.plane.FPlane;
import eu.scattering.core.design.main.mutable.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.main.mutable.number.complex.FComplex;
import eu.scattering.core.design.main.mutable.number.quaternion.FQuaternion;
import eu.scattering.core.design.main.fixed.position.FPosition;
import eu.scattering.core.design.main.fixed.rotation.FRotation;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.impl.production.main.mutable.geometry.base.point.FPointProd;
import eu.scattering.core.impl.production.main.mutable.geometry.base.vector.FVectorProd;
import eu.scattering.core.impl.production.main.mutable.geometry.extension.line.FLineProd;
import eu.scattering.core.impl.production.main.mutable.geometry.extension.plane.FPlaneProd;
import eu.scattering.core.impl.production.main.mutable.geometry.shape.sphere.FSphereProd;
import eu.scattering.core.impl.production.main.mutable.number.complex.FComplexProd;
import eu.scattering.core.impl.production.main.mutable.number.quaternion.FQuaternionProd;
import eu.scattering.core.impl.production.main.fixed.position.FPositionProd;
import eu.scattering.core.impl.production.main.fixed.rotation.FRotationProd;
import eu.scattering.core.impl.production.support.helper.AngleHelperProd;
import eu.scattering.core.impl.production.support.helper.RandomHelperProd;
import eu.scattering.core.impl.production.support.helper.SignalHelperProd;

public final class FactoryProd implements Factory {

    private final RandomHelper helperRandom = RandomHelperProd.create(this);
    private final SignalHelper helperSignal = SignalHelperProd.create();
    private final AngleHelper helperAngle = AngleHelperProd.create();
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
    public FPosition getFPosition(int x, int y, int z) {

        return FPositionProd.create(this, x, y, z);
    }

    @Override
    public FPosition getFPosition(String structure) {

        return FPositionProd.parse(this, structure);
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
    public AngleHelper getHelperAngle() {

        return helperAngle;
    }

    @Override
    public SignalHelper getHelperSignal() {

        return helperSignal;
    }

    @Override
    public RandomHelper getHelperRandom() {

        return helperRandom;
    }


}
