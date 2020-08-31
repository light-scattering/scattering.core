package eu.scattering.core.implementation.injection;

import eu.scattering.core.design.injection.MainFactory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.valjo.FDipole;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.valjo.FDipoleDefault;

public final class MainFactoryDefault implements MainFactory {

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
    public FDipole getFDipole(int x, int y, int z) {

        return FDipoleDefault.create(x, y, z);
    }

    @Override
    public FDipole getFDipole(String position) {

        return FDipoleDefault.parse(position);
    }
}
