package eu.scattering.core.design.engine.export;

import eu.scattering.core.design.component.ComponentEngineExport;
import eu.scattering.core.design.engine.Engine;
import eu.scattering.core.design.statistics.StatisticsEngineExport;

public interface FExportEngine extends ComponentEngineExport, Engine<FExportEngine> {

    StatisticsEngineExport getPlotContext();
}
