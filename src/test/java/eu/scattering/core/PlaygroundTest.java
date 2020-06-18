package eu.scattering.core;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {
        FactoryGeometry.getIFPoint().set(1, 2, 3).devDescribe("uga").set(3, 2, 1).devDescribe();
        FactoryGeometry.getIFVector().devDescribe();

        IFPoint store = FactoryGeometry.getIFPoint();
        FactoryGeometry.getIFPoint(1, 2, 3).devStore(store).set(4, 5, 6).devDescribe();
        store.devDescribe();
    }
}
