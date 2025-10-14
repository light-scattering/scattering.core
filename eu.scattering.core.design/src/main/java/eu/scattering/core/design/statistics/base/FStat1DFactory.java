package eu.scattering.core.design.statistics.base;

import org.json.JSONObject;

import java.util.Collection;

public interface FStat1DFactory {

    FStat1D getFStat1D();

    FStat1D getFStat1D(int[] values);

    FStat1D getFStat1D(double[] values);

    FStat1D getFStat1D(Collection<Double> values);

    FStat1D getFStat1D(JSONObject json);
}
