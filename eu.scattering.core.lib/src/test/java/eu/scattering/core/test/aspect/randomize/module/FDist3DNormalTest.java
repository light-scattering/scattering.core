package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3D;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(5)
@DisplayName("FDist3DNormalTest")
public class FDist3DNormalTest {

    @Test
    @DisplayName("Use configuration - Default")
    void useConfigurationDefault() {
        var random = factory.random();
        FRandDist3D dist = random.dist3D().normal();

        FStat d0Axis = factory.getFStat();
        FStat d1Axis = factory.getFStat();
        FStat d2Axis = factory.getFStat();

        FPos3D dir = factory.random().engine().nextDoubleOnSphere(1);
        FStat dirAxis = factory.getFStat();

        double[] valB = new double[3];
        for (int i = 0 ; i < 5000 ; i++) {
            FPos3D valA = dist.produce();

            d0Axis.add(valA.getD0());
            d1Axis.add(valA.getD1());
            d2Axis.add(valA.getD2());

            dirAxis.add(valA.getD0() * dir.getD0() + valA.getD1() * dir.getD1() + valA.getD2() * dir.getD2());

            dist.produce(valB);

            d0Axis.add(valB[0]);
            d1Axis.add(valB[1]);
            d2Axis.add(valB[2]);

            dirAxis.add(valB[0] * dir.getD0() + valB[1] * dir.getD1() + valB[2] * dir.getD2());
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

        avg = d2Axis.mean();
        std = d2Axis.std(true);

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
        var random = factory.random();
        FRandDist3D dist = random.dist3D().normal()
                .setAvg(-1, 1, 10)
                .setStd(0.5, 0.1, 2)
                .setCorD01(0.5)
                .setCorD02(-0.4)
                .setCorD12(0.3);


        FStat d0Axis = factory.getFStat();
        FStat d1Axis = factory.getFStat();
        FStat d2Axis = factory.getFStat();

        double[] valB = new double[3];
        for (int i = 0 ; i < 10000 ; i++) {
            FPos3D valA = dist.produce();

            d0Axis.add(valA.getD0());
            d1Axis.add(valA.getD1());
            d2Axis.add(valA.getD2());

            dist.produce(valB);

            d0Axis.add(valB[0]);
            d1Axis.add(valB[1]);
            d2Axis.add(valB[2]);
        }

        double avg, std;

        avg = d0Axis.mean();
        std = d0Axis.std(true);

        assertEquals(-1, avg, 0.05);
        assertEquals(0.5, std, 0.05);

        avg = d1Axis.mean();
        std = d1Axis.std(true);

        assertEquals(1, avg, 0.05);
        assertEquals(0.1, std, 0.05);

        avg = d2Axis.mean();
        std = d2Axis.std(true);

        assertEquals(10, avg, 0.05);
        assertEquals(2, std, 0.05);

        double corD01 = d0Axis.correlation(d1Axis);
        double corD02 = d0Axis.correlation(d2Axis);
        double corD12 = d1Axis.correlation(d2Axis);

        assertEquals(0.5, corD01, 0.05);
        assertEquals(-0.4, corD02, 0.05);
        assertEquals(0.3, corD12, 0.05);
    }

    @Test
    @DisplayName("Use configuration - Erroneous")
    void useConfigurationErroneous() {
        var random = factory.random();
        FRandDist3D dist = random.dist3D().normal()
                .setCorD01(0.5)
                .setCorD02(-0.7)
                .setCorD12(0.9);

        assertThrows(IllegalStateException.class, dist::produce);
    }
}
