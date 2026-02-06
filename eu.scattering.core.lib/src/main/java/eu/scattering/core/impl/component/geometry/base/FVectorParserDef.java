package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.vector.FVectorFactory;
import org.json.JSONObject;

public class FVectorParserDef implements GeometryParser {
    private static final String JSON_TYPE = "type";

    private static GeometryParser self;

    private final FVectorFactory factory;
    private final GeometryParser chain;

    private FVectorParserDef(FVectorFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(FVectorFactory factorySelf, GeometryParser chain) {

        if (FVectorParserDef.self == null) {
            FVectorParserDef.self = new FVectorParserDef(factorySelf, chain);
        }

        return FVectorParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FVectorDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFVector().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
