package eu.scattering.core.implementation.main.algebra;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.Algebra;

import java.time.LocalTime;
import java.util.Optional;

import static eu.scattering.core.Config.factory;

public abstract class AlgebraPresetDevelopment<T> implements Algebra<T> {
    private final LocalTime creationTime = LocalTime.now();
    private final Statistics statistics = factory.getStatistics().setEnabled(false);
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

        Config.getDebugPrintStream().println(result);

        return self();
    }

    @Override
    public T devDescStatistics() {
        var postfix = statistics.isEnabled() ? statistics.toString() : "EVENT LOGGING DISABLED\n";
        var result = "Object statistics for " + id + " (created at " + creationTime + "):\n" + postfix;

        Config.getDebugPrintStream().println(result);

        return self();
    }

    @Override
    public T devDescClassStatistics() {
        var name = self().getClass().getSimpleName();
        var stats = getClassStatistics().toString();
        var result = "Class statistics for " + name + ":\n" + stats;

        Config.getDebugPrintStream().println(result);

        return self();
    }

    @Override
    public T devDescNumberOfInstances() {
        var name = self().getClass().getSimpleName();
        var instances = getNumberOfInstances();
        var result = "Number of instances for " + name + ": " +  instances + "\n";

        Config.getDebugPrintStream().println(result);

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
