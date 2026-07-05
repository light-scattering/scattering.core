package eu.scattering.core.paper.morphology_07_2026;

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

import static eu.scattering.core.test.Config.factory;

@Disabled
@DisplayName("Paper - Morphology (PCA volume reduction)")
public class DfBCVolumeReductionPCATest {
    private final int repetitions = 1000;
    private final int size = 1000;

    private static final DecimalFormat df = new DecimalFormat("#.####");

    static {
        df.setRoundingMode(RoundingMode.DOWN);
    }

    @Test
    @Tag("Visual")
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
    @Tag("Visual")
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
    @Tag("Visual")
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
        FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

        FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        container.update(fAggregate);
    }

    private record Container(
            FStat initial,
            FStat corrected,
            FStat error
    ) {
        public Container() {
            this(
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat());
        }

        public void update(FAggregate aggregate) {
            FPos3D lengthInitial = aggregate.getLength();
            double volumeInitial = lengthInitial.getD0() * lengthInitial.getD1() * lengthInitial.getD2();

            initial.add(volumeInitial);

            aggregate.pca();

            FPos3D lengthCorrected = aggregate.getLength();
            double volumeCorrected = lengthCorrected.getD0() * lengthCorrected.getD1() * lengthCorrected.getD2();

            corrected.add(volumeCorrected);

            error.add(factory.getStatisticsHelper().getRelErr(volumeInitial, volumeCorrected));
        }

        public void show() {
            System.out.println("Error: " + df.format(error.mean()) + ",\t" + df.format(error.max()) + ",\t" + df.format(error.min()));
        }
    }
}
