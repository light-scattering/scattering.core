package eu.scattering.core.design.transfer.box;

public interface FBoxDoubleFactory {

    default FBoxDouble getFBoxDouble() {

        return FBoxDouble.create();
    }
}
