package eu.scattering.core.design;

import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {

       FPoint fPoint = MainFactory.getFPoint();
       fPoint.set(1, 1, 1);
       fPoint.setX(3);

        FPoint fPoint2 = MainFactory.getFPoint();
        fPoint2.set(1, 1, 1);
        fPoint2.reflect();
        fPoint2.normalize();
        fPoint2.setX(3);

        fPoint.devDescribeStats();
        fPoint.devDescribeClassStats();

        System.out.println(fPoint2.devGetStats().get().getMethod("setX(double)"));
    }
}
