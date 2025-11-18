package eu.scattering.core.design.component.aggregate.monitor.construct;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.construct.FPlot;

import java.util.function.BiConsumer;

public interface FMonitorConstruct extends BiConsumer<FAggregate, Shape> {

    void setSkip(int skip);

    FPlot getResults();
}
