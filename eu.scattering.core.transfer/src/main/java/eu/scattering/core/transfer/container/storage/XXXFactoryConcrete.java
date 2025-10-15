package eu.scattering.core.transfer.container.storage;

public class XXXFactoryConcrete implements XXXFactory {

    private XXXFactoryConcrete() {}

    public static XXXFactory create() {

        return new XXXFactoryConcrete();
    }
}
