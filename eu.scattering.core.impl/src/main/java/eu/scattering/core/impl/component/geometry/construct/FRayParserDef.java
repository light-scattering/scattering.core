package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FRayParserDef implements GeometryParser {

    private static GeometryParser self;

    private final ConstructFactory factory;
    private final GeometryParser chain;

    private FRayParserDef(ConstructFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(ConstructFactory factorySelf, GeometryParser chain) {

        if (FRayParserDef.self == null) {
            FRayParserDef.self = new FRayParserDef(factorySelf, chain);
        }

        return FRayParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FRayDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFRay().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
