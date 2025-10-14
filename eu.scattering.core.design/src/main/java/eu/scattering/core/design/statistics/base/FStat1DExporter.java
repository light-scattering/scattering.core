package eu.scattering.core.design.statistics.base;

public interface FStat1DExporter {

    String toPythonPlotlyHistogram(FStat1D... stat);
}
