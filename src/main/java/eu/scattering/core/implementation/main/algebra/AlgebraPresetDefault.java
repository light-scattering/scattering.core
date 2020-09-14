package eu.scattering.core.implementation.main.algebra;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.Algebra;

import java.time.LocalTime;
import java.util.Optional;

public abstract class AlgebraPresetDefault<T> implements Algebra<T> {

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    @Override
    public boolean devIsStatisticsEnabled() {

        Config.getDebugPrintStream().println("Not implemented");

        return false;
    }

    @Override
    public T devSetStatisticsEnabled(boolean enabled) {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

    @Override
    public T devDesc() {

        Config.getDebugPrintStream().println(LocalTime.now().toString()
                + " - " + self().getClass().getSimpleName()
                + " - " + toString());

        return self();
    }

    @Override
    public T devDescStatistics() {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

    @Override
    public T devDescClassStatistics() {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

    @Override
    public T devDescNumberOfInstances() {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    };

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.empty();
    }

    @Override
    public T devResetNumberOfInstances() {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

    @Override
    public Optional<Statistics> devGetStatistics() {

        return Optional.empty();
    }

    @Override
    public Optional<Statistics> devGetClassStatistics() {

        return Optional.empty();
    }

    @Override
    public String devGetLabel() {

        Config.getDebugPrintStream().println("Not implemented");

        return "";
    }

    @Override
    public T devSetLabel(String label) {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

}
