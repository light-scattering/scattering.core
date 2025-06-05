package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.construct.line.FLineFactory;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FLineParserDef implements GeometryParser {

    private static GeometryParser self;

    private final FLineFactory factory;
    private final GeometryParser chain;

    private FLineParserDef(FLineFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(FLineFactory factorySelf, GeometryParser chain) {

        if (FLineParserDef.self == null) {
            FLineParserDef.self = new FLineParserDef(factorySelf, chain);
        }

        return FLineParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FLineDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFLine().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
