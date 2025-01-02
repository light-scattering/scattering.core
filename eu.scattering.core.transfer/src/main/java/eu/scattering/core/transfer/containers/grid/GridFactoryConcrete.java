package eu.scattering.core.transfer.containers.grid;

public class GridFactoryConcrete implements GridFactory {

    private GridFactoryConcrete() {}

    public static GridFactory create() {

        return new GridFactoryConcrete();
    }
}
