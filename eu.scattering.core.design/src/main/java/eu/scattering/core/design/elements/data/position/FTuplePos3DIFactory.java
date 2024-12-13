package eu.scattering.core.design.elements.data.position;

import org.json.JSONObject;

public interface FTuplePos3DIFactory {

    default FTuplePos3DI getFTuplePos3DI(FPos3DI posA, FPos3DI posB) {
        return FTuplePos3DI.create(posA, posB);
    }

    //--------------------------------------------------

    default FTuplePos3DI getFTuplePos3DI(String text) {
        return FTuplePos3DI.create(text);
    }

    default FTuplePos3DI getFTuplePos3DI(JSONObject json) {
        return FTuplePos3DI.create(json);
    }
}
