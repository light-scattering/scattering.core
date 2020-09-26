package eu.scattering.core.test.design.main.fixed.rotation;

import eu.scattering.core.test.design.main.Main;
import eu.scattering.core.test.design.main.mutable.geometry.Geometry;
import eu.scattering.core.test.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.test.design.main.mutable.number.quaternion.FQuaternion;

import java.util.function.Consumer;

public interface FRotation extends Main<FRotation> {

    FQuaternion getCore();

    FVector getRotationAxis();
    double getRotationAngle();

    Consumer<Geometry> rotate();
}
