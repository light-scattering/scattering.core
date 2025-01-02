package eu.scattering.core.transfer.containers.engine.FRot;

import eu.scattering.core.transfer.containers.ContainerFactory;
import eu.scattering.core.transfer.containers.ContainerFactoryConcrete;
import eu.scattering.core.transfer.containers.engine.Engine;
import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

import static eu.scattering.core.transfer.configurations.NameConfiguration.JSON_TYPE;

public class FRot implements Engine<FRot> {
    private static ContainerFactory factory = ContainerFactoryConcrete.create();
    private static final String JSON_TAG = "engRot";
    private static final String JSON_AXIS = "axis";
    private static final String JSON_ANGLE = "angle";
    private static final String JSON_CORE_CODE = "core";
    private static final String JSON_CORE_MATRIX = "matrix";

    private final double rotAngle;
    private final FPairPos3D rotAxis;

    private final FPos4D rotCoreCode;
    private final FMatrix3x3D rotCoreMatrix;

    private FRot(FPairPos3D rotAxis, double rotAngle, FPos4D rotCoreCode, FMatrix3x3D rotCoreMatrix) {

        this.rotAxis = rotAxis;
        this.rotAngle = rotAngle;
        this.rotCoreCode = rotCoreCode;
        this.rotCoreMatrix = rotCoreMatrix;
    }

    protected static FRot create(FPairPos3D rotAxis, double rotAngle, FPos4D rotCoreCode, FMatrix3x3D rotCoreMatrix) {

        return new FRot(rotAxis, rotAngle, rotCoreCode, rotCoreMatrix);
    }

    protected static FRot create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_TAG) {
           throw new IllegalArgumentException("The object type is incorrect");
        }

        var rotAxis = factory.getFPairPos3D(json.getJSONObject(JSON_AXIS));
        var rotAngle = json.getDouble(JSON_ANGLE);
        var rotCoreCode = factory.getFPos4D(json.getJSONObject(JSON_CORE_CODE));
        var rotCoreMatrix = factory.getFMatrix3x3D(json.getJSONObject(JSON_CORE_MATRIX));

        return new FRot(rotAxis, rotAngle, rotCoreCode, rotCoreMatrix);
    }

    public double getAngle() {
        return rotAngle;
    }

    public FPairPos3D getAxis() {
        return rotAxis;
    }

    public FPos4D getCoreCode() {
        return rotCoreCode;
    }

    public FMatrix3x3D getCoreMatrix() {
        return rotCoreMatrix;
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_TAG);
        json.put(JSON_AXIS, getAxis().exportToJSON());
        json.put(JSON_CORE_CODE, getCoreCode().exportToJSON());
        json.put(JSON_ANGLE, getAngle());
        json.put(JSON_CORE_MATRIX, getCoreMatrix().exportToJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + getAxis().hashCode();
        hashCode = 31 * hashCode + getCoreCode().hashCode();
        hashCode = 31 * hashCode + getAngle();
        hashCode = 31 * hashCode + getCoreMatrix().hashCode();

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
