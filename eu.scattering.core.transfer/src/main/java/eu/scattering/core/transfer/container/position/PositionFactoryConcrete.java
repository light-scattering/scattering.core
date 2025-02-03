package eu.scattering.core.transfer.container.position;

public class PositionFactoryConcrete implements PositionFactory {

    private PositionFactoryConcrete() {}

    public static PositionFactory create() {

        return new PositionFactoryConcrete();
    }
}
