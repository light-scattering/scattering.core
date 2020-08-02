package eu.scattering.core;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.point.impl.dec.FPointDev;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {
       IFPoint fPoint = FactoryGeometry.getIFPoint();
       fPoint.set(1, 1, 1);
       fPoint.reflect();
       fPoint.normalize();
       fPoint.setX(3);

        fPoint.devDescribeStats();
    }
}
