package eu.scattering.core.transfer.statistics.FPlot2D;

import eu.scattering.core.transfer.statistics.FPlot2D.concrete.FPlot2DDef;
import eu.scattering.core.transfer.statistics.FPlot2D.concrete.FPlot2DExporterDef;
import org.json.JSONObject;

public interface FPlot2DFactory {

    default FPlot2D getFPlot2D() {

        return FPlot2DDef.create();
    }

    default FPlot2D getFPlot2D(JSONObject json) {

        return FPlot2DDef.create(json);
    }

    //--------------------------------------------------

    default FPlot2DExporter getFPlotExporter() {

        return FPlot2DExporterDef.crete();
    }
}
