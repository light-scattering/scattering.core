package eu.scattering.core.design.main.engine.type.quaternion;

import eu.scattering.core.design.main.engine.Engine;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.type.complex.FComplex;

public interface FQuaternion extends FQuaternionAdvanced,
        Engine<FPoint> {

    FQuaternion set(double re, double i, double j, double k);
    FQuaternion setGraphics(FPoint fPoint, double angle);

    double getRe();
    FComplex setRe(double re);

    double getIm();
    FComplex setIm(double i, double j, double k);

    FPoint getGraphicsFPoint();
    double getGraphicsAngle();

    Object clone();
}
