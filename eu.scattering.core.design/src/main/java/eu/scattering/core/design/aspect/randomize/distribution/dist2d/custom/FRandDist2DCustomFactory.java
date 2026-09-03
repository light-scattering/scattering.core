package eu.scattering.core.design.aspect.randomize.distribution.dist2d.custom;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;

import java.util.function.BiConsumer;

public interface FRandDist2DCustomFactory {

    FRandDist2DCustom custom(BiConsumer<FRandEngine, Double[]> consumer);
}
