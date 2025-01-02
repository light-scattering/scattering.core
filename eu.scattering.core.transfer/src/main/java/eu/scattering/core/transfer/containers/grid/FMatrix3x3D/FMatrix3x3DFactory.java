package eu.scattering.core.transfer.containers.grid.FMatrix3x3D;

import org.json.JSONObject;

public interface FMatrix3x3DFactory {

    default FMatrix3x3D getFMatrix3x3D(double[][] origin) {

        return FMatrix3x3D.create(origin);
    }

    default FMatrix3x3D getFMatrix3x3D(JSONObject json) {

        return FMatrix3x3D.create(json);
    }
}
