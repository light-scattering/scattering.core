package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.mutable.box.BoxFactory;

public class StorageFactoryConcrete implements BoxFactory {

    private StorageFactoryConcrete() {}

    public static StorageFactoryConcrete create() {

        return new StorageFactoryConcrete();
    }
}
