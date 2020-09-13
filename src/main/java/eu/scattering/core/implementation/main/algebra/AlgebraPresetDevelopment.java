package eu.scattering.core.implementation.main.algebra;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;

import java.time.LocalTime;
import java.util.Optional;

import static eu.scattering.core.Config.factory;

public abstract class AlgebraPresetDevelopment<T> implements Algebra<T> {

    protected FPoint core;

    private final Statistics instanceStatistics = factory.getStatistics().setEnabled(false);
    private final LocalTime instanceCreationTime = LocalTime.now();
    private final String instanceId = self().getClass().getSimpleName() + ":" + getNumberOfInstances();
    private final String className = self().getClass().getSimpleName();
    private String instanceLabel = "";

    protected abstract long getNumberOfInstances();
    protected abstract void setNumberOfInstances(long numberOfInstances);
    protected abstract Statistics getClassStatistics();

    @Override
    public abstract Object clone();

    @Override
    public abstract boolean equals(Object object);

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return core.toString();
    }

    @Override
    public int hashCode() {

        return core.hashCode();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean devIsStatisticsEnabled() {

        return instanceStatistics.isEnabled();
    }

    @Override
    public T devSetStatisticsEnabled(boolean enabled) {

        instanceStatistics.setEnabled(enabled);

        return self();
    }

    @Override
    public T devDesc() {

        String data = LocalTime.now().toString() + " - " + instanceId + " (created at " + instanceCreationTime + ") - " +
                exportToJSON() + "\n";

        Config.getDebugPrintStream().println(data);

        return self();
    }

    @Override
    public T devDescStatistics() {


        String postfix = instanceStatistics.isEnabled() ? instanceStatistics.toString() : "EVENT LOGGING DISABLED\n";
        String data = "Object statistics for " + instanceId + " (created at " + instanceCreationTime + "):\n" + postfix;

        Config.getDebugPrintStream().println(data);

        return self();
    }

    @Override
    public T devDescClassStatistics() {

        String data = "Class statistics for " + className + ":\n" +
                getClassStatistics().toString();

        Config.getDebugPrintStream().println(data);

        return self();
    }

    @Override
    public T devDescNumberOfInstances() {

        String data = "Number of instances for " + className + ": " + getNumberOfInstances() + "\n";

        Config.getDebugPrintStream().println(data);

        return self();
    };

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

        return Optional.of(instanceStatistics);
    }

    @Override
    public Optional<Statistics> devGetClassStatistics() {

        return Optional.of(getClassStatistics());
    }

    @Override
    public String devGetLabel() {

        return instanceLabel;
    }

    @Override
    public T devSetLabel(String label) {

        this.instanceLabel = label;

        return self();
    }

    // -------------------------------------------------------------------------------------------------

    protected void updateStats(String methodName, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        getClassStatistics().recordEvent(methodName, time);
        instanceStatistics.recordEvent(methodName, time);
    }

}
