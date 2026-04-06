package eu.scattering.core.test.aspect.export;

import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.base.FStatMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.statistics.construct.plot.util.FPlotInterpolator;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMeta;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Statistics export")
public class StatisticsAspectExportTest {

    @Test
    @DisplayName("Plotly FStat histogram")
    void plotlyFStatHistogram() {
        FStatMeta fStatMeta = factory.getFStatMeta();

        fStatMeta.setName("Test");
        fStatMeta.setAnnotation("Annotation");
        fStatMeta.setNameX("X");
        fStatMeta.setNameY("Y");
        fStatMeta.setRangeX(-10, 10);
        fStatMeta.setRangeY(0, 10);

        FStat fStatA = factory.getFStat();
        fStatA.add(1, 0.2, -5, 2, 4.2, -1.9, 2, 0.2, 0.2, 5.2, -2.6, -1.9, 0, 2.5, 4.1, -1.0, -0.3);

        FStat fStatB = factory.getFStat();
        fStatB.add(-2, 1, 8, 4.5, 3.1, 1.9, 1.8, 1.7, -1.3, 3.6, 5.1, 6.9, 2.9, -0.9, -0.8);

        fStatA.setName("Stat A");
        fStatB.setName("Stat B");

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotlyHistogram(fStatMeta, fStatA, fStatB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FStat histogram - Without config")
    void plotlyFStatHistogramWithoutConfig() {
        FStat fStatA = factory.getFStat();
        fStatA.add(1, 0.2, -5, 2, 4.2, -1.9, 2, 0.2, 0.2, 5.2, -2.6, -1.9, 0, 2.5, 4.1, -1.0, -0.3);

        FStat fStatB = factory.getFStat();
        fStatB.add(-2, 1, 8, 4.5, 3.1, 1.9, 1.8, 1.7, -1.3, 3.6, 5.1, 6.9, 2.9, -0.9, -0.8);

        fStatA.setName("Stat A");
        fStatB.setName("Stat B");

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotlyHistogram(fStatA, fStatB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlot linear")
    void plotlyFPlotLinear() {
        FPlot fPlot = factory.getFPlot();
        FPlotMetaGlobal fPlotMeta = factory.getFPlotMetaGlobal();

        fPlotMeta.setName("Name");
        fPlotMeta.setNameX("X");
        fPlotMeta.setNameY("Y");
        fPlotMeta.setRangeX(-5, 5);
        fPlotMeta.setRangeY(-6, 6);

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

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(fPlotMeta, fPlot, fPlotInterpolated, fPlotSimpleLinearRegression);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlot linear - Without config")
    void plotlyFPlotLinearWithoutConfig() {
        FPlot fPlot = factory.getFPlot();

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

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(fPlot, fPlotInterpolated, fPlotSimpleLinearRegression);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlot histogram")
    void plotlyFPlotHistogram() {
        FPlotMetaGlobal fPlotMeta = factory.getFPlotMetaGlobal();

        fPlotMeta.setName("Test");
        fPlotMeta.setNameX("X");
        fPlotMeta.setNameY("Y");
        fPlotMeta.setRangeX(-10, 10);
        fPlotMeta.setRangeY(0, 10);

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

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotlyHistogram(fPlotMeta, fPlotA, fPlotB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlot histogram - Without config")
    void plotlyFPlotHistogramWithoutConfig() {
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

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotlyHistogram(fPlotA, fPlotB);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlotBar")
    void plotlyFPlotBar() {
        FPlotBarMeta fPlotBarMeta = factory.getFPlotBarMeta();

        fPlotBarMeta.setName("Test");
        fPlotBarMeta.setAnnotation("Annotation");
        fPlotBarMeta.setNameX("X");
        fPlotBarMeta.setNameY("Y");
        fPlotBarMeta.setRangeX(-10, 10);
        fPlotBarMeta.setRangeY(-10, 10);

        FPlotBar fPlotBar = factory.getFPlotBar();
        fPlotBar.setName("data");

        fPlotBar.addRef(-8, factory.getFStat(2, 3, 4));
        fPlotBar.addRef(-6, factory.getFStat(-1, 0, 1, 1, 1, 3));
        fPlotBar.addRef(-4, factory.getFStat());
        fPlotBar.addRef(-2, factory.getFStat(4, 4, 6));
        fPlotBar.addRef(0, factory.getFStat(-5, -4, -2));
        fPlotBar.addRef(2, factory.getFStat(1.1, 1.8, 2.3, 1.2, 1.8, 1.7, 1.6, 2.0, 1.3));
        fPlotBar.addRef(4, factory.getFStat(3));
        fPlotBar.addRef(6, factory.getFStat(0, 1, 2));
        fPlotBar.addRef(8, factory.getFStat(-6, -5, -4));

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(fPlotBarMeta, fPlotBar);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Test
    @DisplayName("Plotly FPlotBar - Without config")
    void plotlyFPlotBarWithoutConfig() {
        FPlotBar fPlotBar = factory.getFPlotBar();
        fPlotBar.setName("data");

        fPlotBar.addRef(-8, factory.getFStat(2, 3, 4));
        fPlotBar.addRef(-6, factory.getFStat(-1, 0, 1, 1, 1, 3));
        fPlotBar.addRef(-4, factory.getFStat());
        fPlotBar.addRef(-2, factory.getFStat(4, 4, 6));
        fPlotBar.addRef(0, factory.getFStat(-5, -4, -2));
        fPlotBar.addRef(2, factory.getFStat(1.1, 1.8, 2.3, 1.2, 1.8, 1.7, 1.6, 2.0, 1.3));
        fPlotBar.addRef(4, factory.getFStat(3));
        fPlotBar.addRef(6, factory.getFStat(0, 1, 2));
        fPlotBar.addRef(8, factory.getFStat(-6, -5, -4));

        String script = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(fPlotBar);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.graph_objects"))
        );
    }

    @Nested
    @Tag("Meta")
    @DisplayName("Meta")
    class StatisticsConfigTest {

        @Test
        @DisplayName("FStat meta config")
        void validateFStatMetaConfig() {
            FStatMeta fStatMeta = factory.getFStatMeta();

            FStatMeta results = fStatMeta
                    .setName("a")
                    .setNameX("b")
                    .setNameY("c")
                    .setAnnotation("d")
                    .setRangeX(1, 2)
                    .setRangeY(3, 4);

            assertSame(fStatMeta, results);
            assertEquals("a", fStatMeta.getName());
            assertEquals("b", fStatMeta.getNameX());
            assertEquals("c", fStatMeta.getNameY());
            assertEquals("d", fStatMeta.getAnnotation());
            assertEquals(1, fStatMeta.getRangeX().getD0());
            assertEquals(2, fStatMeta.getRangeX().getD1());
            assertEquals(3, fStatMeta.getRangeY().getD0());
            assertEquals(4, fStatMeta.getRangeY().getD1());
        }

        @Test
        @DisplayName("FPlot meta config")
        void validateFPlotMetaConfig() {
            FPlotMetaGlobal fPlotMeta = factory.getFPlotMetaGlobal();

            FPlotMetaGlobal results = fPlotMeta
                    .setName("a")
                    .setNameX("b")
                    .setNameY("c")
                    .setRangeX(1, 2)
                    .setRangeY(3, 4);

            assertSame(fPlotMeta, results);
            assertEquals("a", fPlotMeta.getName());
            assertEquals("b", fPlotMeta.getNameX());
            assertEquals("c", fPlotMeta.getNameY());
            assertEquals(1, fPlotMeta.getRangeX().getD0());
            assertEquals(2, fPlotMeta.getRangeX().getD1());
            assertEquals(3, fPlotMeta.getRangeY().getD0());
            assertEquals(4, fPlotMeta.getRangeY().getD1());
        }

        @Test
        @DisplayName("FPlotBar meta config")
        void validateFPlotBarMetaConfig() {
            FPlotBarMeta fPlotBarMeta = factory.getFPlotBarMeta();

            FPlotBarMeta results = fPlotBarMeta
                    .setName("a")
                    .setNameX("b")
                    .setNameY("c")
                    .setAnnotation("d")
                    .setRangeX(1, 2)
                    .setRangeY(3, 4);

            assertSame(fPlotBarMeta, results);
            assertEquals("a", fPlotBarMeta.getName());
            assertEquals("b", fPlotBarMeta.getNameX());
            assertEquals("c", fPlotBarMeta.getNameY());
            assertEquals("d", fPlotBarMeta.getAnnotation());
            assertEquals(1, fPlotBarMeta.getRangeX().getD0());
            assertEquals(2, fPlotBarMeta.getRangeX().getD1());
            assertEquals(3, fPlotBarMeta.getRangeY().getD0());
            assertEquals(4, fPlotBarMeta.getRangeY().getD1());
        }
    }
}
