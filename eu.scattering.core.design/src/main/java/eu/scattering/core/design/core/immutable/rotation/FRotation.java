package eu.scattering.core.design.core.immutable.rotation;

import eu.scattering.core.design.core.Core;
import eu.scattering.core.design.core.mutable.geometry.Geometry;
import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;
import eu.scattering.core.design.core.mutable.number.quaternion.FQuaternion;

import java.util.function.Consumer;

public interface FRotation extends Core<FRotation> {

    FQuaternion getCore();

    FVector getRotationAxis();
    double getRotationAngle();

    //--------------------------------------------------
    // Extensions
    //--------------------------------------------------

    Consumer<Geometry> rotate();
}
