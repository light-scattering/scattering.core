package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftFactory;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FDraftParserDef implements GeometryParser {

    private static GeometryParser self;

    private final FDraftFactory factory;
    private final GeometryParser chain;

    private FDraftParserDef(FDraftFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(FDraftFactory factorySelf, GeometryParser chain) {

        if (FDraftParserDef.self == null) {
            FDraftParserDef.self = new FDraftParserDef(factorySelf, chain);
        }

        return FDraftParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FDraftDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFDraft().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
