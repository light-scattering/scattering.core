package eu.scattering.core.transfer.helper.transfer;

public interface FPositionHelperFactory {

    default FPositionHelper getFPositionHelper() {

        return FPositionHelperConcrete.create();
    }
}
