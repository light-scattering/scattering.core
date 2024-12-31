package eu.scattering.core.transfer.containers.engine;

public class EngineFactoryConcrete implements EngineFactory {

    private EngineFactoryConcrete() {}

    public static EngineFactoryConcrete create() {

        return new EngineFactoryConcrete();
    }
}
