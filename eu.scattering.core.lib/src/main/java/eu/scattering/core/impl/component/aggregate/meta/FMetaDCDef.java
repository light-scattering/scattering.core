package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public class FMetaDCDef implements FMetaDC {
    private String plotApproximation;
    private String plotDerivative;
    private FPlot data;
    private long time = -1;
    private int refs = -1;

    private FMetaDCDef() {}

    public static FMetaDC create() {

        return new FMetaDCDef();
    }

    @Override
    public String getPlotApproximation() {

        return this.plotApproximation;
    }

    @Override
    public void setPlotApproximation(String plot) {

        this.plotApproximation = plot;
    }

    @Override
    public String getPlotDerivative() {

        return this.plotDerivative;
    }

    @Override
    public void setPlotDerivative(String plot) {

        this.plotDerivative = plot;
    }

    @Override
    public FPlot getData() {

        return this.data;
    }

    @Override
    public void setData(FPlot data) {

        this.data = data;
    }

    @Override
    public long getExecutionTime() {

        return this.time;
    }

    @Override
    public void setExecutionTime(long time) {

        this.time = time;
    }

    @Override
    public int getReferenceParticleCount() {

        return this.refs;
    }

    @Override
    public void setReferenceParticleTime(int refs) {

        this.refs = refs;
    }
}
