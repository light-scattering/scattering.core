package eu.scattering.core;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.support.line.IFLine;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {
        IFPoint fPoint = FactoryGeometry.getIFPoint();
        IFLine fLine = FactoryGeometry.getIFLine();

        System.out.println(fPoint.devDescribe().extNumber(fLine.getDistance()));
    }
}
