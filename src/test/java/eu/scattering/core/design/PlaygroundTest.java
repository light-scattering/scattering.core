package eu.scattering.core.design;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.Set;

import static eu.scattering.core.Configuration.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("Playground")
public class PlaygroundTest {

    @Test
    void playground1() {

//        System.out.println(mainFactory.getFPosition(1,2 , 3).toString());

       FVector fVector = factory.getFVector(0, 1, 0);
       FPlane fPlane = factory.getFPlane(fVector);
       FPoint fPoint = factory.getFPoint(1, 2, 3).devSetStatisticsEnabled(true);

       fPoint.ext(fPlane.reflect()).ext(fPlane.setDistance(10)).ext(fPlane.project()).trans(e -> e.set(1, 2, 3));

//       fVector.devDescribeClassStatistics();
//       fPlane.devDescribeClassStatistics();
        fPoint.devDesc();
       fPoint.devDescClassStatistics();
       fPoint.devDescStatistics();
       factory.getFPoint().devDescStatistics();
       fPoint.devDescNumberOfInstances();

    }
    
    @Test
    void playground2() {
        FPoint fPointA = factory.getFPoint(1, 2, 3);
        FPoint fPointB = factory.getFPoint(1, 2, 3);
        FPoint fPointC = factory.getFPoint(1, 2, 3);
        
        assertTrue(fPointA.isExact(fPointB));
        assertNotSame(fPointA, fPointB);

        Set<FPoint> set = new HashSet<>();
        set.add(fPointA);
        set.add(fPointB);
        set.add(fPointC);
        set.add(fPointB);
        System.out.println(set.size());

    }


}
