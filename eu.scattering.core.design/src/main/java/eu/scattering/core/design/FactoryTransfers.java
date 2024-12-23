package eu.scattering.core.design;

import eu.scattering.core.design.transfers.TransfersFactory;

public class FactoryTransfers implements TransfersFactory {

    private FactoryTransfers() {}

    public static FactoryTransfers create() {

        return new FactoryTransfers();
    }

}
