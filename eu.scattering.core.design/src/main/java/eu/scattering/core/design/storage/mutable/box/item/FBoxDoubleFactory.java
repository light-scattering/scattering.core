package eu.scattering.core.design.storage.mutable.box.item;

public interface FBoxDoubleFactory {

    default FBoxDouble getFBoxDouble() {

        return FBoxDouble.create();
    }
}
