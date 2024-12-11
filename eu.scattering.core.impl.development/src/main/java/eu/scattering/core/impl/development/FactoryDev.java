package eu.scattering.core.impl.development;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.core.algebra.geometry.construct.line.FLine;
import eu.scattering.core.design.core.algebra.geometry.construct.plane.FPlane;
import eu.scattering.core.design.core.algebra.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.core.algebra.number.complex.FComplex;
import eu.scattering.core.design.core.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.core.data.position.FPos3DI;
import eu.scattering.core.design.core.engine.rotation.FRotation;
import eu.scattering.core.design.helper.angle.FAngleHelper;
import eu.scattering.core.design.helper.random.FRandomHelper;
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
    public FPos3DI getFPos3DI(int d0, int d1, int d2) {

        return factory.getFPos3DI(d0, d1, d2);
    }

    @Override
    public FPos3DI getFPos3DI(String text) {

        return factory.getFPos3DI(text);
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
    public FAngleHelper getFAngleHelper() {

        return factory.getFAngleHelper();
    }

    @Override
    public FRandomHelper getFRandomHelper() {

        return factory.getFRandomHelper();
    }
}