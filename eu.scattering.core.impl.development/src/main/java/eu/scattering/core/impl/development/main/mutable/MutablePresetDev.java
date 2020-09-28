package eu.scattering.core.impl.development.main.mutable;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.mutable.Mutable;
import eu.scattering.core.impl.production.development.statistics.StatisticsProd;

import java.time.LocalTime;
import java.util.Optional;

public abstract class MutablePresetDev<T> implements Mutable<T> {
    private final LocalTime creationTime = LocalTime.now();
    private final Statistics statistics = StatisticsProd.create().setEnabled(false);
    private final String id = self().getClass().getSimpleName() + ":" + getNumberOfInstances();
    private String label = "";
    private T core;

    protected T getCore() {

        return core;
    }

    protected void setCore(T core) {

        this.core = core;
    }

    protected abstract Statistics getClassStatistics();
    protected abstract long getNumberOfInstances();
    protected abstract void setNumberOfInstances(long numberOfInstances);

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return getCore().toString();
    }

    @Override
    public int hashCode() {

        return getCore().hashCode();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean devIsStatisticsEnabled() {

        return statistics.isEnabled();
    }

    @Override
    public T devSetStatisticsEnabled(boolean enabled) {

        statistics.setEnabled(enabled);

        return self();
    }

    @Override
    public T devDesc() {
        var json = exportToJSON();
        var date = LocalTime.now().toString();
        var result = date + " - " + id + " (created at " + creationTime + ") - " + json + "\n";

        System.out.println(result);

        return self();
    }

    @Override
    public T devDescStatistics() {
        var postfix = statistics.isEnabled() ? statistics.toString() : "EVENT LOGGING DISABLED\n";
        var result = "Object statistics for " + id + " (created at " + creationTime + "):\n" + postfix;

        System.out.println(result);

        return self();
    }

    @Override
    public T devDescClassStatistics() {
        var name = self().getClass().getSimpleName();
        var stats = getClassStatistics().toString();
        var result = "Class statistics for " + name + ":\n" + stats;

        System.out.println(result);

        return self();
    }

    @Override
    public T devDescNumberOfInstances() {
        var name = self().getClass().getSimpleName();
        var instances = getNumberOfInstances();
        var result = "Number of instances for " + name + ": " +  instances + "\n";

        System.out.println(result);

        return self();
    }

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.of(getNumberOfInstances());
    }

    @Override
    public T devResetNumberOfInstances() {

        setNumberOfInstances(0);

        return self();
    }

    @Override
    public Optional<Statistics> devGetStatistics() {

        return Optional.of(statistics);
    }

    @Override
    public Optional<Statistics> devGetClassStatistics() {

        return Optional.of(getClassStatistics());
    }

    @Override
    public String devGetLabel() {

        return label;
    }

    @Override
    public T devSetLabel(String label) {

        this.label = label;

        return self();
    }

    // -------------------------------------------------------------------------------------------------

    protected void updateStats(String methodName, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        getClassStatistics().recordEvent(methodName, time);
        statistics.recordEvent(methodName, time);
    }

}
