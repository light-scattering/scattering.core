package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRandEngineDef implements FRandEngine {
    private final FRandGenerator core;

    private FRandEngineDef(FRandGenerator core) {

        this.core = core;
    }

    public static FRandEngine create(FRandGenerator core) {

        return new FRandEngineDef(core);
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator getFRand() {

        return this.core;
    }

    @Override
    public FComplex rndPos(FComplex in, FPairPos2D range) {

        in.applyStateFrom(core.nextDouble2D(range));

        return in;
    }

    @Override
    public FComplex rndPosInCircle(FComplex in, double radius) {

        in.applyStateFrom(core.nextDoubleInCircle(radius));

        return in;
    }

    @Override
    public FComplex rndPosOnCircle(FComplex in, double radius) {

        in.applyStateFrom(core.nextDoubleOnCircle(radius));

        return in;
    }

    @Override
    public FQuaternion rndPos(FQuaternion in, FPairPos4D range) {

        in.applyStateFrom(core.nextDouble4D(range));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint varyAngle(FPoint in) {
        double radius = in.getMagnitude();

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPosInRange(FPoint in, FPairPos3D range) {

        in.applyStateFrom(core.nextDouble3D(range));

        return in;
    }

    @Override
    public FPoint rndPosInSphere(FPoint in, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPosOnSphere(FPoint in, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPosOnAxis(FPoint in, FPoint dir) {

        in.applyStateFrom(dir);
        in.setMagnitude(core.nextDouble(EPSILON, dir.getMagnitude()));

        return in;
    }

    @Override
    public FPoint rndPosBaseInCircle(FPoint in, FPoint dir, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint rndPosBaseOnCircle(FPoint in, FPoint dir, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint rndPosHeadInCircle(FPoint in, FPoint dir, double radius) {

        rndPosBaseInCircle(in, dir, radius);

        in.add(dir);

        return in;
    }

    @Override
    public FPoint rndPosHeadOnCircle(FPoint in, FPoint dir, double radius) {

        rndPosBaseOnCircle(in, dir, radius);

        in.add(dir);

        return in;
    }

    @Override
    public FVector varyAngle(FVector in) {
        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();

        in.moveBaseToCenter();
        varyAngle(in.getRefHead());
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FVector rndPos(FVector in, FPairPos3D range) {

        rndPosInRange(in.getRefBase(), range);
        rndPosInRange(in.getRefHead(), range);

        return in;
    }

    @Override
    public FVector rndPosInSphere(FVector in, double radius) {

        rndPosInSphere(in.getRefBase(), radius);
        rndPosInSphere(in.getRefHead(), radius);

        return in;
    }

    @Override
    public FVector rndPosOnSphere(FVector in, double radius) {

        rndPosOnSphere(in.getRefBase(), radius);
        rndPosOnSphere(in.getRefHead(), radius);

        return in;
    }

    @Override
    public FPoint rndPosOnAxis(FPoint in, FVector dir) {

        in.applyStateFrom(dir.getRefHead());
        in.sub(dir.getRefBase());
        in.setMagnitude(core.nextDouble(EPSILON, in.getMagnitude()));
        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint rndPosBaseInCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint rndPosBaseOnCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint rndPosHeadInCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefHead());

        return in;
    }

    @Override
    public FPoint rndPosHeadOnCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefHead());

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint rndPosBaseInCircle(FPoint in, FRay dir, double radius) {

        return rndPosBaseInCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint rndPosBaseOnCircle(FPoint in, FRay dir, double radius) {

        return rndPosBaseOnCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint rndPosOnSegment(FPoint in, FSegment dir) {

        return rndPosOnAxis(in, dir.getRefOrigin());
    }

    @Override
    public FPoint rndPosBaseInCircle(FPoint in, FSegment dir, double radius) {

        return rndPosBaseInCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint rndPosBaseOnCircle(FPoint in, FSegment dir, double radius) {

        return rndPosBaseOnCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint rndPosHeadInCircle(FPoint in, FSegment dir, double radius) {

        return rndPosHeadInCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint rndPosHeadOnCircle(FPoint in, FSegment dir, double radius) {

        return rndPosHeadOnCircle(in, dir.getRefOrigin(), radius);
    }
}
