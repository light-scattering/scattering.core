package eu.scattering.core.transfer.container.engine;

public class EngineFactoryConcrete implements EngineFactory {

    private EngineFactoryConcrete() {}

    public static EngineFactoryConcrete create() {

        return new EngineFactoryConcrete();
    }
}
