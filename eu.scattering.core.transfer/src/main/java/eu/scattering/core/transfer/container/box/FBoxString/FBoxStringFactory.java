package eu.scattering.core.transfer.container.box.FBoxString;

public interface FBoxStringFactory {

    default FBoxString getFBoxString() {

        return FBoxString.create();
    }
}
