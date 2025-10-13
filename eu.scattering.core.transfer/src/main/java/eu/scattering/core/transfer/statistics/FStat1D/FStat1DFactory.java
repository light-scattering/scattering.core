package eu.scattering.core.transfer.statistics.FStat1D;

import eu.scattering.core.transfer.statistics.FStat1D.concrete.FStat1DDef;
import org.json.JSONObject;

import java.util.Collection;

public interface FStat1DFactory {

    default FStat1D getFStat1D() {

        return FStat1DDef.create();
    }

    default FStat1D getFStat1D(int[] values) {

        return FStat1DDef.create(values);
    }

    default FStat1D getFStat1D(double[] values) {

        return FStat1DDef.create(values);
    }

    default FStat1D getFStat1D(Collection<Double> values) {

        return FStat1DDef.create(values);
    }

    default FStat1D getFStat1D(JSONObject json) {

        return FStat1DDef.create(json);
    }
}
