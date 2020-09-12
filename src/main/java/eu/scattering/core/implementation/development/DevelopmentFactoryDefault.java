package eu.scattering.core.implementation.development;

import eu.scattering.core.design.development.DevelopmentFactory;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.implementation.development.statistics.StatisticsDefault;

public final class DevelopmentFactoryDefault implements DevelopmentFactory {

    @Override
    public Statistics getStatistics() {

        return StatisticsDefault.create();
    }

}
