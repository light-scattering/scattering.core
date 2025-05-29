package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FPointParserDef implements GeometryParser {

    private static GeometryParser self;

    private final FPointFactory factory;
    private final GeometryParser chain;

    private FPointParserDef(FPointFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(FPointFactory factorySelf, GeometryParser chain) {

        if (FPointParserDef.self == null) {
            FPointParserDef.self = new FPointParserDef(factorySelf, chain);
        }

        return FPointParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FPointDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFPoint().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
