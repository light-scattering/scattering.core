package eu.scattering.core.transfer.helpers;

import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.helpers.transfer.FPositionHelper;
import eu.scattering.core.transfer.helpers.transfer.FPositionHelperFactory;
import eu.scattering.core.transfer.helpers.transfer.FPositionHelperFactoryConcrete;

public class HelperFactoryConcrete implements HelperFactory {
    private final FPositionHelperFactory positionHelperFactory;

    private HelperFactoryConcrete() {

        this.positionHelperFactory = FPositionHelperFactoryConcrete.create();
    }

    public static HelperFactory create() {

        return new TransferFactoryConcrete();
    }

    @Override
    public FPositionHelper getFPositionHelper() {

        return this.positionHelperFactory.getFPositionHelper();
    }
}
