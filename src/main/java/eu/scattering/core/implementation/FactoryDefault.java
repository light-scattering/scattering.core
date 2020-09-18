package eu.scattering.core.implementation;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.geometry.extension.line.FLine;
import eu.scattering.core.design.main.mutable.geometry.extension.plane.FPlane;
import eu.scattering.core.design.main.mutable.number.complex.FComplex;
import eu.scattering.core.design.main.mutable.number.quaternion.FQuaternion;
import eu.scattering.core.design.main.fixed.position.FPosition;
import eu.scattering.core.design.main.fixed.rotation.FRotation;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.implementation.main.mutable.geometry.base.point.FPointDefault;
import eu.scattering.core.implementation.main.mutable.geometry.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.mutable.geometry.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.mutable.geometry.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.mutable.number.complex.FComplexDefault;
import eu.scattering.core.implementation.main.mutable.number.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.fixed.position.FPositionDefault;
import eu.scattering.core.implementation.main.fixed.rotation.FRotationDefault;
import eu.scattering.core.implementation.support.helper.AngleHelperDefault;
import eu.scattering.core.implementation.support.helper.RandomHelperDefault;
import eu.scattering.core.implementation.support.helper.SignalHelperDefault;

public final class FactoryDefault implements Factory {

    private final RandomHelper helperRandom = RandomHelperDefault.create(this);
    private final SignalHelper helperSignal = SignalHelperDefault.create();
    private final AngleHelper helperAngle = AngleHelperDefault.create();
    private double jitter = 1E-8;

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

        this.jitter = jitter;

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
