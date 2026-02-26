package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.container.ContainerFactory;
import org.json.JSONObject;

public class FAssemblyParserDef implements GeometryParser {
    private static final String JSON_TYPE = "type";

    private static GeometryParser self;

    private final ContainerFactory factory;
    private final GeometryParser chain;

    private FAssemblyParserDef(ContainerFactory factory, GeometryParser chain) {

        this.factory = factory;
        this.chain = chain;
    }

    public static GeometryParser create(ContainerFactory factorySelf, GeometryParser chain) {

        if (FAssemblyParserDef.self == null) {
            FAssemblyParserDef.self = new FAssemblyParserDef(factorySelf, chain);
        }

        return FAssemblyParserDef.self;
    }

    private boolean isParsable(String tag) {

        return FAssemblyDef.isParsable(tag);
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        if (tag == null) {
            tag = json.getString(JSON_TYPE);
        }

        if (isParsable(tag)) {
            return this.factory.getFAssembly().set(json);
        }

        if (chain != null) {
            return chain.parse(json, tag);
        }

        throw new IllegalArgumentException("The JSON file cannot be parsed");
    }
}
