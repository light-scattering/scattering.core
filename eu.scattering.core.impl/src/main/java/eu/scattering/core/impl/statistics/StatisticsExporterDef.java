package eu.scattering.core.impl.statistics;

import eu.scattering.core.design.statistics.StatisticsExport;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.base.FStat1D;

import java.util.ArrayList;
import java.util.List;

public class StatisticsExporterDef implements StatisticsExport {
    private String name = "";
    private String nameX = "";
    private String nameY = "";

    private StatisticsExporterDef() {}

    public static StatisticsExport create() {

        return new StatisticsExporterDef();
    }

    @Override
    public String toPythonPlotlyLinear(FPlot2D... plot) {
        StringBuilder builder = new StringBuilder();
        String namePlot = getName().isEmpty() ? "" : getName();
        String nameX = getNameX().isEmpty() ? "" : getNameX();
        String nameY = getNameY().isEmpty() ? "" : getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        for (int i = 0 ; i < plot.length ; i++) {
            List<String> x = new ArrayList<>();
            List<String> y = new ArrayList<>();
            String name = plot[i].getName().isEmpty() ? "" + i : plot[i].getName();

            for (int j = 0; j < plot[i].size() ; j++) {
                x.add("" + plot[i].getX(j));
                y.add("" + plot[i].getY(j));
            }

            builder.append("fig.add_trace(go.Scatter(");
            builder.append("x=[" + String.join(",", x) + "], ");
            builder.append("y=[" + String.join(",", y) + "], ");
            builder.append("name='" + name + "', ");
            builder.append("hoverinfo='text+name', mode='lines+markers', line_shape='linear'))\n");
        }

        builder.append("\n");

        builder.append("fig.update_layout(\n");
        builder.append("title=dict(text='" + namePlot + "'),\n");
        builder.append("xaxis=dict(title=dict(text='" + nameX + "')),\n");
        builder.append("yaxis=dict(title=dict(text='" + nameY + "')),\n");
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    @Override
    public String toPythonPlotlyHistogram(FStat1D... stat) {
        StringBuilder builder = new StringBuilder();
        String namePlot = getName().isEmpty() ? "" : getName();
        String nameX = getNameX().isEmpty() ? "" : getNameX();
        String nameY = getNameY().isEmpty() ? "" : getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        for (int i = 0 ; i < stat.length ; i++) {
            List<String> x = stat[i].getData().stream().map(e -> "" + e).toList();
            String name = stat[i].getName().isEmpty() ? "" + i : stat[i].getName();

            builder.append("fig.add_trace(go.Histogram(");
            builder.append("x=[" + String.join(",", x) + "], ");
            builder.append("name='" + stat[i].getName() + "', ");
            builder.append("opacity=0.75))\n");
        }

        builder.append("\n");
        builder.append("fig.update_layout(\n");
        builder.append("barmode='overlay',\n");
        builder.append("title=dict(text='" + namePlot + "'),\n");
        builder.append("xaxis=dict(title=dict(text='" + nameX + "')),\n");
        builder.append("yaxis=dict(title=dict(text='" + nameY + "')),\n");
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    @Override
    public String toPythonPlotlyHistogram(FPlot2D... plot) {
        StringBuilder builder = new StringBuilder();
        String namePlot = getName().isEmpty() ? "" : getName();
        String nameX = getNameX().isEmpty() ? "" : getNameX();
        String nameY = getNameY().isEmpty() ? "" : getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        for (int i = 0 ; i < plot.length ; i++) {
            List<String> x = new ArrayList<>();
            String name = plot[i].getName().isEmpty() ? "" + i : plot[i].getName();
            double step = plot[i].getX(1) - plot[i].getX(0);
            double correction = step / 2;
            double start = plot[i].getX(0);
            double end = plot[i].getX(plot[i].size() - 1) + step;

            for (int j = 0; j < plot[i].size() ; j++) {
                double val = plot[i].getX(j) + correction;

                for (int k = 0 ; k < plot[i].getY(j) ; k++) {
                    x.add("" + val);
                }
            }

            builder.append("fig.add_trace(go.Histogram(");
            builder.append("x=[" + String.join(",", x) + "], ");
            builder.append("xbins=dict(start=" + start + ", end=" + end + ", size=" + step + "),");
            builder.append("name='" + name + "', ");
            builder.append("opacity=0.75))\n");
        }

        builder.append("\n");

        builder.append("fig.update_layout(\n");
        builder.append("barmode='overlay',\n");
        builder.append("title=dict(text='" + namePlot + "'),\n");
        builder.append("xaxis=dict(title=dict(text='" + nameX + "')),\n");
        builder.append("yaxis=dict(title=dict(text='" + nameY + "')),\n");
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public void setNameX(String nameX) {

        this.nameX = nameX;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public void setNameY(String nameY) {

        this.nameY = nameY;
    }
}
