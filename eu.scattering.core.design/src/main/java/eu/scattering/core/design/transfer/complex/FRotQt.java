package eu.scattering.core.design.transfer.complex;

import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.matrix.variants.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos4D;
import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONObject;

import java.util.Objects;

public class FRotQt implements Transfer {
    private static final String JSON_TYPE = "type";
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

    public static FRotQt create(FPos4D qt, FPos3D offset, FMatrix3x3D matrix) {

        return new FRotQt(qt, offset, matrix);
    }

    public static FRotQt create(TransferFactory factoryExt, JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
           throw new IllegalArgumentException("The object type is incorrect");
        }

        FPos4D qt = factoryExt.getFPos4D(json.getJSONObject(JSON_QUATERNION));
        FPos3D offset = factoryExt.getFPos3D(json.getJSONObject(JSON_OFFSET));
        FMatrix3x3D matrix = factoryExt.getFMatrix3x3D(json.getJSONObject(JSON_MATRIX));

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

        if (object instanceof FRotQt fRotQt) {

            return getQuaternion().equals(fRotQt.getQuaternion());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
