package eu.scattering.core.transfer.statistics.FPlot2D.concrete;

import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2D;
import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2DExporter;

import java.util.ArrayList;
import java.util.List;

public class FPlot2DExporterDef implements FPlot2DExporter {

    private FPlot2DExporterDef() {}

    public static FPlot2DExporter crete() {

        return new FPlot2DExporterDef();
    }

    @Override
    public String toPythonPlotly(FPlot2D chart) {
        StringBuilder builder = new StringBuilder();

        List<String> x = new ArrayList<>();
        List<String> y = new ArrayList<>();

        for (int i = 0 ; i < chart.size() ; i++) {
            x.add("" + chart.getX(i));
            y.add("" + chart.getY(i));
        }

        builder.append("import plotly.express as px\n\n");
        builder.append("fig = px.line(x=[");
        builder.append(String.join(",", x));
        builder.append("], y=[");
        builder.append(String.join(",", y));
        builder.append("], labels={'x':'x', 'y':'y'})\n");
        builder.append("fig.show()");

        return builder.toString();
    }

    @Override
    public String toPythonPlotly(FPlot2D... chart) {
        StringBuilder builder = new StringBuilder();
        return null;
    }
}
