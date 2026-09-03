package eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;

import java.util.function.BiConsumer;

public interface FRandDist3DCustomFactory {

    FRandDist3DCustom custom(BiConsumer<FRandEngine, Double[]> consumer);
}
