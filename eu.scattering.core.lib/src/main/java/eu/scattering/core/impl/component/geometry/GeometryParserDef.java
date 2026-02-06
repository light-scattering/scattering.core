package eu.scattering.core.impl.component.geometry;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.impl.component.geometry.base.FPointParserDef;
import eu.scattering.core.impl.component.geometry.base.FVectorParserDef;
import eu.scattering.core.impl.component.geometry.construct.*;
import eu.scattering.core.impl.component.geometry.container.FAssemblyParserDef;
import eu.scattering.core.impl.component.geometry.shape.FSphereParserDef;
import org.json.JSONObject;

public class GeometryParserDef implements GeometryParser{

    private static GeometryParser self;

    private final GeometryParser init;

    private GeometryParserDef(GeometryFactory factory) {

        GeometryParser fPointParser = FPointParserDef.create(factory, null);
        GeometryParser fVectorParser = FVectorParserDef.create(factory, fPointParser);
        GeometryParser fLineParser = FLineParserDef.create(factory, fVectorParser);
        GeometryParser fPlaneParser = FPlaneParserDef.create(factory, fLineParser);
        GeometryParser fRayParser = FRayParserDef.create(factory, fPlaneParser);
        GeometryParser fSegmentParser = FSegmentParserDef.create(factory, fRayParser);
        GeometryParser fDraftParser = FDraftParserDef.create(factory, fSegmentParser);
        GeometryParser fSphereParser = FSphereParserDef.create(factory, fDraftParser);
        GeometryParser fAssemblyParser = FAssemblyParserDef.create(factory, fSphereParser);

        this.init = fAssemblyParser;
    }

    public static GeometryParser get(GeometryFactory factory) {

        if (GeometryParserDef.self == null) {
            GeometryParserDef.self = new GeometryParserDef(factory);
        }

        return GeometryParserDef.self;
    }

    @Override
    public Geometry parse(JSONObject json, String tag) {

        return this.init.parse(json, tag);
    }
}
