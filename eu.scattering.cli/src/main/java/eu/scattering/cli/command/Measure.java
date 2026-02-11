package eu.scattering.cli.command;

import eu.scattering.cli.type.TYPE_METRIC;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.FractalDimension;

import java.util.ArrayList;
import java.util.List;

public class Measure {

    public static String measure(FAggregate aggregate, List<TYPE_METRIC> metrics) {
        List<String> results = new ArrayList<>(metrics.size());

        for (TYPE_METRIC metric : metrics) {

            results.add(switch (metric) {
                case NP -> getNp(aggregate);
                case RP -> getRp(aggregate);
                case RP_AVG -> getRpAvg(aggregate);
                case RP_STD -> getRpStd(aggregate);
                case DF_B -> getDfBox(aggregate);
                case DF_C -> getDfCor(aggregate);
            });
        }

        return String.join(" ", results);
    }

    private static String getNp(FAggregate aggregate) {

        return String.valueOf(aggregate.size());
    }

    private static String getRp(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius());
    }

    private static String getRpAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius().mean());
    }

    private static String getRpStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius().std(true));
    }

    private static String getDfBox(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.BOX));
    }

    private static String getDfCor(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.CORRELATION));
    }
}
