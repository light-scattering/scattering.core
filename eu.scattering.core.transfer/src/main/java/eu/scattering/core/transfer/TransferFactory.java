package eu.scattering.core.transfer;

import eu.scattering.core.transfer.statistics.StatisticsFactory;
import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.helper.HelperFactory;

public interface TransferFactory extends ContainerFactory, HelperFactory, StatisticsFactory {
}
