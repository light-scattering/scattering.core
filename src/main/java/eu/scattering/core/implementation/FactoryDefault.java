package eu.scattering.core.implementation;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.container.position.FPosition;
import eu.scattering.core.design.main.container.rotor.FRotor;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.container.position.FPositionDefault;
import eu.scattering.core.implementation.main.container.rotor.FRotorDefault;

public final class FactoryDefault implements Factory {

    @Override
    public FPoint getFPoint() {

        return FPointDefault.create();
    }

    @Override
    public FVector getFVector() {

        return FVectorDefault.create();
    }

    @Override
    public FLine getFLine() {

        return FLineDefault.create();
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDefault.create();
    }

    @Override
    public FComplex getFComplex() {

        return FComplexDefault.create();
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDefault.create();
    }

    @Override
    public FPosition getFPosition(int x, int y, int z) {

        return FPositionDefault.create(x, y, z);
    }

    @Override
    public FPosition getFPosition(String structure) {

        return FPositionDefault.parse(structure);
    }

    @Override
    public FRotor getFRotor(FVector axis, double angle) {

        return FRotorDefault.create(axis, angle);
    }

    @Override
    public FRotor getFRotor(FPoint axis, double angle) {

        return FRotorDefault.create(axis, angle);
    }

    @Override
    public FRotor getFRotor(String structure) {

        return FRotorDefault.parse(structure);
    }

    @Override
    public AngleHelper getHelperAngle() {
        return null;
    }

    @Override
    public SignalHelper getHelperSignal() {
        return null;
    }
}
