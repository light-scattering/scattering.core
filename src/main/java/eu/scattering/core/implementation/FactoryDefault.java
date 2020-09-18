package eu.scattering.core.implementation;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.box.position.FPosition;
import eu.scattering.core.design.main.box.rotation.FRotation;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.box.position.FPositionDefault;
import eu.scattering.core.implementation.main.box.rotation.FRotationDefault;
import eu.scattering.core.implementation.support.helper.AngleHelperDefault;
import eu.scattering.core.implementation.support.helper.RandomHelperDefault;
import eu.scattering.core.implementation.support.helper.SignalHelperDefault;

public final class FactoryDefault implements Factory {

    private double jitter = 1E-8;
    private final RandomHelper randomHelper = RandomHelperDefault.create(this);

    private FactoryDefault() { }

    public static Factory create() {

        return new FactoryDefault();
    }

    @Override
    public double getJitter() {

        return jitter;
    }

    @Override
    public Factory setJitter(double jitter) {

        return this;
    }

    @Override
    public FPoint getFPoint() {

        return FPointDefault.create(this);
    }

    @Override
    public FVector getFVector() {

        return FVectorDefault.create(this);
    }

    @Override
    public FLine getFLine() {

        return FLineDefault.create(this);
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDefault.create(this);
    }

    @Override
    public FComplex getFComplex() {

        return FComplexDefault.create(this);
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDefault.create(this);
    }

    @Override
    public FPosition getFPosition(int x, int y, int z) {

        return FPositionDefault.create(this, x, y, z);
    }

    @Override
    public FPosition getFPosition(String structure) {

        return FPositionDefault.parse(this, structure);
    }

    @Override
    public FRotation getFRotation(FVector axis, double angle) {

        return FRotationDefault.create(this, axis, angle);
    }

    @Override
    public FRotation getFRotation(FPoint axis, double angle) {

        return FRotationDefault.create(this, axis, angle);
    }

    @Override
    public FRotation getFRotation(String structure) {

        return FRotationDefault.parse(this, structure);
    }

    @Override
    public AngleHelper getHelperAngle() {

        return AngleHelperDefault.INSTANCE;
    }

    @Override
    public SignalHelper getHelperSignal() {

        return SignalHelperDefault.INSTANCE;
    }

    @Override
    public RandomHelper getHelperRandom() {

        return randomHelper;
    }
}
