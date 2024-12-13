package eu.scattering.core.design.elements.data.position;

import org.json.JSONObject;

public interface FTuplePos3DFactory {

    default FTuplePos3D getFTuplePos3D(FPos3D posA, FPos3D posB) {
        return FTuplePos3D.create(posA, posB);
    }

    default FTuplePos3D getFTuplePos3D(FPos3DI posA, FPos3DI posB) {
        return getFTuplePos3D(posA.toDouble(), posB.toDouble());
    }

    default FTuplePos3D getFTuplePos3D(FPos3D posA, FPos3DI posB) {
        return getFTuplePos3D(posA, posB.toDouble());
    }

    default FTuplePos3D getFTuplePos3D(FPos3DI posA, FPos3D posB) {
        return getFTuplePos3D(posA.toDouble(), posB);
    }

    //--------------------------------------------------

    default FTuplePos3D getFTuplePos3D(String text) {
        return FTuplePos3D.create(text);
    }

    default FTuplePos3D getFTuplePos3D(JSONObject json) {
        return FTuplePos3D.create(json);
    }
}
