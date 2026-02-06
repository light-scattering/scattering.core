package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import org.json.JSONObject;

public class FSphereParserDef implements GeometryParser {
    private static final String JSON_TYPE = "type";

    private static GeometryParser self;

    private final FSphereFactory factory;
    private final GeometryParser chain;

    private FSphereParserDef(FSphereFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(FSphereFactory factorySelf, GeometryParser chain) {

        if (FSphereParserDef.self == null) {
            FSphereParserDef.self = new FSphereParserDef(factorySelf, chain);
        }

        return FSphereParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FSphereDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFSphere().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
