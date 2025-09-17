package eu.scattering.core.transfer.container.box;

public class BoxFactoryConcrete implements BoxFactory {

    private BoxFactoryConcrete() {}

    public static BoxFactory create() {

        return new BoxFactoryConcrete();
    }
}
