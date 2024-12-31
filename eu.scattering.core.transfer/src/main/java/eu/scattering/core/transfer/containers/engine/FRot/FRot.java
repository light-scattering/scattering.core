package eu.scattering.core.transfer.containers.engine.FRot;

import eu.scattering.core.transfer.containers.ContainerFactory;
import eu.scattering.core.transfer.containers.ContainerFactoryConcrete;
import eu.scattering.core.transfer.containers.engine.Engine;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import eu.scattering.core.transfer.enums.FRotationEngine;
import org.json.JSONArray;
import org.json.JSONObject;

public class FRot implements Engine<FRot> {
    private static ContainerFactory factory = ContainerFactoryConcrete.create();
    private static final String JSON_TAG = "rotEng";

    private final FRotationEngine rotEngine;

    private final FPairPos3D rotAxis;
    private final double rotAngle;

    private final FPos4D rotQuaternionCore;

    private FRot(FRotationEngine engine, FPairPos3D rotAxis, double rotAngle, FPos4D rotCore) {

        this.rotEngine = engine;
        this.rotAxis = rotAxis;
        this.rotAngle = rotAngle;
        this.rotQuaternionCore = rotCore;
    }

    protected static FRot create(FRotationEngine engine, FPairPos3D rotAxis, double rotAngle, FPos4D rotCore) {

        return new FRot(engine, rotAxis, rotAngle, rotCore);
    }

    protected static FRot create(JSONObject json) {
        JSONArray structure = json.getJSONArray(JSON_TAG);

        FRotationEngine rotEngine = FRotationEngine.valueOf(structure.getJSONObject(0).toString());
        FPairPos3D rotAxis = factory.getFPairPos3D(structure.getJSONObject(1));
        double rotAngle = structure.getDouble(2);
        FPos4D rotQuaternionCore = factory.getFPos4D(structure.getJSONObject(3));

        return new FRot(rotEngine, rotAxis, rotAngle, rotQuaternionCore);
    }

    public FRotationEngine getEngineType() {
        return rotEngine;
    }

    public double getAngle() {
        return rotAngle;
    }

    public FPairPos3D getAxis() {
        return rotAxis;
    }

    public FPos4D getQuaternionCore() {
        return rotQuaternionCore;
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append(JSON_TAG, getEngineType());
        json.append(JSON_TAG, getAxis().exportToJSON());
        json.append(JSON_TAG, getAngle());
        json.append(JSON_TAG, getQuaternionCore().exportToJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        double hashCode = 7;

        hashCode = 31 * hashCode + getEngineType().hashCode();
        hashCode = 31 * hashCode + getAxis().hashCode();
        hashCode = 31 * hashCode + getAngle();

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FRot) {
            FRot fRot = (FRot) object;

            return getEngineType().equals(fRot.getEngineType()) &&
                    getAxis().equals(fRot.getAxis()) &&
                    getAngle() == fRot.getAngle();
        }

        return false;
    }

    @Override
    public String toString() {

        return exportToJSON().toString();
    }
}
