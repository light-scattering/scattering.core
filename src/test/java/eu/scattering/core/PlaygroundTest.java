package eu.scattering.core;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {
        FactoryGeometry.getIFPoint().devDescribe();
        FactoryGeometry.getIFVector().devDescribe();
        FactoryGeometry.getIFLine().devDescribe();
        FactoryGeometry.getIFPlane().devDescribe();

    }
}
