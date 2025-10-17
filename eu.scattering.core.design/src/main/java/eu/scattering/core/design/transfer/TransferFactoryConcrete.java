package eu.scattering.core.design.transfer;

public class TransferFactoryConcrete implements TransferFactory {
    private static TransferFactory factory;

    private TransferFactoryConcrete() {}

    public static TransferFactory create() {

        if (factory == null) {
            factory = new TransferFactoryConcrete();
        }

        return factory;
    }
}
