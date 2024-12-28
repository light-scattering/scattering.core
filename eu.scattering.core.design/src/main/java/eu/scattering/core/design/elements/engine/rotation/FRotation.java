package eu.scattering.core.design.elements.engine.rotation;

import eu.scattering.core.design.annotations.GeometryExtension;
import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.Engine;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

import java.util.function.Consumer;

public interface FRotation extends Engine<FRotation> {

    FQuaternion getCore();

    FPairPos3D getRotationAxis();
    double getRotationAngle();

    @GeometryExtension
    Consumer<Geometry> rotate();
}
