package eu.scattering.core.design.core.engine.rotation;

import eu.scattering.core.design.core.engine.Engine;
import eu.scattering.core.design.core.algebra.geometry.Geometry;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.core.algebra.number.quaternion.FQuaternion;

import java.util.function.Consumer;

public interface FRotation extends Engine<FRotation> {

    FQuaternion getCore();

    FVector getRotationAxis();
    double getRotationAngle();

    //--------------------------------------------------
    // Extensions
    //--------------------------------------------------

    Consumer<Geometry> rotate();
}
