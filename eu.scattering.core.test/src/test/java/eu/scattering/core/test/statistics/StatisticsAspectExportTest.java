package eu.scattering.core.test.statistics;

import eu.scattering.core.design.statistics.StatisticsAspectExport;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.statistics.construct.utils.FPlotInterpolator;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FChartLine")
public class StatisticsAspectExportTest {

    @Test
    @DisplayName("Plotly FPlot linear")
    void plotlyFPlotLinear() {
        FPlot fPlot = factory.getFPlot();
        StatisticsAspectExport fPlotExporter = factory.getExportAspect().getFPlotContext();

        fPlotExporter.setName("Name");
        fPlotExporter.setAnnotation("Annotation");
        fPlotExporter.setNameX("X");
        fPlotExporter.setNameY("Y");
        fPlotExporter.setRangeX(-5, 5);
        fPlotExporter.setRangeY(-6, 6);

        fPlot.add(-3, 4);
        fPlot.add(-2, 3);
        fPlot.add(-1, 4);
        fPlot.add(0, 0);
        fPlot.add(1, -2);
        fPlot.add(2, 1);
        fPlot.add(3, -3);

        FPlot fPlotInterpolated = fPlot.apx().sampleStep(FPlotInterpolator::hermite, 0.05);

        FPlot fPlotSimpleLinearRegression = fPlot.copy();
        fPlotSimpleLinearRegression.setY(fPlotSimpleLinearRegression.reg().poly(1));

        fPlot.setName("Raw");
        fPlotInterpolated.setName("Interpolated");
        fPlotSimpleLinearRegression.setName("Linear regression");

        String script = fPlotExporter.toPythonPlotlyLinear(fPlot, fPlotInterpolated, fPlotSimpleLinearRegression);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FStat histogram")
    void plotlyFStatHistogram() {
        StatisticsAspectExport fPlotExporter = factory.getExportAspect().getFPlotContext();

        fPlotExporter.setName("Test");
        fPlotExporter.setAnnotation("Annotation");
        fPlotExporter.setNameX("X");
        fPlotExporter.setNameY("Y");
        fPlotExporter.setRangeX(-10, 10);
        fPlotExporter.setRangeY(0, 10);

        FStat fStatA = factory.getFStat();
        fStatA.add(1, 0.2, -5, 2, 4.2, -1.9, 2, 0.2, 0.2, 5.2, -2.6, -1.9, 0, 2.5, 4.1, -1.0, -0.3);

        FStat fStatB = factory.getFStat();
        fStatB.add(-2, 1, 8, 4.5, 3.1, 1.9, 1.8, 1.7, -1.3, 3.6, 5.1, 6.9, 2.9, -0.9, -0.8);

        fStatA.setName("Stat A");
        fStatB.setName("Stat B");

        String script = fPlotExporter.toPythonPlotlyHistogram(fStatA, fStatB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlot histogram")
    void plotlyFPlotHistogram() {
        StatisticsAspectExport fPlotExporter = factory.getExportAspect().getFPlotContext();

        fPlotExporter.setName("Test");
        fPlotExporter.setAnnotation("Annotation");
        fPlotExporter.setNameX("X");
        fPlotExporter.setNameY("Y");
        fPlotExporter.setRangeX(-10, 10);
        fPlotExporter.setRangeY(0, 10);

        FPlot fPlotA = factory.getFPlot();
        fPlotA.setName("Plot A");

        fPlotA.add(-3, 4);
        fPlotA.add(-2, 3);
        fPlotA.add(-1, 4);
        fPlotA.add(0, 0);
        fPlotA.add(1, 2);
        fPlotA.add(2, 1);
        fPlotA.add(3, 3);

        FPlot fPlotB = factory.getFPlot();
        fPlotB.setName("Plot B");

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
    class FPlotExporterMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            StatisticsAspectExport fPlotExporter = factory.getExportAspect().getFPlotContext();

            StatisticsAspectExport results = fPlotExporter
                    .setName("a")
                    .setNameX("b")
                    .setNameY("c")
                    .setAnnotation("d")
                    .setRangeX(1, 2)
                    .setRangeY(3, 4);

            assertSame(fPlotExporter, results);
            assertEquals("a", fPlotExporter.getName());
            assertEquals("b", fPlotExporter.getNameX());
            assertEquals("c", fPlotExporter.getNameY());
            assertEquals("d", fPlotExporter.getAnnotation());
            assertEquals(1, fPlotExporter.getRangeX().getD0());
            assertEquals(2, fPlotExporter.getRangeX().getD1());
            assertEquals(3, fPlotExporter.getRangeY().getD0());
            assertEquals(4, fPlotExporter.getRangeY().getD1());        }
    }
}
