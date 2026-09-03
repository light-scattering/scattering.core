package eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal;

public interface FRandDist1DNormalFactory {

    FRandDist1DNormal normal(double mean, double std);

    // -------------------------------------------------------------------------------------------------

    default FRandDist1DNormal normal() {

        return normal(0, 1);
    }
}
