package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.array.concrete.FArrayDef;
import org.json.JSONObject;

public interface FArrayFactory {

    default FArray getFArray(int length) {

        return FArrayDef.create(length);
    }

    default FArray getFArray(JSONObject json) {

        return FArrayDef.create(json);
    }
}
