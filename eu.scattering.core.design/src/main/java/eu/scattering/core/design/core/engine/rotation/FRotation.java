package eu.scattering.core.design.core.engine.rotation;

import eu.scattering.core.design.core.algebra.geometry.Geometry;
import eu.scattering.core.design.core.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.core.data.position.FTuplePos3D;
import eu.scattering.core.design.core.engine.Engine;

import java.util.function.Consumer;

public interface FRotation extends Engine<FRotation> {

    FQuaternion getCore();

    FTuplePos3D getRotationAxis();
    double getRotationAngle();

    //--------------------------------------------------
    // Extensions
    //--------------------------------------------------

    Consumer<Geometry> rotate();
}
