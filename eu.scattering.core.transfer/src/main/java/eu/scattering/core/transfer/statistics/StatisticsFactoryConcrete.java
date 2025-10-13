package eu.scattering.core.transfer.statistics;

public class StatisticsFactoryConcrete implements StatisticsFactory {

    private StatisticsFactoryConcrete() {}

    public static StatisticsFactory create() {

        return new StatisticsFactoryConcrete();
    }
}
