package eu.scattering.core.test.statistics;

import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FPlotBar")
public class FPlotBarTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FBarBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            assertEquals(0, fPlotBar.size());
        }

        @Test
        @DisplayName("Create with reference")
        void createWithReference() {
            FStat coreX = factory.getFStat();
            List<FStat> coreY = new ArrayList<>();

            FPlotBar fPlotBar = factory.getRefFPlotBar(coreX, coreY);

            assertEquals(0, fPlotBar.size());
            assertSame(coreX, fPlotBar.getRefCoreX());
            assertSame(coreY, fPlotBar.getRefCoreY());
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(2);
            fPlotBar.add(3);

            assertEquals(2, fPlotBar.size());
            assertEquals(0, fPlotBar.getY(0).size());
            assertEquals(0, fPlotBar.getY(1).size());

            assertThrows(IllegalStateException.class, () -> fPlotBar.add(2));
        }

        @Test
        @DisplayName("Add X Y")
        void addXY() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(2, 4);
            fPlotBar.add(3, 5);

            fPlotBar.add(2, 6);
            fPlotBar.add(2, 8);

            assertEquals(2, fPlotBar.size());
            assertEquals(3, fPlotBar.getY(0).size());
            assertEquals(1, fPlotBar.getY(1).size());
            assertEquals(4, fPlotBar.getY(0).get(0));
            assertEquals(6, fPlotBar.getY(0).get(1));
            assertEquals(8, fPlotBar.getY(0).get(2));
        }

        @Test
        @DisplayName("Add with FStat")
        void addWithFStat() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2, 3);
            FStat fStatB = factory.getFStat(4, 5);

            fPlotBar.add(2, fStatA);
            fPlotBar.add(3, fStatB);

            assertEquals(2, fPlotBar.size());
            assertEquals(3, fPlotBar.getY(0).size());
            assertEquals(2, fPlotBar.getY(1).size());
            assertNotSame(fStatA, fPlotBar.getY(0));
            assertNotSame(fStatB, fPlotBar.getY(1));

            FStat fStatC = factory.getFStat(6);

            assertThrows(IllegalStateException.class, () -> fPlotBar.add(2, fStatC));
        }

        @Test
        @DisplayName("Add with ref FStat")
        void addWithRefFStat() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2, 3);
            FStat fStatB = factory.getFStat(4, 5);

            fPlotBar.addRef(2, fStatA);
            fPlotBar.addRef(3, fStatB);

            assertEquals(2, fPlotBar.size());
            assertEquals(3, fPlotBar.getY(0).size());
            assertEquals(2, fPlotBar.getY(1).size());
            assertSame(fStatA, fPlotBar.getY(0));
            assertSame(fStatB, fPlotBar.getY(1));

            FStat fStatC = factory.getFStat(6);

            assertThrows(IllegalStateException.class, () -> fPlotBar.addRef(2, fStatC));
        }
    }
}
