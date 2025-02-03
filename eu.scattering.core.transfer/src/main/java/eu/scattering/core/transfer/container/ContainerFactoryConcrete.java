package eu.scattering.core.transfer.container;

public class ContainerFactoryConcrete implements ContainerFactory {

    private ContainerFactoryConcrete() {}

    public static ContainerFactory create() {

        return new ContainerFactoryConcrete();
    }
}
