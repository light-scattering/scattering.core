package eu.scattering.core.design.main.vo.rotor;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

public interface FRotorFactory {

    FRotor getFRotor(FVector axis, double angle);

    FRotor getFRotor(FPoint axis, double angle);

    FRotor getFRotor(String structure);
}
