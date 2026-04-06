package eu.scattering.core.impl.statistics;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.StatisticsAspectSave;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.base.FStatMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMeta;

import java.util.ArrayList;
import java.util.List;

public class StatisticsAspectSaveDef implements StatisticsAspectSave {
    private final ScatFactory factory;

    private StatisticsAspectSaveDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static StatisticsAspectSave create(ScatFactory factory) {

        return new StatisticsAspectSaveDef(factory);
    }

    // -------------------------------------------------------------------------------------------------
    
    @Override
    public String toCLI(FStat stat) {
        List<String> data = stat.getRefCore().stream().map(String::valueOf).toList();

        return "[" + String.join(",", data) + "]";
    }

    @Override
    public String toPythonPlotlyHistogram(FStatMeta config, FStat... stat) {

        if (config == null) {
            config = this.factory.getFStatMeta();
        }

        StringBuilder builder = new StringBuilder();
        String nameAnnotation = config.getAnnotation().isEmpty() ? "" : config.getAnnotation();
        String namePlot = config.getName().isEmpty() ? "" : config.getName();
        String nameX = config.getNameX().isEmpty() ? "" : config.getNameX();
        String nameY = config.getNameY().isEmpty() ? "" : config.getNameY();

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
        if (config.getRangeX() != null) {
            builder.append("  xaxis_range=[").append(config.getRangeX().getD0()).append(",").append(config.getRangeX().getD1()).append("],\n");
        }
        builder.append("  yaxis_title='").append(nameY).append("',\n");
        if (config.getRangeY() != null) {
            builder.append("  yaxis_range=[").append(config.getRangeY().getD0()).append(",").append(config.getRangeY().getD1()).append("],\n");
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
    public String toCLI(FPlot plot) {
        List<String> data = new ArrayList<>(plot.size());

        for (int i = 0 ; i < plot.size() ; i++) {
            data.add("[" + plot.getX(i) + "," + plot.getY(i) + "]");
        }

        return "[" + String.join(",", data) + "]";
    }

    @Override
    public String toPythonPlotly(FPlotMetaGlobal config, FPlot... plot) {

        if (config == null) {
            config = this.factory.getFPlotMetaGlobal();
        }

        int fontSize = config.getFontSize();

        StringBuilder builder = new StringBuilder();
        String namePlot = config.getName().isEmpty() ? "" : config.getName();
        String nameX = config.getNameX().isEmpty() ? "x" : config.getNameX();
        String nameY = config.getNameY().isEmpty() ? "y" : config.getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        for (int i = 0 ; i < plot.length ; i++) {
            FPlotMeta meta = plot[i].getRefMeta();

            List<String> x = new ArrayList<>();
            List<String> y = new ArrayList<>();
            String name = plot[i].getName().isEmpty() ? "data " + i : plot[i].getName();

            boolean lines = meta.getLinesShow();
            boolean markers = meta.getMarkersShow();

            if (!lines && !markers) {
                throw new IllegalArgumentException("Invalid plot mode");
            }

            String mode = lines && markers ? "lines+markers" : lines ? "lines" : "markers";

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
            builder.append("    hovertemplate='").append("x").append(" = %{x}<br>").append("y").append(" = %{y}<extra></extra>',\n");
            builder.append("    mode='").append(mode).append("',\n");
            builder.append("    line_shape='linear',\n");
            builder.append("    line=dict(color='").append(meta.getLinesColor()).append("', width=").append(meta.getLinesWidth()).append("),\n");
            builder.append("    marker=dict(color='").append(meta.getMarkersColor()).append("', size=").append(meta.getMarkersSize()).append(")\n");
            builder.append("  )\n");
            builder.append(")\n");
        }

        builder.append("\n");

        builder.append("fig.update_layout(\n");
        builder.append("  hoverlabel=dict(font=dict(family='Courier New, monospace')),\n");
        builder.append("  paper_bgcolor='rgba(0,0,0,0)',\n");
        builder.append("  plot_bgcolor='rgba(0,0,0,0)',\n");
        if (!namePlot.isEmpty()) {
            builder.append("  title=dict(\n");
            builder.append("    text='").append(namePlot).append("',\n");
            builder.append("    font=dict(size=").append((int) (fontSize * 1.5)).append("),\n");
            builder.append("    xanchor='center',\n");
            builder.append("    x=0.5,\n");
            builder.append("  ),\n");
        }
        builder.append("  xaxis=dict(\n");
        builder.append("    title=dict(text='").append(nameX).append("', font=dict(size=").append(fontSize).append(")),\n");
        builder.append("    tickfont=dict(size=").append(fontSize).append("),\n");
        if (config.getRangeX() != null) {
            builder.append("    range=[").append(config.getRangeX().getD0()).append(",").append(config.getRangeX().getD1()).append("],\n");
        }
        builder.append("  ),\n");
        builder.append("  yaxis=dict(\n");
        builder.append("    title=dict(text='").append(nameY).append("', font=dict(size=").append(fontSize).append(")),\n");
        builder.append("    tickfont=dict(size=").append(fontSize).append("),\n");
        if (config.getRangeY() != null) {
            builder.append("    range=[").append(config.getRangeY().getD0()).append(",").append(config.getRangeY().getD1()).append("],\n");
        }
        builder.append("  ),\n");
        builder.append("  legend=dict(\n");
        builder.append("    font=dict(size=").append(fontSize).append("),\n");
        builder.append("    xanchor='right',\n");
        builder.append("    x=0.95,\n");
        builder.append("    yanchor='top',\n");
        builder.append("    y=0.95,\n");
        builder.append("    bgcolor='rgba(255, 255, 255, 0.8)',\n");
        builder.append("    bordercolor='black',\n");
        builder.append("    borderwidth=1\n");
        builder.append("  ),\n");
        builder.append(")\n\n");

        builder.append("fig.update_xaxes(showline=True, linewidth=1, linecolor='black', gridcolor='rgba(230, 230, 230, 0.8)')\n");
        builder.append("fig.update_yaxes(showline=True, linewidth=1, linecolor='black', gridcolor='rgba(230, 230, 230, 0.8)')\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    @Override
    public String toPythonPlotlyHistogram(FPlotMetaGlobal config, FPlot... plot) {

        if (config == null) {
            config = this.factory.getFPlotMetaGlobal();
        }

        StringBuilder builder = new StringBuilder();
        String namePlot = config.getName().isEmpty() ? "" : config.getName();
        String nameX = config.getNameX().isEmpty() ? "" : config.getNameX();
        String nameY = config.getNameY().isEmpty() ? "" : config.getNameY();

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
        if (config.getRangeX() != null) {
            builder.append("  xaxis_range=[").append(config.getRangeX().getD0()).append(",").append(config.getRangeX().getD1()).append("],\n");
        }
        builder.append("  yaxis_title='").append(nameY).append("',\n");
        if (config.getRangeY() != null) {
            builder.append("  yaxis_range=[").append(config.getRangeY().getD0()).append(",").append(config.getRangeY().getD1()).append("],\n");
        }
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toPythonPlotly(FPlotBarMeta config, FPlotBar plotBar) {

        if (config == null) {
            config = this.factory.getFPlotBarMeta();
        }

        StringBuilder builder = new StringBuilder();
        String nameAnnotation = config.getAnnotation().isEmpty() ? "" : config.getAnnotation();
        String namePlot = config.getName().isEmpty() ? "" : config.getName();
        String nameX = config.getNameX().isEmpty() ? "x" : config.getNameX();
        String nameY = config.getNameY().isEmpty() ? "y" : config.getNameY();

        builder.append("import plotly.graph_objects as go\n");
        builder.append("import numpy as np\n\n");
        builder.append("fig = go.Figure()\n");

        List<String> x = new ArrayList<>();
        List<String> avg = new ArrayList<>();
        List<String> std = new ArrayList<>();
        List<String> min = new ArrayList<>();
        List<String> max = new ArrayList<>();

        String name = plotBar.getName().isEmpty() ? "data" : plotBar.getName();

        for (int i = 0; i < plotBar.size() ; i++) {
            if (plotBar.getRefY(i).size() > 0) {
                x.add("" + plotBar.getX(i));
                avg.add("" + plotBar.getRefY(i).mean());
                min.add("" + plotBar.getRefY(i).min());
                max.add("" + plotBar.getRefY(i).max());

                if (plotBar.getRefY(i).size() > 2) {
                    std.add("" + plotBar.getRefY(i).std(true));
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
        if (config.getRangeX() != null) {
            builder.append("  xaxis_range=[").append(config.getRangeX().getD0()).append(",").append(config.getRangeX().getD1()).append("],\n");
        }
        builder.append("  yaxis_title='").append(nameY).append("',\n");
        if (config.getRangeY() != null) {
            builder.append("  yaxis_range=[").append(config.getRangeY().getD0()).append(",").append(config.getRangeY().getD1()).append("],\n");
        }
        builder.append(")\n\n");

        builder.append("fig.add_annotation(\n");
        builder.append("  text='").append(nameAnnotation).append("',\n");
        builder.append("  x=0.5, y=-0.15, xref='paper', yref='paper', showarrow=False, align='center'\n");
        builder.append(")\n\n");

        builder.append("fig.show()");

        return builder.toString();
    }
}

// https://plotly.com/python/reference/layout/xaxis/