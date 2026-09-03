package eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;

public interface FRandDist1DNormal extends FRandDist1D {

    double getCutoffMin();
    void setCutoffMin(double cutoff);

    double getCutoffMax();
    void setCutoffMax(double cutoff);
}
