package eu.scattering.core.implementation;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.box.position.FPosition;
import eu.scattering.core.design.main.box.rotation.FRotation;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.implementation.development.statistics.StatisticsDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDevelopment;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDevelopment;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDevelopment;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDevelopment;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDevelopment;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDevelopment;
import eu.scattering.core.implementation.main.box.position.FPositionDefault;
import eu.scattering.core.implementation.main.box.rotation.FRotationDefault;
import eu.scattering.core.implementation.support.helper.AngleHelperDefault;
import eu.scattering.core.implementation.support.helper.SignalHelperDefault;

public final class FactoryDevelopment implements Factory {

    @Override
    public FPoint getFPoint() {

        return FPointDevelopment.create(FPointDefault.create());
    }

    @Override
    public FVector getFVector() {

        return FVectorDevelopment.create(FVectorDefault.create());
    }

    @Override
    public FLine getFLine() {

        return FLineDevelopment.create(FLineDefault.create());
    }

    @Override
    public FPlane getFPlane() {

        return FPlaneDevelopment.create(FPlaneDefault.create());
    }

    @Override
    public FComplex getFComplex() {

        return FComplexDevelopment.create(FComplexDefault.create());
    }

    @Override
    public FQuaternion getFQuaternion() {

        return FQuaternionDevelopment.create(FQuaternionDefault.create());
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
    public FRotation getFRotation(FVector axis, double angle) {

        return FRotationDefault.create(axis, angle);
    }

    @Override
    public FRotation getFRotation(FPoint axis, double angle) {

        return FRotationDefault.create(axis, angle);
    }

    @Override
    public FRotation getFRotation(String structure) {

        return FRotationDefault.parse(structure);
    }

    @Override
    public Statistics getStatistics() {

        return StatisticsDefault.create();
    }

    @Override
    public AngleHelper getHelperAngle() {

        return AngleHelperDefault.INSTANCE;
    }

    @Override
    public SignalHelper getHelperSignal() {

        return SignalHelperDefault.INSTANCE;
    }
}