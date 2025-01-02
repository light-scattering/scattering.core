package eu.scattering.core.design.elements.algebra.geometry.primitive.point;

public interface FPointEngineRotation {

    FPoint setAngle(FPoint origin, FPoint ref, double angle);

    FPoint rotate(FPoint origin, FPoint ref, double angle);
}
