package eu.scattering.core.implementation.main.box.rotation;

import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.box.rotation.FRotation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Consumer;

import static eu.scattering.core.Config.factory;

public class FRotationDefault implements FRotation {

    private final FPoint offset = factory.getFPoint();
    private final FQuaternion core = factory.getFQuaternion();
    private final double[][] rotation = new double[3][3];

    private FRotationDefault(FVector axis, double angle) {

        if (axis.isNonDirectional()) {
            throw new IllegalArgumentException("The rotation axis is not defined");
        }

        offset.set(axis.getBase());

        initializeCore(axis.copy().moveBase().getHead(), angle);
        initializeRotor();
    }

    private FRotationDefault(FPoint axis, double angle) {

        if (axis.isZero()) {
            throw new IllegalArgumentException("The rotation axis is not defined");
        }

        initializeCore(axis.copy(), angle);
        initializeRotor();
    }

    private FRotationDefault(double re, double i, double j, double k) {
        double direction = 1 - (re * re);

        if (direction <= 0) {
            throw new IllegalArgumentException("The rotation axis is not defined");
        }

        this.core.set(re, i, j, k);

        initializeRotor();
    }

    public static FRotation create(FPoint axis, double angle) {

        return new FRotationDefault(axis, angle);
    }

    public static FRotation create(FVector axis, double angle) {

        return new FRotationDefault(axis, angle);
    }

    public static FRotation parse(String json) {
        JSONArray structure = (new JSONObject(json)).getJSONArray("rotor");

        double re = structure.getDouble(0);
        double i = structure.getDouble(1);
        double j = structure.getDouble(2);
        double k = structure.getDouble(3);

        return new FRotationDefault(re, i, j, k);
    }

    @Override
    public int hashCode() {

        return getCore().hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FRotation) {
            FRotation fRotation = (FRotation) object;

            return getCore().isExact(fRotation.getCore());
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    private void initializeCore(FPoint axis, double angle) {

        axis.normalize().mul(Math.sin(angle * 0.5));
        core.set(Math.cos(angle * 0.5), axis.getX(), axis.getY(), axis.getZ());
    }

    private void initializeRotor() {

        rotation[0][0] = 1 - (2 * core.getJ() * core.getJ()) - (2 * core.getK() * core.getK());
        rotation[0][1] = 2 * ((core.getI() * core.getJ()) + (core.getRe() * core.getK()));
        rotation[0][2] = 2 * ((core.getI() * core.getK()) - (core.getRe() * core.getJ()));
        rotation[1][0] = 2 * ((core.getI() * core.getJ()) - (core.getRe() * core.getK()));
        rotation[1][1] = 1 - (2 * core.getI() * core.getI()) - (2 * core.getK() * core.getK());
        rotation[1][2] = 2 * ((core.getJ() * core.getK()) + (core.getRe() * core.getI()));
        rotation[2][0] = 2 * ((core.getI() * core.getK()) + (core.getRe() * core.getJ()));
        rotation[2][1] = 2 * ((core.getJ() * core.getK()) - (core.getRe() * core.getI()));
        rotation[2][2] = 1 - (2 * core.getI() * core.getI()) - (2 * core.getJ() * core.getJ());
    }

    @Override
    public FVector getRotationAxis() {
        double factor = 1 / Math.sqrt(1 - (core.getRe() * core.getRe()));

        FPoint head = factory.getFPoint(core.getI(), core.getJ(), core.getK()).mul(factor).add(offset);
        FVector axis = factory.getFVector(offset.copy(), head);

        return axis;
    }

    @Override
    public double getRotationAngle() {

        if (core.getRe() <= -1) {
            return Math.PI * 2;
        }

        if (core.getRe() >= 1) {
            return 0;
        }

        return Math.acos(core.getRe()) * 2;
    }

    @Override
    public FQuaternion getCore() {

        return core.copy();
    }

    @Override
    public Consumer<Engine> rotate() {

        return (e) -> e.disassemble().forEach(p -> p
                .sub(offset)
                .set(
                        (rotation[0][0] * p.getX()) + (rotation[0][1] * p.getY()) + (rotation[0][2] * p.getZ()),
                        (rotation[1][0] * p.getX()) + (rotation[1][1] * p.getY()) + (rotation[1][2] * p.getZ()),
                        (rotation[2][0] * p.getX()) + (rotation[2][1] * p.getY()) + (rotation[2][2] * p.getZ())
                )
                .add(offset));
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append("rotor", core.getRe());
        json.append("rotor", core.getI());
        json.append("rotor", core.getJ());
        json.append("rotor", core.getK());

        return json;
    }

}
