package eu.scattering.core.transfer.container.storage;

public class StorageFactoryConcrete implements StorageFactory {

    private StorageFactoryConcrete() {}

    public static StorageFactory create() {

        return new StorageFactoryConcrete();
    }
}
