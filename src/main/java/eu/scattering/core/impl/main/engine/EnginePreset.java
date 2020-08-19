package eu.scattering.core.impl.main.engine;

import eu.scattering.core.logic.dev.stats.Stats;
import eu.scattering.core.logic.main.engine.Engine;

import java.time.LocalTime;
import java.util.Optional;

import static eu.scattering.core.Config.debugPrintStream;

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

        debugPrintStream.println(LocalTime.now().toString()
                + " - " + self().getClass().getSimpleName()
                + " - " + toString());

        return self();
    }

    @Override
    public T devDescribeStats() {

        debugPrintStream.println("Not implemented");

        return self();
    }

    @Override
    public T devDescribeClassStats() {

        debugPrintStream.println("Not implemented");

        return self();
    }

    @Override
    public Optional<Stats> devGetStats() {

        return Optional.empty();
    }

    @Override
    public Optional<Stats> devGetClassStats() {

        return Optional.empty();
    }

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.empty();
    }

    @Override
    public T devResetNumberOfInstances() {

        debugPrintStream.println("Not implemented");

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
