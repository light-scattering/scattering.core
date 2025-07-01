package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.array.concrete.FArrayMeshDef;
import org.json.JSONObject;

public interface FArrayMeshFactory {

    default FArrayMesh getFArrayMesh(int length) {

        return FArrayMeshDef.create(length);
    }

    default FArrayMesh getFArrayMesh(JSONObject json) {

        return FArrayMeshDef.create(json);
    }
}
