package eu.scattering.core.impl.storage.transfer;

import eu.scattering.core.design.storage.transfer.TransferHelper;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;

public class TransferHelperDef implements TransferHelper {
    private final TransferFactory factoryExt;

    private TransferHelperDef(TransferFactory factoryExt) {

        this.factoryExt = factoryExt;
    }

    public static TransferHelper create(TransferFactory factoryExt) {

        return new TransferHelperDef(factoryExt);
    }

    @Override
    public FPairPos2D getFPairPos2DWithRange(double range) {
        FPos2D min = factoryExt.getFPos2D(-range, -range);
        FPos2D max = factoryExt.getFPos2D(range, range);

        return factoryExt.getFPairPos2D(min, max);
    }

    @Override
    public FPairPos3D getFPairPos3DWithRange(double range) {
        FPos3D min = factoryExt.getFPos3D(-range, -range, -range);
        FPos3D max = factoryExt.getFPos3D(range, range, range);

        return factoryExt.getFPairPos3D(min, max);
    }

    @Override
    public FPairPos4D getFPairPos4DWithRange(double range) {
        FPos4D min = factoryExt.getFPos4D(-range, -range, -range, -range);
        FPos4D max = factoryExt.getFPos4D(range, range, range, range);

        return factoryExt.getFPairPos4D(min, max);
    }

    @Override
    public FPairPos2D getFPairPos2DWithRange(double rangeX, double rangeY) {
        FPos2D min = factoryExt.getFPos2D(-rangeX, -rangeY);
        FPos2D max = factoryExt.getFPos2D(rangeX, rangeY);

        return factoryExt.getFPairPos2D(min, max);
    }

    @Override
    public FPairPos3D getFPairPos3DWithRange(double rangeX, double rangeY, double rangeZ) {
        FPos3D min = factoryExt.getFPos3D(-rangeX, -rangeY, -rangeZ);
        FPos3D max = factoryExt.getFPos3D(rangeX, rangeY, rangeZ);

        return factoryExt.getFPairPos3D(min, max);
    }

    @Override
    public FPairPos4D getFPairPos4DWithRange(double rangeX, double rangeY, double rangeZ, double rangeW) {
        FPos4D min = factoryExt.getFPos4D(-rangeX, -rangeY, -rangeZ, -rangeW);
        FPos4D max = factoryExt.getFPos4D(rangeX, rangeY, rangeZ, rangeW);

        return factoryExt.getFPairPos4D(min, max);
    }
}
