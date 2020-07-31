package eu.scattering.core;

import eu.scattering.core.debug.IRecord;
import eu.scattering.core.debug.impl.Record;
import lombok.Getter;

public class Main {

    @Getter
    public static final IRecord devStats;

    static {
        devStats = new Record();
    }
}
