package eu.scattering.core.design.storage.transfer.polynomial.variant;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.utility.annotation.Modificator;

public interface FPoly extends Storage {

    int size();

    double at(int n);
    double value(double x);

    @Modificator
    double[] getRefCore();
}
