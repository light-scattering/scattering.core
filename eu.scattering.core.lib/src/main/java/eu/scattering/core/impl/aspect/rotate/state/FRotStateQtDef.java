package eu.scattering.core.impl.aspect.rotate.state;

import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import org.json.JSONObject;

import java.util.Objects;

public class FRotStateQtDef implements FRotState {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "engRotQt";
    private static final String JSON_OFFSET = "offset";
    private static final String JSON_QUATERNION = "qt";
    private static final String JSON_MATRIX = "matrix";

    private final TransferFactory factory;

    private final FPos3D offset;
    private final FPos4D quaternion;
    private final FMatrix3x3D matrix;

    private FRotStateQtDef(TransferFactory factory, FPos4D quaternion, FPos3D offset, FMatrix3x3D matrix) {

        this.factory = factory;

        this.offset = offset;
        this.quaternion = quaternion;
        this.matrix = matrix;
    }

    public static FRotState create(TransferFactory factory, FPos4D quaternion, FPos3D offset, FMatrix3x3D matrix) {

        return new FRotStateQtDef(factory, quaternion, offset, matrix);
    }

    public static FRotState create(TransferFactory factory, JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FPos3D offset = factory.getFPos3D(json.getJSONObject(JSON_OFFSET));
        FPos4D quaternion = factory.getFPos4D(json.getJSONObject(JSON_QUATERNION));
        FMatrix3x3D matrix = factory.getFMatrix3x3D(json.getJSONObject(JSON_MATRIX));

        return new FRotStateQtDef(factory, quaternion, offset, matrix);
    }

    //--------------------------------------------------

    public FPos3D getOffset() {

        return this.offset;
    }

    public FPos4D getQuaternion() {

        return this.quaternion;
    }

    public FMatrix3x3D getMatrix() {

        return this.matrix;
    }

    @Override
    public double getAngle() {
        double re = this.quaternion.getD0();

        if (re <= -1) {
            return Math.PI * 2;
        }

        if (re >= 1) {
            return 0;
        }

        return Math.acos(re) * 2;
    }

    @Override
    public FPairPos3D getAxis() {
        double re = this.quaternion.getD0();

        double i = this.quaternion.getD1();
        double j = this.quaternion.getD2();
        double k = this.quaternion.getD3();

        double factor = 1 / Math.sqrt(1 - (re * re));

        FPos3D head = factory.getFPos3D(
                (i * factor) + this.offset.getD0(),
                (j * factor) + this.offset.getD1(),
                (k * factor) + this.offset.getD2()
        );

        return factory.getFPairPos3D(this.offset, head);
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

        if (object instanceof FRotState fRotQt) {

            return getQuaternion().equals(fRotQt.getQuaternion());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
