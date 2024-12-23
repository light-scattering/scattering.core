package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.helpers.transfer.FPositionHelper;
import eu.scattering.core.design.transfers.position.FPairPos2D;
import eu.scattering.core.design.transfers.position.FPairPos3D;
import eu.scattering.core.design.transfers.position.FPos2D;
import eu.scattering.core.design.transfers.position.FPos3D;

public class FPositionHelperProd implements FPositionHelper {

    private final Factory factory;

    private FPositionHelperProd(Factory factory) {

        this.factory = factory;
    }

    public static FPositionHelper create(Factory factory) {

        return new FPositionHelperProd(factory);
    }

    @Override
    public FPairPos2D getFPairPos2DWithRange(double range) {
        FPos2D min = factory.getFPos2D(-range, -range);
        FPos2D max = factory.getFPos2D(range, range);

        return factory.getFPairPos2D(min, max);
    }

    @Override
    public FPairPos3D getFPairPos3DWithRange(double range) {
        FPos3D min = factory.getFPos3D(-range, -range, -range);
        FPos3D max = factory.getFPos3D(range, range, range);

        return factory.getFPairPos3D(min, max);
    }

    @Override
    public FPairPos2D getFPairPos2DWithRange(double rangeX, double rangeY) {
        FPos2D min = factory.getFPos2D(-rangeX, -rangeY);
        FPos2D max = factory.getFPos2D(rangeX, rangeY);

        return factory.getFPairPos2D(min, max);
    }

    @Override
    public FPairPos3D getFPairPos3DWithRange(double rangeX, double rangeY, double rangeZ) {
        FPos3D min = factory.getFPos3D(-rangeX, -rangeY, -rangeZ);
        FPos3D max = factory.getFPos3D(rangeX, rangeY, rangeZ);

        return factory.getFPairPos3D(min, max);
    }
}
