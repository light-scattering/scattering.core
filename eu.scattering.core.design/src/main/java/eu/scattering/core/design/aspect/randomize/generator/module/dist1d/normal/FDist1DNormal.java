package eu.scattering.core.design.aspect.randomize.generator.module.dist1d.normal;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;

public interface FDist1DNormal extends FDist1D {

//    void setListValidation(double std, double err);

    double getCutoffMin();
    void setCutoffMin(double cutoff);

    double getCutoffMax();
    void setCutoffMax(double cutoff);
}
