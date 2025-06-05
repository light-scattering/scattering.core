package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneFactory;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FPlaneParserDef implements GeometryParser {

    private static GeometryParser self;

    private final FPlaneFactory factory;
    private final GeometryParser chain;

    private FPlaneParserDef(FPlaneFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(FPlaneFactory factorySelf, GeometryParser chain) {

        if (FPlaneParserDef.self == null) {
            FPlaneParserDef.self = new FPlaneParserDef(factorySelf, chain);
        }

        return FPlaneParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FPlaneDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFPlane().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
