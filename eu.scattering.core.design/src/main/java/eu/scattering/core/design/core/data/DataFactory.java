package eu.scattering.core.design.core.data;

import eu.scattering.core.design.core.data.pos2D.FPos2DFactory;
import eu.scattering.core.design.core.data.pos2DI.FPos2DIFactory;
import eu.scattering.core.design.core.data.pos3D.FPos3DFactory;
import eu.scattering.core.design.core.data.pos3DI.FPos3DIFactory;
import eu.scattering.core.design.core.data.pos4D.FPos4DFactory;
import eu.scattering.core.design.core.data.pos4DI.FPos4DIFactory;
import eu.scattering.core.design.core.data.tuplePos3D.FTuplePos3DFactory;
import eu.scattering.core.design.core.data.tuplePos3DI.FTuplePos3DIFactory;

public interface DataFactory extends
        FPos2DIFactory, FPos3DIFactory, FPos4DIFactory,
        FPos2DFactory, FPos3DFactory, FPos4DFactory,
        FTuplePos3DFactory, FTuplePos3DIFactory {
}
