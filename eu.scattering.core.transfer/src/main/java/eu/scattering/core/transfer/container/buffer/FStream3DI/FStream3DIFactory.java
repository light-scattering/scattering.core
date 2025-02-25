package eu.scattering.core.transfer.container.buffer.FStream3DI;

import org.json.JSONObject;

public interface FStream3DIFactory {

    default FStream3DI getFStream3DI(int length) {

        return FStream3DI.create(length);
    }

    default FStream3DI getFStream3DI(JSONObject json) {

        return FStream3DI.create(json);
    }
}
