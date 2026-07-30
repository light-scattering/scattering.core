package eu.scattering.core.design.storage.transfer;

import eu.scattering.core.design.storage.transfer.box.FBoxFactory;
import eu.scattering.core.design.storage.transfer.matrix.FMatrixFactory;
import eu.scattering.core.design.storage.transfer.polynomial.FPolyFactory;
import eu.scattering.core.design.storage.transfer.position.PositionFactory;

public interface TransferFactory extends FBoxFactory, PositionFactory, FPolyFactory, FMatrixFactory {

    TransferHelper getTransferHelper();
}
