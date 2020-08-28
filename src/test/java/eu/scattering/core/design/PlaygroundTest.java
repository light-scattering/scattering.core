package eu.scattering.core.design;

import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(5)
@DisplayName("Playground")
public class PlaygroundTest {

    @Test
    void playground() {

        System.out.println(MainFactory.getFDipole(1,2 , 3).toString());

       FVector fVector = MainFactory.getFVector(0, 1, 0);
       FPlane fPlane = MainFactory.getFPlane(fVector);
       FPoint fPoint = MainFactory.getFPoint(1, 2, 3);

       fPoint.ext(fPlane.reflect()).ext(fPlane.setDistance(10)).ext(fPlane.project()).trans(e -> e.set(1, 2, 3));

       fVector.devDescribeClassStatistics();
       fPlane.devDescribeClassStatistics();
       fPoint.devDescribeClassStatistics();
        System.out.println(fPoint.devGetNumberOfInstances().get());
    }
}
