package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.FDist2D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(5)
@DisplayName("FDist2DNormalTest")
public class FDist2DNormalTest {

    @Test
    @DisplayName("Use configuration - Default")
    void useConfigurationDefault() {
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DNormal();

        FStat d0Axis = factory.getFStat();
        FStat d1Axis = factory.getFStat();

        FPos2D dir = random.nextDoubleOnCircle(1);
        FStat dirAxis = factory.getFStat();

        double[] valB = new double[3];
        for (int i = 0 ; i < 5000 ; i++) {
            FPos2D valA = dist.produce();

            d0Axis.add(valA.getD0());
            d1Axis.add(valA.getD1());

            dirAxis.add(valA.getD0() * dir.getD0() + valA.getD1() * dir.getD1());

            dist.produce(valB);

            d0Axis.add(valB[0]);
            d1Axis.add(valB[1]);

            dirAxis.add(valB[0] * dir.getD0() + valB[1] * dir.getD1());
        }

        double avg, std;

        avg = d0Axis.mean();
        std = d0Axis.std(true);

        assertEquals(0, avg, 0.05);
        assertEquals(1, std, 0.05);

        avg = d1Axis.mean();
        std = d1Axis.std(true);

        assertEquals(0, avg, 0.05);
        assertEquals(1, std, 0.05);

        avg = dirAxis.mean();
        std = dirAxis.std(true);

        assertEquals(0, avg, 0.05);
        assertEquals(1, std, 0.05);
    }

    @Test
    @DisplayName("Use configuration - Custom")
    void useConfigurationCustom() {
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DNormal()
                .setAvg(-0.5, 1)
                .setStd(0.5, 0.1)
                .setCor(0.75);

        FStat d0Axis = factory.getFStat();
        FStat d1Axis = factory.getFStat();

        double[] valB = new double[3];
        for (int i = 0 ; i < 5000 ; i++) {
            FPos2D valA = dist.produce();

            d0Axis.add(valA.getD0());
            d1Axis.add(valA.getD1());

            dist.produce(valB);

            d0Axis.add(valB[0]);
            d1Axis.add(valB[1]);
        }

        double avg, std;

        avg = d0Axis.mean();
        std = d0Axis.std(true);

        assertEquals(-0.5, avg, 0.05);
        assertEquals(0.5, std, 0.05);

        avg = d1Axis.mean();
        std = d1Axis.std(true);

        assertEquals(1, avg, 0.05);
        assertEquals(0.1, std, 0.05);

        assertEquals(0.75, d0Axis.correlation(d1Axis), 0.05);
    }

    @Test
    @DisplayName("Use configuration - Erroneous")
    void useConfigurationErroneous() {
        FRandGenerator random = factory.getFRand();

        assertThrows(IllegalArgumentException.class, () -> random.getFDist2DNormal().setCor(-1.5));
    }
}
