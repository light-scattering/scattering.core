package eu.scattering.core.design.main.engine.support;

import eu.scattering.core.design.main.engine.Disassemble;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Support<T> {

    FVector getOrigin();

    T setOriginRef(FVector origin);

    FPoint getBase();
    FPoint getHead();

    Consumer<Disassemble> project();
    Consumer<Disassemble> reflect();

    Function<Disassemble, List<Double>> getDistance();
    Consumer<Disassemble> setDistance(double distance);

    Function<Disassemble, List<Boolean>> isPartOf();
}
