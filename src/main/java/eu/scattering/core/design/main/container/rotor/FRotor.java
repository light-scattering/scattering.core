package eu.scattering.core.design.main.container.rotor;

import eu.scattering.core.design.main.Main;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

import java.util.function.Consumer;

public interface FRotor extends Main<FRotor> {

    FQuaternion getCore();

    FVector getRotationAxis();
    double getRotationAngle();

    Consumer<Engine> rotate();
}
