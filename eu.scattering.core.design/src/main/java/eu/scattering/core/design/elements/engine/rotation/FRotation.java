package eu.scattering.core.design.elements.engine.rotation;

import eu.scattering.core.design.annotations.GeometryExtension;
import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.Engine;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

import java.util.function.Consumer;

public interface FRotation extends Engine<FRotation> {

    FRot getRotation(FPairPos3D axis, double angle);
    FRot getRotation(FPos3D axis, double angle);

    @GeometryExtension
    Consumer<Geometry> rotate(FRot core);
}
