package eu.scattering.core.paper.man_07_2026;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static eu.scattering.core.test.TestConfig.factory;

@Disabled
@Tag("sandbox")
@DisplayName("Paper - Morphology (PCA volume reduction)")
public class BCVolumeReductionPCATest {
    private final int repetitions = 100;

    private static final DecimalFormat df = new DecimalFormat("#.####");

    static {
        df.setRoundingMode(RoundingMode.DOWN);
    }

    @Test
    @DisplayName("Dimension 1.4")
    void df14() {
        Container container = new Container();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                measure(container, 1.4, 1.5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        container.show();
    }

    @Test
    @DisplayName("Dimension 1.8")
    void df18() {
        Container container = new Container();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                measure(container, 1.8, 1.3);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        container.show();
    }

    @Test
    @DisplayName("Dimension 2.2")
    void df22() {
        Container container = new Container();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                measure(container, 2.2, 0.8);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        container.show();
    }

    private void measure(Container container, double df, double kf) {
        int size = 1000;

        FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

        FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        container.update(fAggregate);
    }

    private record Container(FStat initial, FStat corrected, FStat error) {

        public Container() {

            this (factory.getFStat(), factory.getFStat(), factory.getFStat());
        }

        public void update(FAggregate aggregate) {
            FPos3D initLength = aggregate.getLength();
            double initVolume = initLength.getD0() * initLength.getD1() * initLength.getD2();

            initial.add(initVolume);

            aggregate.pca();

            FPos3D updatedLength = aggregate.getLength();
            double updatedVolume = updatedLength.getD0() * updatedLength.getD1() * updatedLength.getD2();

            corrected.add(updatedVolume);

            error.add(factory.getStatisticsHelper().getRelErr(initVolume, updatedVolume));
        }

        public void show() {
            System.out.println("Error: " + df.format(error.mean()) + ",\t" + df.format(error.max()) + ",\t" + df.format(error.min()));
        }
    }
}
