package eu.scattering.core.design.core.data.tuplePos3D;

import eu.scattering.core.design.core.data.pos3D.FPos3D;
import eu.scattering.core.design.core.data.pos3DI.FPos3DI;
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
