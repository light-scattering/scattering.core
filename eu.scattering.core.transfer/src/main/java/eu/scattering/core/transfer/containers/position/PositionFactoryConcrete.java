package eu.scattering.core.transfer.containers.position;

public class PositionFactoryConcrete implements PositionFactory {

    private PositionFactoryConcrete() {}

    public static PositionFactory create() {

        return new PositionFactoryConcrete();
    }
}
