package eu.scattering.core.design.aspect.randomize.distribution.dist1d.custom;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;

import java.util.function.BiConsumer;

public interface FRandDist1DCustomFactory {

    FRandDist1DCustom custom(BiConsumer<FRandEngine, Double[]> consumer);
}
