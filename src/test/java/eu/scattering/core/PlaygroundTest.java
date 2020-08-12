package eu.scattering.core;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {

        Configuration.setDevObjectStatsSuspended(false);
        Configuration.setDevEnabled(true);

       IFPoint fPoint = FactoryGeometry.getIFPoint();
       fPoint.set(1, 1, 1);
       fPoint.setX(3);

        IFPoint fPoint2 = FactoryGeometry.getIFPoint();
        fPoint2.set(1, 1, 1);
        fPoint2.reflect();
        fPoint2.normalize();
        fPoint2.setX(3);

        fPoint.devDescribeStats();
        fPoint.devDescribeClassStats();

        System.out.println(fPoint2.devGetStats().get().getMethod("setX(double)"));
    }
}
