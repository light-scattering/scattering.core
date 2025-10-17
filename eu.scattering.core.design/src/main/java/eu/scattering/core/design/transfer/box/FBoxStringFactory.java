package eu.scattering.core.design.transfer.box;

public interface FBoxStringFactory {

    default FBoxString getFBoxString() {

        return FBoxString.create();
    }
}
