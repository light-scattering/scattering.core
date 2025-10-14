package eu.scattering.core.design.storage.mutable.box;

public class BoxFactoryConcrete implements BoxFactory {

    private BoxFactoryConcrete() {}

    public static BoxFactory create() {

        return new BoxFactoryConcrete();
    }
}
