package eu.scattering.core.impl.statistics;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.StatisticsAspectExport;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.transfer.primitive.FPos2D;

import java.util.ArrayList;
import java.util.List;

public class StatisticsAspectExportDef implements StatisticsAspectExport {
    private final ScatFactory factory;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private String annotation = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private StatisticsAspectExportDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static StatisticsAspectExport create(ScatFactory factory) {

        return new StatisticsAspectExportDef(factory);
    }

    @Override
    public String toPythonPlotlyHistogram(FStat... stat) {
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

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toPythonPlotlyLinear(FPlot... plot) {
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
    public String toPythonPlotlyHistogram(FPlot... plot) {
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
    public String toPythonPlotlyFull(FPlotBar fPlotBar) {
        StringBuilder builder = new StringBuilder();
        String nameAnnotation = getAnnotation().isEmpty() ? "" : getAnnotation();
        String namePlot = getName().isEmpty() ? "" : getName();
        String nameX = getNameX().isEmpty() ? "x" : getNameX();
        String nameY = getNameY().isEmpty() ? "y" : getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        List<String> x = new ArrayList<>();
        List<String> avg = new ArrayList<>();
        List<String> std = new ArrayList<>();
        List<String> min = new ArrayList<>();
        List<String> max = new ArrayList<>();

        String name = fPlotBar.getName().isEmpty() ? "data" : fPlotBar.getName();

        for (int i = 0; i < fPlotBar.size() ; i++) {
            if (fPlotBar.getRefY(i).size() > 0) {
                x.add("" + fPlotBar.getX(i));
                avg.add("" + fPlotBar.getRefY(i).mean());
                min.add("" + fPlotBar.getRefY(i).min());
                max.add("" + fPlotBar.getRefY(i).max());

                if (fPlotBar.getRefY(i).size() > 2) {
                    std.add("" + fPlotBar.getRefY(i).std(true));
                } else {
                    std.add("0");
                }
            }
        }

        builder.append("\n");
        builder.append("fig.add_trace(\n");
        builder.append("  go.Scatter(\n");
        builder.append("    x=[").append(String.join(",", x)).append("],\n");
        builder.append("    y=[").append(String.join(",", max)).append("],\n");
        builder.append("    name='max',\n");
        builder.append("    hovertemplate='")
                .append(nameX).append(" = %{x}<br>")
                .append("max ").append(nameY)
                .append(" = %{y}<extra></extra>',\n");
        builder.append("    mode='lines',\n");
        builder.append("    line=dict(\n");
        builder.append("      dash='dot',\n");
        builder.append("      shape='linear',\n");
        builder.append("      width=0.5,\n");
        builder.append("      color='red'\n");
        builder.append("    )\n");
        builder.append("  )\n");
        builder.append(")\n");

        builder.append("fig.add_trace(\n");
        builder.append("  go.Scatter(\n");
        builder.append("    x=[").append(String.join(",", x)).append("],\n");
        builder.append("    y=[").append(String.join(",", avg)).append("],\n");
        builder.append("    error_y=dict(\n");
        builder.append("      type='data',\n");
        builder.append("      array=[").append(String.join(",", std)).append("],\n");
        builder.append("      visible=True,\n");
        builder.append("      thickness=0.5,\n");
        builder.append("      width=2,\n");
        builder.append("      color='gray'\n");
        builder.append("    ),\n");
        builder.append("    name='").append(name).append("',\n");
        builder.append("    hovertemplate='")
                .append(nameX).append(" = %{x}<br>")
                .append("avg ").append(nameY)
                .append(" = %{y}<extra></extra>',\n");
        builder.append("    mode='lines',\n");
        builder.append("    line=dict(\n");
        builder.append("      shape='linear',\n");
        builder.append("      width=1,\n");
        builder.append("      color='black'\n");
        builder.append("    )\n");
        builder.append("  )\n");
        builder.append(")\n");

        builder.append("fig.add_trace(\n");
        builder.append("  go.Scatter(\n");
        builder.append("    x=[").append(String.join(",", x)).append("],\n");
        builder.append("    y=[").append(String.join(",", min)).append("],\n");
        builder.append("    name='min',\n");
        builder.append("    hovertemplate='")
                .append(nameX).append(" = %{x}<br>")
                .append("min ").append(nameY)
                .append(" = %{y}<extra></extra>',\n");
        builder.append("    mode='lines',\n");
        builder.append("    line=dict(\n");
        builder.append("      dash='dot',\n");
        builder.append("      shape='linear',\n");
        builder.append("      width=0.5,\n");
        builder.append("      color='red'\n");
        builder.append("    )\n");
        builder.append("  )\n");
        builder.append(")\n");

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

    // -------------------------------------------------------------------------------------------------

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public StatisticsAspectExport setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public StatisticsAspectExport setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public StatisticsAspectExport setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public StatisticsAspectExport setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public StatisticsAspectExport setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public StatisticsAspectExport setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }
}

// https://plotly.com/python/reference/layout/xaxis/