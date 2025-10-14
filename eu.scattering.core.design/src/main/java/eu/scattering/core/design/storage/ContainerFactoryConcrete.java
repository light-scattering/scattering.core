package eu.scattering.core.design.storage;

public class ContainerFactoryConcrete implements ContainerFactory {

    private ContainerFactoryConcrete() {}

    public static ContainerFactory create() {

        return new ContainerFactoryConcrete();
    }
}
