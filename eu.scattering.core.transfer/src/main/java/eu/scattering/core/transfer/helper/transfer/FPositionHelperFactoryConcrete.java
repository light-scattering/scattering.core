package eu.scattering.core.transfer.helper.transfer;

public class FPositionHelperFactoryConcrete implements FPositionHelperFactory {
    private final FPositionHelper positionHelper;

    private FPositionHelperFactoryConcrete() {

        this.positionHelper = FPositionHelperConcrete.create();
    }

    public static FPositionHelperFactory create() {

        return new FPositionHelperFactoryConcrete();
    }

//    @Override
//    public FPositionHelper getFPositionHelper() {
//
//        return this.positionHelper;
//    }
}
