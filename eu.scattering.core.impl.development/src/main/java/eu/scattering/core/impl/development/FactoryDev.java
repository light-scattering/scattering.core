package eu.scattering.core.impl.development;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;
import eu.scattering.core.design.core.mutable.geometry.advanced.line.FLine;
import eu.scattering.core.design.core.mutable.geometry.advanced.plane.FPlane;
import eu.scattering.core.design.core.mutable.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.core.mutable.number.complex.FComplex;
import eu.scattering.core.design.core.mutable.number.quaternion.FQuaternion;
import eu.scattering.core.design.core.immutable.position.FPosition;
import eu.scattering.core.design.core.immutable.rotation.FRotation;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.impl.development.core.mutable.geometry.simple.point.FPointDev;
import eu.scattering.core.impl.development.core.mutable.geometry.simple.vector.FVectorDev;
import eu.scattering.core.impl.development.core.mutable.geometry.advanced.line.FLineDev;
import eu.scattering.core.impl.development.core.mutable.geometry.advanced.plane.FPlaneDev;
import eu.scattering.core.impl.development.core.mutable.number.complex.FComplexDev;
import eu.scattering.core.impl.development.core.mutable.number.quaternion.FQuaternionDev;

public final class FactoryDev implements Factory {

    private final Factory factory;

    private FactoryDev(Factory factory) {

        this.factory = factory;
    }

    public static Factory create(Factory factory) {

        return new FactoryDev(factory);
    }


    @Override
    public double getJitter() {

        return factory.getJitter();
    }

    @Override
    public Factory setJitter(double jitter) {

        factory.setJitter(jitter);

        return this;
    }

    @Override
    public FPoint getFPoint() {

        return FPointDev.create(factory.getFPoint());
    }

    @Override
    public FVector getFVector() {

        return FVectorDev.create(factory.getFVector());
    }

    @Override
    public FLine getFLine() {

        return FLineDev.create(factory.getFLine());
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDev.create(factory.getFPlane());
    }

    @Override
    public FSphere getFSphere() {
        return null;
    }

    @Override
    public FComplex getFComplex() {

        return FComplexDev.create(factory.getFComplex());
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDev.create(factory.getFQuaternion());
    }

    @Override
    public FPosition getFPosition(int x, int y, int z) {

        return factory.getFPosition(x, y, z);
    }

    @Override
    public FPosition getFPosition(String structure) {

        return factory.getFPosition(structure);
    }

    @Override
    public FRotation getFRotation(FVector axis, double angle) {

        return factory.getFRotation(axis, angle);
    }

    @Override
    public FRotation getFRotation(FPoint axis, double angle) {

        return factory.getFRotation(axis, angle);
    }

    @Override
    public FRotation getFRotation(String structure) {

        return factory.getFRotation(structure);
    }

    @Override
    public AngleHelper getHelperAngle() {

        return factory.getHelperAngle();
    }

    @Override
    public SignalHelper getHelperSignal() {

        return factory.getHelperSignal();
    }

    @Override
    public RandomHelper getHelperRandom() {

        return factory.getHelperRandom();
    }
}