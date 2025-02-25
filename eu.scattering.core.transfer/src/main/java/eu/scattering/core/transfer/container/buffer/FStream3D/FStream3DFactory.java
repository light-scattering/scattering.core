package eu.scattering.core.transfer.container.buffer.FStream3D;

import org.json.JSONObject;

public interface FStream3DFactory {

    default FStream3D getFStream3D(int length) {

        return FStream3D.create(length);
    }

    default FStream3D getFStream3D(JSONObject json) {

        return FStream3D.create(json);
    }
}
