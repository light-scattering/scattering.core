package eu.scattering.core.impl.helper;

import eu.scattering.core.design.helper.transfer.FPositionHelper;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.*;

public class FPositionHelperDef implements FPositionHelper {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static FPositionHelperDef self;

    private FPositionHelperDef() { }

    public static FPositionHelper get() {

        if (FPositionHelperDef.self == null) {
            FPositionHelperDef.self = new FPositionHelperDef();
        }

        return FPositionHelperDef.self;
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
