package eu.scattering.core.design.storage.transfer;

import eu.scattering.core.design.storage.transfer.box.FBoxFactory;
import eu.scattering.core.design.storage.transfer.matrix.FMatrixFactory;
import eu.scattering.core.design.storage.transfer.polynomial.FPolyFactory;
import eu.scattering.core.design.storage.transfer.position.FPosFactory;

public interface TransferFactory extends FBoxFactory, FPosFactory, FPolyFactory, FMatrixFactory {
}
