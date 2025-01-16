package eu.scattering.core.design.mutables.geometry.primitive.point;

public interface FPointEngineRotation {

    FPoint setAngle(FPoint origin, FPoint op, double angle);

    FPoint rotate(FPoint origin, FPoint op, double angle);

    //  FPoint rotate(FPoint origin, FRotRg core);
    //  FPoint rotate(FPoint origin, FRotQt core);
}
