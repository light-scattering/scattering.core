package eu.scattering.core.design.storage.mutable.box.item;

public interface FBoxStringFactory {

    default FBoxString getFBoxString() {

        return FBoxString.create();
    }
}
