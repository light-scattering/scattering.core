package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.FactoryTransfers;
import eu.scattering.core.design.helpers.transfer.FPositionHelper;
import eu.scattering.core.design.transfers.position.*;

public class FPositionHelperProd implements FPositionHelper {

    private final FactoryTransfers factory;

    private FPositionHelperProd() {

        this.factory = FactoryTransfers.create();
    }

    public static FPositionHelper create() {

        return new FPositionHelperProd();
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
    public FPairPos4D getFPairPos4DWithRange(double range) {
        FPos4D min = factory.getFPos4D(-range, -range, -range, -range);
        FPos4D max = factory.getFPos4D(range, range, range, range);

        return factory.getFPairPos4D(min, max);
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

    @Override
    public FPairPos4D getFPairPos4DWithRange(double rangeX, double rangeY, double rangeZ, double rangeW) {
        FPos4D min = factory.getFPos4D(-rangeX, -rangeY, -rangeZ, -rangeW);
        FPos4D max = factory.getFPos4D(rangeX, rangeY, rangeZ, rangeW);

        return factory.getFPairPos4D(min, max);
    }
}
