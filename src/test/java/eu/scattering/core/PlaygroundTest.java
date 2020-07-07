package eu.scattering.core;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {
       IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1);
       IFLine fLine = FactoryGeometry.getIFLine(fVector);
       System.out.println(fVector.getLength());
       System.out.println(fLine.getIFPoint(1));
    }
}
