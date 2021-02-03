package eu.scattering.core.impl.production.main.mutable;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.mutable.Mutable;

import java.time.LocalTime;
import java.util.Optional;

public abstract class MutablePresetProd<T> implements Mutable<T> {

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    @Override
    public boolean devIsStatisticsEnabled() {

        System.out.println("Not implemented");

        return false;
    }

    @Override
    public T devSetStatisticsEnabled(boolean enabled) {

        System.out.println("Not implemented");

        return self();
    }

    @Override
    public T devDesc() {

        System.out.println(LocalTime.now().toString()
                + " - " + self().getClass().getSimpleName()
                + " - " + toString());

        return self();
    }

    @Override
    public T devDescStatistics() {

        System.out.println("Not implemented");

        return self();
    }

    @Override
    public T devDescClassStatistics() {

        System.out.println("Not implemented");

        return self();
    }

    @Override
    public T devDescNumberOfInstances() {

        System.out.println("Not implemented");

        return self();
    }

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.empty();
    }

    @Override
    public T devResetNumberOfInstances() {

        System.out.println("Not implemented");

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

        System.out.println("Not implemented");

        return "";
    }

    @Override
    public T devSetLabel(String label) {

        System.out.println("Not implemented");

        return self();
    }

}
