package eu.scattering.core.design.component.aggregate.model.pc.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.construct.FPlot2D;

import java.util.function.BiConsumer;

public interface FMonitorRoGMono extends BiConsumer<FAggregate, Shape> {

    FPlot2D getResults();
}
