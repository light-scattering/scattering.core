package eu.scattering.core.transfer.container.buffer;

public class BufferFactoryConcrete implements BufferFactory{

    private BufferFactoryConcrete() {}

    public static BufferFactoryConcrete create() {

        return new BufferFactoryConcrete();
    }
}
