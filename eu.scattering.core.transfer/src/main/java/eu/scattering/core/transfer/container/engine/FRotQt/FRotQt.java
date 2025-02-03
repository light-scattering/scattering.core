package eu.scattering.core.transfer.container.engine.FRotQt;

import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.position.FPos4D.FPos4D;
import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.engine.Engine;
import eu.scattering.core.transfer.container.storage.FMatrix3x3D.FMatrix3x3D;
import org.json.JSONObject;

import java.util.Objects;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FRotQt implements Engine<FRotQt> {
    private static final ContainerFactory factory = ContainerFactoryConcrete.create();
    private static final String JSON_MAIN = "engRotQt";
    private static final String JSON_OFFSET = "offset";
    private static final String JSON_QUATERNION = "qt";
    private static final String JSON_MATRIX = "matrix";

    private final FPos4D qt;
    private final FPos3D offset;
    private final FMatrix3x3D matrix;

    private FRotQt(FPos4D qt, FPos3D offset, FMatrix3x3D matrix) {

        this.qt = qt;
        this.offset = offset;
        this.matrix = matrix;
    }

    protected static FRotQt create(FPos4D qt, FPos3D offset, FMatrix3x3D matrix) {

        return new FRotQt(qt, offset, matrix);
    }

    protected static FRotQt create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
           throw new IllegalArgumentException("The object type is incorrect");
        }

        FPos4D qt = factory.getFPos4D(json.getJSONObject(JSON_QUATERNION));
        FPos3D offset = factory.getFPos3D(json.getJSONObject(JSON_OFFSET));
        FMatrix3x3D matrix = factory.getFMatrix3x3D(json.getJSONObject(JSON_MATRIX));

        return new FRotQt(qt, offset, matrix);
    }

    public FPos3D getOffset() {

        return this.offset;
    }

    public FPos4D getQuaternion() {

        return this.qt;
    }

    public FMatrix3x3D getMatrix() {

        return this.matrix;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_OFFSET, getOffset().toJSON());
        json.put(JSON_QUATERNION, getQuaternion().toJSON());
        json.put(JSON_MATRIX, getMatrix().toJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getOffset(), getQuaternion(), getMatrix());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FRotQt) {
            FRotQt fRot = (FRotQt) object;

            return getQuaternion().equals(fRot.getQuaternion());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
