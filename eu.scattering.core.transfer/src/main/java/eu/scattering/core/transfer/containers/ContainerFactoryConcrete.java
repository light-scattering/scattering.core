package eu.scattering.core.transfer.containers;

public class ContainerFactoryConcrete implements ContainerFactory {

    private ContainerFactoryConcrete() {}

    public static ContainerFactory create() {

        return new ContainerFactoryConcrete();
    }
}
