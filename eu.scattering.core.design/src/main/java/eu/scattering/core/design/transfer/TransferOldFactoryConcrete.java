package eu.scattering.core.design.transfer;

public class TransferOldFactoryConcrete implements TransferOldFactory {
    private static TransferOldFactory factory;

    private TransferOldFactoryConcrete() {}

    public static TransferOldFactory create() {

        if (factory == null) {
            factory = new TransferOldFactoryConcrete();
        }

        return factory;
    }
}
