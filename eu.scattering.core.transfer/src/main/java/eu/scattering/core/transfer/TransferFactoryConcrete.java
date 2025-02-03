package eu.scattering.core.transfer;

import eu.scattering.core.transfer.helper.transfer.FPositionHelper;
import eu.scattering.core.transfer.helper.transfer.FPositionHelperFactory;
import eu.scattering.core.transfer.helper.transfer.FPositionHelperFactoryConcrete;

public class TransferFactoryConcrete implements TransferFactory {
    private final FPositionHelperFactory positionHelperFactory;

    public TransferFactoryConcrete() {

        this.positionHelperFactory = FPositionHelperFactoryConcrete.create();
    }

    public static TransferFactory create() {

        return new TransferFactoryConcrete();
    }

    @Override
    public FPositionHelper getFPositionHelper() {

        return this.positionHelperFactory.getFPositionHelper();
    }
}
