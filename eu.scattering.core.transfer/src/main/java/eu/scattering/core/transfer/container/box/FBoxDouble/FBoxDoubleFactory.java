package eu.scattering.core.transfer.container.box.FBoxDouble;

public interface FBoxDoubleFactory {

    default FBoxDouble getFBoxDouble() {

        return FBoxDouble.create();
    }
}
