package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.statistics.base.FStat1D;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;

@Disabled
public class FAggregateHeavyTest {

    @Test
    @DisplayName("Infinite PC tunable Filippov 3D")
    void infinitePCTunableFilippov3D() {
        FAggregate fAggregate = factory.getFAggregatePreMono(250, 1);

        FModelPCTunable modelTunable = factory.createFModelFilippov3D(fAggregate, 2.2, 1);
        modelTunable.setEarlyStageCorrection(true);

        FStat1D data = factory.getFStat1D();

        for (int i = 0 ; i < 100 ; i++) {
            modelTunable.build();

            data.add(fAggregate.getBoxDimension());

            System.out.println(i);
        }

        System.out.println(data.mean());
        System.out.println(data.std(true));
    }



}
