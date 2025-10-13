package eu.scattering.core.transfer.container.statistics;

import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2D;
import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2DExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FChartLine")
public class FStat2DExporterTest {
    private final TransferFactory factory = TransferFactoryConcrete.create();

    @Test
    @DisplayName("Standardize")
    void standardize() {
        FPlot2D chart = factory.getFPlot2D();
        FPlot2DExporter chartExporter = factory.getFPlotExporter();

        chart.setApproxMethod(FPlot2D.Approx.HERMITE);

        chart.add(-2, 2);

        chart.add(0, 0);

        chart.add(2, 2);

        chart.interpolate(0.01, true);

        String script = chartExporter.toPythonPlotly(chart);

        Assertions.assertAll("Test values",
                () -> assertTrue(script.contains("plotly.express"))
        );
    }
}
