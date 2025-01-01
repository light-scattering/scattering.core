package eu.scattering.core.transfer.containers.engine.FRot;

import eu.scattering.core.transfer.containers.ContainerFactory;
import eu.scattering.core.transfer.containers.ContainerFactoryConcrete;
import eu.scattering.core.transfer.containers.engine.Engine;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FRot implements Engine<FRot> {
    private static ContainerFactory factory = ContainerFactoryConcrete.create();
    private static final String JSON_TAG = "engRot";
    private static final String JSON_AXIS = "axis";
    private static final String JSON_CORE = "core";
    private static final String JSON_ANGLE = "angle";

    private final FPairPos3D rotAxis;
    private final double rotAngle;

    private final FPos4D rotCore;

    private FRot(FPairPos3D rotAxis, double rotAngle, FPos4D rotCore) {

        this.rotAxis = rotAxis;
        this.rotAngle = rotAngle;
        this.rotCore = rotCore;
    }

    protected static FRot create(FPairPos3D rotAxis, double rotAngle, FPos4D rotCore) {

        return new FRot(rotAxis, rotAngle, rotCore);
    }

    protected static FRot create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
           throw new IllegalArgumentException("The object type is incorrect");
        }

        var rotAxis = factory.getFPairPos3D(json.getJSONObject(JSON_AXIS));
        var rotAngle = json.getDouble(JSON_ANGLE);
        var rotQuaternionCore = factory.getFPos4D(json.getJSONObject(JSON_CORE));

        return new FRot(rotAxis, rotAngle, rotQuaternionCore);
    }

    public double getAngle() {
        return rotAngle;
    }

    public FPairPos3D getAxis() {
        return rotAxis;
    }

    public FPos4D getQuaternionCore() {
        return rotCore;
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_TAG);
        json.put(JSON_AXIS, getAxis().exportToJSON());
        json.put(JSON_CORE, getQuaternionCore().exportToJSON());
        json.put(JSON_ANGLE, getAngle());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + getAxis().hashCode();
        hashCode = 31 * hashCode + getQuaternionCore().hashCode();
        hashCode = 31 * hashCode + getAngle();

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FRot) {
            FRot fRot = (FRot) object;

            return getAxis().equals(fRot.getAxis()) && getAngle() == fRot.getAngle();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
