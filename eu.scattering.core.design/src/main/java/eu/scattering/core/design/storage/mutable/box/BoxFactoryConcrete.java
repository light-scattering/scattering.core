package eu.scattering.core.design.storage.mutable.box;

public class BoxFactoryConcrete implements BoxFactory {

    private BoxFactoryConcrete() {}

    public static BoxFactoryConcrete create() {

        return new BoxFactoryConcrete();
    }
}
