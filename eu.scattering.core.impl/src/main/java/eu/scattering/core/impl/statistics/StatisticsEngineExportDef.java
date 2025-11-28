package eu.scattering.core.impl.statistics;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.StatisticsEngineExport;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.transfer.primitive.FPos2D;

import java.util.ArrayList;
import java.util.List;

public class StatisticsEngineExportDef implements StatisticsEngineExport {
    private final ScatFactory factory;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private String annotation = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private StatisticsEngineExportDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static StatisticsEngineExport create(ScatFactory factory) {

        return new StatisticsEngineExportDef(factory);
    }

    @Override
    public String exportPythonPlotlyLinear(FPlot... plot) {
        StringBuilder builder = new StringBuilder();
        String nameAnnotation = getAnnotation().isEmpty() ? "" : getAnnotation();
        String namePlot = getName().isEmpty() ? "" : getName();
        String nameX = getNameX().isEmpty() ? "x" : getNameX();
        String nameY = getNameY().isEmpty() ? "y" : getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        for (int i = 0 ; i < plot.length ; i++) {
            List<String> x = new ArrayList<>();
            List<String> y = new ArrayList<>();
            String name = plot[i].getName().isEmpty() ? "data " + i : plot[i].getName();

            for (int j = 0; j < plot[i].size() ; j++) {
                x.add("" + plot[i].getX(j));
                y.add("" + plot[i].getY(j));
            }

            builder.append("\n");
            builder.append("fig.add_trace(\n");
            builder.append("  go.Scatter(\n");
            builder.append("    x=[").append(String.join(",", x)).append("],\n");
            builder.append("    y=[").append(String.join(",", y)).append("],\n");
            builder.append("    name='").append(name).append("',\n");
            builder.append("    hovertemplate='").append(nameX).append(" = %{x}<br>").append(nameY).append(" = %{y}<extra></extra>',\n");
            builder.append("    mode='lines+markers', line_shape='linear'\n");
            builder.append("  )\n");
            builder.append(")\n");
        }

        builder.append("\n");

        builder.append("fig.update_layout(\n");
        builder.append("  hoverlabel=dict(font=dict(family='Courier New, monospace')),\n");
        builder.append("  title=dict(text='").append(namePlot).append("'),\n");
        builder.append("  xaxis_title='").append(nameX).append("',\n");
        if (getRangeX() != null) {
            builder.append("  xaxis_range=[").append(getRangeX().getD0()).append(",").append(getRangeX().getD1()).append("],\n");
        }
        builder.append("  yaxis_title='").append(nameY).append("',\n");
        if (getRangeY() != null) {
            builder.append("  yaxis_range=[").append(getRangeY().getD0()).append(",").append(getRangeY().getD1()).append("],\n");
        }
        builder.append(")\n\n");

        builder.append("fig.add_annotation(\n");
        builder.append("  text='").append(nameAnnotation).append("',\n");
        builder.append("  x=0.5, y=-0.15, xref='paper', yref='paper', showarrow=False, align='center'\n");
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    @Override
    public String exportPythonPlotlyHistogram(FStat... stat) {
        StringBuilder builder = new StringBuilder();
        String nameAnnotation = getAnnotation().isEmpty() ? "" : getAnnotation();
        String namePlot = getName().isEmpty() ? "" : getName();
        String nameX = getNameX().isEmpty() ? "" : getNameX();
        String nameY = getNameY().isEmpty() ? "" : getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        for (int i = 0 ; i < stat.length ; i++) {
            List<String> x = stat[i].getRefCore().stream().map(e -> "" + e).toList();
            String name = stat[i].getName().isEmpty() ? "data " + i : stat[i].getName();

            builder.append("\n");
            builder.append("fig.add_trace(\n");
            builder.append("  go.Histogram(\n");
            builder.append("    x=[").append(String.join(",", x)).append("],\n");
            builder.append("    name='").append(name).append("',\n");
            builder.append("    hovertemplate='range = [%{x})<br>count = %{y:d}<extra></extra>',\n");
            builder.append("    opacity=0.75\n");
            builder.append("  )\n");
            builder.append(")\n");
        }

        builder.append("\n");

        builder.append("fig.update_layout(\n");
        builder.append("  barmode='overlay',\n");
        builder.append("  hoverlabel=dict(font=dict(family='Courier New, monospace')),\n");
        builder.append("  title=dict(text='").append(namePlot).append("'),\n");
        builder.append("  xaxis_title='").append(nameX).append("',\n");
        if (getRangeX() != null) {
            builder.append("  xaxis_range=[").append(getRangeX().getD0()).append(",").append(getRangeX().getD1()).append("],\n");
        }
        builder.append("  yaxis_title='").append(nameY).append("',\n");
        if (getRangeY() != null) {
            builder.append("  yaxis_range=[").append(getRangeY().getD0()).append(",").append(getRangeY().getD1()).append("],\n");
        }
        builder.append(")\n\n");

        builder.append("fig.add_annotation(\n");
        builder.append("  text='").append(nameAnnotation).append("',\n");
        builder.append("  x=0.5, y=-0.15, xref='paper', yref='paper', showarrow=False, align='center'\n");
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    @Override
    public String exportPythonPlotlyHistogram(FPlot... plot) {
        StringBuilder builder = new StringBuilder();
        String nameAnnotation = getAnnotation().isEmpty() ? "" : getAnnotation();
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

            for (int j = 0; j < plot[i].size() ; j++) {
                double val = plot[i].getX(j) + correction;

                for (int k = 0 ; k < plot[i].getY(j) ; k++) {
                    x.add("" + val);
                }
            }

            builder.append("\n");
            builder.append("fig.add_trace(\n");
            builder.append("  go.Histogram(\n");
            builder.append("    x=[").append(String.join(",", x)).append("],\n");
            builder.append("    name='").append(name).append("',\n");
            builder.append("    hovertemplate='range = [%{x})<br>count = %{y:d}<extra></extra>',\n");
            builder.append("    opacity=0.75\n");
            builder.append("  )\n");
            builder.append(")\n");
        }

        builder.append("\n");

        builder.append("fig.update_layout(\n");
        builder.append("  barmode='overlay',\n");
        builder.append("  hoverlabel=dict(font=dict(family='Courier New, monospace')),\n");
        builder.append("  title=dict(text='").append(namePlot).append("'),\n");
        builder.append("  xaxis_title='").append(nameX).append("',\n");
        if (getRangeX() != null) {
            builder.append("  xaxis_range=[").append(getRangeX().getD0()).append(",").append(getRangeX().getD1()).append("],\n");
        }
        builder.append("  yaxis_title='").append(nameY).append("',\n");
        if (getRangeY() != null) {
            builder.append("  yaxis_range=[").append(getRangeY().getD0()).append(",").append(getRangeY().getD1()).append("],\n");
        }
        builder.append(")\n\n");

        builder.append("fig.add_annotation(\n");
        builder.append("  text='").append(nameAnnotation).append("',\n");
        builder.append("  x=0.5, y=-0.15, xref='paper', yref='paper', showarrow=False, align='center'\n");
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
    public StatisticsEngineExport setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public StatisticsEngineExport setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public StatisticsEngineExport setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public StatisticsEngineExport setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public StatisticsEngineExport setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public StatisticsEngineExport setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }
}

// https://plotly.com/python/reference/layout/xaxis/