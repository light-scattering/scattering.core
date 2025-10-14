package eu.scattering.core.test.statistics;

import eu.scattering.core.design.statistics.StatisticsExport;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.base.FStat1D;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FChartLine")
public class FStat2DExporterTest {

    @Test
    @DisplayName("Plotly FPlot2D linear")
    void plotlyFPlot2DLinear() {
        FPlot2D fPlot = factory.getFPlot2D();
        StatisticsExport fPlotExporter = factory.getStatisticsExport();

        fPlotExporter.setName("Test");
        fPlotExporter.setNameX("X");
        fPlotExporter.setNameY("Y");

        fPlot.add(-3, 4);
        fPlot.add(-2, 3);
        fPlot.add(-1, 4);
        fPlot.add(0, 0);
        fPlot.add(1, -2);
        fPlot.add(2, 1);
        fPlot.add(3, -3);

        FPlot2D fPlotRaw = fPlot.copy();
        FPlot2D fPlotInterpolated = fPlot.copy();
        FPlot2D fPlotSimpleLinearRegression = fPlot.copy();

        fPlotRaw.setName("Raw");
        fPlotInterpolated.setName("Interpolated");
        fPlotSimpleLinearRegression.setName("Linear regression");

        fPlotInterpolated.interpolate(0.05, true);
        fPlotSimpleLinearRegression.simpleLinearRegression();

        String script = fPlotExporter.toPythonPlotlyLinear(fPlotRaw, fPlotInterpolated, fPlotSimpleLinearRegression);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FStat1D histogram")
    void plotlyFStat1DHistogram() {
        StatisticsExport fPlotExporter = factory.getStatisticsExport();

        fPlotExporter.setName("Test");
        fPlotExporter.setNameX("X");
        fPlotExporter.setNameY("Y");

        FStat1D fStatA = factory.getFStat1D();
        fStatA.add(1, 0.2, -5, 2, 4.2, -1.9, 2, 0.2, 0.2, 5.2, -2.6, -1.9, 0, 2.5, 4.1, -1.0, -0.3);

        FStat1D fStatB = factory.getFStat1D();
        fStatB.add(-2, 1, 8, 4.5, 3.1, 1.9, 1.8, 1.7, -1.3, 3.6, 5.1, 6.9, 2.9, -0.9, -0.8);

        fStatA.setName("Stat A");
        fStatB.setName("Stat B");

        String script = fPlotExporter.toPythonPlotlyHistogram(fStatA, fStatB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlot2D histogram")
    void plotlyFPlot2DHistogram() {
        StatisticsExport fPlotExporter = factory.getStatisticsExport();

        fPlotExporter.setName("Test");
        fPlotExporter.setNameX("X");
        fPlotExporter.setNameY("Y");

        FPlot2D fPlotA = factory.getFPlot2D();
        fPlotA.setName("A");

        fPlotA.add(-3, 4);
        fPlotA.add(-2, 3);
        fPlotA.add(-1, 4);
        fPlotA.add(0, 0);
        fPlotA.add(1, 2);
        fPlotA.add(2, 1);
        fPlotA.add(3, 3);

        FPlot2D fPlotB = factory.getFPlot2D();
        fPlotB.setName("B");

        fPlotB.add(0, 4);
        fPlotB.add(1, 3);
        fPlotB.add(2, 4);
        fPlotB.add(3, 0);
        fPlotB.add(4, 2);
        fPlotB.add(5, 1);
        fPlotB.add(6, 3);

        String script = fPlotExporter.toPythonPlotlyHistogram(fPlotA, fPlotB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Nested
    @Tag("Meta")
    @DisplayName("Meta")
    class FPlot2DExporterMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            StatisticsExport fPlotExporter = factory.getStatisticsExport();

            fPlotExporter.setName("a");
            fPlotExporter.setNameX("b");
            fPlotExporter.setNameY("c");

            assertEquals("a", fPlotExporter.getName());
            assertEquals("b", fPlotExporter.getNameX());
            assertEquals("c", fPlotExporter.getNameY());
        }
    }
}
