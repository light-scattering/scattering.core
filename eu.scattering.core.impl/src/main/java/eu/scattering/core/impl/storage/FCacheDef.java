package eu.scattering.core.impl.storage;

import eu.scattering.core.design.storage.cache.FCache;
import org.json.JSONObject;

public class FCacheDef {
    private static final String JSON_TYPE = "type";

    public static FCache create(boolean multi) {

        if (multi) {
            return FCacheMultiDef.create();
        }

        return FCacheMonoDef.create();
    }

    public static FCache create(JSONObject json) {

        if (json.get(JSON_TYPE) == FCacheMonoDef.JSON_MAIN) {
            return FCacheMonoDef.create();
        }

        if (json.get(JSON_TYPE) == FCacheMultiDef.JSON_MAIN) {
            return FCacheMultiDef.create();
        }

        throw new IllegalArgumentException("The object type is incorrect");
    }
}
