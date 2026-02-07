package eu.scattering.core.design.storage.transfer;

import eu.scattering.core.design.storage.transfer.box.FBoxFactory;
import eu.scattering.core.design.storage.transfer.polynomial.FPolyFactory;
import eu.scattering.core.design.storage.transfer.position.FPosFactory;
import eu.scattering.core.design.transfer.TransferOldFactory;

public interface TransferFactory extends FBoxFactory, FPosFactory, FPolyFactory, TransferOldFactory {
}
