package eu.scattering.core.test.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.normal.FDist1DNormal;
import eu.scattering.core.design.statistics.base.FStat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FDist1NormalTest")
public class FDist1DNormalTest {

    @Test
    @DisplayName("Construct with parameters")
    void constructWithParameters() {
        double mean = 10;
        double std = 1;

        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DNormal(mean, std);

        FStat fStat = factory.getFStat();

        for (int i = 0 ; i < 2500 ; i++) {
            fStat.add(dist.produce());
        }

        double fStatMean = fStat.mean();
        assertEquals(mean, fStatMean, 1E-1);
        assertEquals(std, fStat.std(true), 1E-1);
    }

    @Test
    @DisplayName("Construct with parameters - Erroneous standard deviation")
    void constructWithParametersErroneousStd() {
        FRandGenerator random = factory.getFRand();

        assertThrows(IllegalArgumentException.class, () -> random.getFDist1DNormal(10, -1));
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DNormal(10, 1);

        double[] arr = new double[1];

        dist.produce(arr);

        assertTrue(arr[0] > 0);
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DNormal(10, 1);

        double[] arr = new double[0];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr));
    }

    @Test
    @DisplayName("Set minimum cut-off")
    void setMinimumCutOff() {
        double mean = 10;
        double std = 1;

        FRandGenerator random = factory.getFRand();
        FDist1DNormal dist = random.getFDist1DNormal(mean, std);

        dist.setCutoffMin(mean);

        assertEquals(mean, dist.getCutoffMin());

        for (int i = 0 ; i < 10 ; i++) {
            assertTrue(dist.produce() > mean);
        }
    }

    @Test
    @DisplayName("Set minimum cut-off - Failure")
    void setMinimumCutOffFailure() {
        double mean = 10;
        double std = 1;

        FRandGenerator random = factory.getFRand();
        FDist1DNormal dist = random.getFDist1DNormal(mean, std);

        dist.setCutoffMax(5);

        assertThrows(IllegalArgumentException.class, () -> dist.setCutoffMin(mean));
    }

    @Test
    @DisplayName("Set maximum cut-off")
    void setMaximumCutOff() {
        double mean = 10;
        double std = 1;

        FRandGenerator random = factory.getFRand();
        FDist1DNormal dist = random.getFDist1DNormal(mean, std);

        dist.setCutoffMax(mean);

        assertEquals(mean, dist.getCutoffMax());

        for (int i = 0 ; i < 10 ; i++) {
            assertTrue(dist.produce() < mean);
        }
    }

    @Test
    @DisplayName("Set maximum cut-off - Failure")
    void setMaximumCutOffFailure() {
        double mean = 10;
        double std = 1;

        FRandGenerator random = factory.getFRand();
        FDist1DNormal dist = random.getFDist1DNormal(mean, std);

        dist.setCutoffMin(15);

        assertThrows(IllegalArgumentException.class, () -> dist.setCutoffMax(mean));
    }
}
