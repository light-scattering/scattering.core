package eu.scattering.core.design;

import eu.scattering.core.injection.EngineFactory;
import eu.scattering.core.design.engine.base.point.FPoint;
import org.junit.jupiter.api.Test;

public class PlaygroundTest {

    @Test
    void playground() {

       FPoint fPoint = EngineFactory.getFPoint();
       fPoint.set(1, 1, 1);
       fPoint.setX(3);

        FPoint fPoint2 = EngineFactory.getFPoint();
        fPoint2.set(1, 1, 1);
        fPoint2.reflect();
        fPoint2.normalize();
        fPoint2.setX(3);

        fPoint.devDescribeStats();
        fPoint.devDescribeClassStats();

        System.out.println(fPoint2.devGetStats().get().getMethod("setX(double)"));
    }
}
