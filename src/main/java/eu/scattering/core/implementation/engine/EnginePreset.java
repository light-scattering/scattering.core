package eu.scattering.core.implementation.engine;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.engine.Engine;

import java.time.LocalTime;
import java.util.Optional;

public abstract class EnginePreset<T> implements Engine<T> {

    private String meta = "";

    @Override
    public abstract Object clone();

    @Override
    public abstract boolean equals(Object object);

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    @Override
    public T devDescribe() {

        Config.getDebugPrintStream().println(LocalTime.now().toString()
                + " - " + self().getClass().getSimpleName()
                + " - " + toString());

        return self();
    }

    @Override
    public T devDescribeStats() {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

    @Override
    public T devDescribeClassStats() {

        Config.getDebugPrintStream().println("Not implemented");

        return self();
    }

    @Override
    public Optional<Statistics> devGetStats() {

        return Optional.empty();
    }

    @Override
    public Optional<Statistics> devGetClassStats() {

        return Optional.empty();
    }

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
    public String devGetMeta() {

        return meta;
    }

    @Override
    public T devSetMeta(String meta) {

        this.meta = meta;

        return self();
    }

}
