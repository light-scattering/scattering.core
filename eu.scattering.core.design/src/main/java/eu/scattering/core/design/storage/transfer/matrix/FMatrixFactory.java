package eu.scattering.core.design.storage.transfer.matrix;

import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import org.json.JSONObject;

public interface FMatrixFactory {

    FMatrix3x3D getFMatrix3x3D(double[][] origin);

    FMatrix3x3D getFMatrix3x3D(JSONObject json);
}
