package eu.scattering.core.impl.production;

import eu.scattering.core.test.design.Factory;
import eu.scattering.core.test.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.test.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.test.design.main.mutable.geometry.extension.line.FLine;
import eu.scattering.core.test.design.main.mutable.geometry.extension.plane.FPlane;
import eu.scattering.core.test.design.main.mutable.number.complex.FComplex;
import eu.scattering.core.test.design.main.mutable.number.quaternion.FQuaternion;
import eu.scattering.core.test.design.main.fixed.position.FPosition;
import eu.scattering.core.test.design.main.fixed.rotation.FRotation;
import eu.scattering.core.test.design.support.helper.AngleHelper;
import eu.scattering.core.test.design.support.helper.RandomHelper;
import eu.scattering.core.test.design.support.helper.SignalHelper;
import eu.scattering.core.impl.production.main.mutable.geometry.base.point.FPointDevelopment;
import eu.scattering.core.impl.production.main.mutable.geometry.base.vector.FVectorDevelopment;
import eu.scattering.core.impl.production.main.mutable.geometry.extension.line.FLineDevelopment;
import eu.scattering.core.impl.production.main.mutable.geometry.extension.plane.FPlaneDevelopment;
import eu.scattering.core.impl.production.main.mutable.number.complex.FComplexDevelopment;
import eu.scattering.core.impl.production.main.mutable.number.quaternion.FQuaternionDevelopment;

public final class FactoryDevelopment implements Factory {

    private final Factory factory;

    private FactoryDevelopment(Factory factory) {

        this.factory = factory;
    }

    public static Factory create(Factory factory) {

        return new FactoryDevelopment(factory);
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

        return FPointDevelopment.create(factory.getFPoint());
    }

    @Override
    public FVector getFVector() {

        return FVectorDevelopment.create(factory.getFVector());
    }

    @Override
    public FLine getFLine() {

        return FLineDevelopment.create(factory.getFLine());
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDevelopment.create(factory.getFPlane());
    }

    @Override
    public FComplex getFComplex() {

        return FComplexDevelopment.create(factory.getFComplex());
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDevelopment.create(factory.getFQuaternion());
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