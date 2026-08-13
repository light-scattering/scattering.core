package eu.scattering.core.paper.man_07_2026;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigBC;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigDC;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigMR;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.TestConfig.factory;

@Disabled
@Tag("sandbox")
@DisplayName("Paper - Morphology (Df method)")
public class DFMethodComparisonTest {

    @Test
    @DisplayName("Dimension 1.4 - 1000")
    void df14_1000() {
        double df = 1.4;
        double kf = 1.5;

        Container container = new Container(df, kf, 1000, 1000);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.8 - 1000")
    void df18_1000() {
        double df = 1.8;
        double kf = 1.3;

        Container container = new Container(df, kf, 1000, 1000);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 2.2 - 1000")
    void df22_1000() {
        double df = 2.2;
        double kf = 0.8;

        Container container = new Container(df, kf, 1000, 1000);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.4 - 2500")
    void df14_2500() {
        double df = 1.4;
        double kf = 1.5;

        Container container = new Container(df, kf, 2500, 1000);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.8 - 2500")
    void df18_2500() {
        double df = 1.8;
        double kf = 1.3;

        Container container = new Container(df, kf, 2500, 1000);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 2.2 - 2500")
    void df22_2500() {
        double df = 2.2;
        double kf = 0.8;

        Container container = new Container(df, kf, 2500, 1000);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.4 - 5000")
    void df14_5000() {
        double df = 1.4;
        double kf = 1.5;

        Container container = new Container(df, kf, 5000, 100);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.8 - 5000")
    void df18_5000() {
        double df = 1.8;
        double kf = 1.3;

        Container container = new Container(df, kf, 5000, 100);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 2.2 - 5000")
    void df22_5000() {
        double df = 2.2;
        double kf = 0.8;

        Container container = new Container(df, kf, 5000, 100);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.4 - 10000")
    void df14_10000() {
        double df = 1.4;
        double kf = 1.5;

        Container container = new Container(df, kf, 10000, 100);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.8 - 10000")
    void df18_10000() {
        double df = 1.8;
        double kf = 1.3;

        Container container = new Container(df, kf, 10000, 100);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 2.2 - 10000")
    void df22_10000() {
        double df = 2.2;
        double kf = 0.8;

        Container container = new Container(df, kf, 10000, 100);

        container.measure();
        container.show();
    }

    private static class Container {
        private final double df, kf;
        private final int size, repetitions;

        private final FStat pl = factory.getFStat();
        private final FStat bcOptimized = factory.getFStat();
        private final FStat dcRestricted = factory.getFStat();
        private final FStat dcFull = factory.getFStat();
        private final FStat mrRestricted = factory.getFStat();
        private final FStat mrFull = factory.getFStat();

        private final FStat bcOptimizedTime = factory.getFStat();
        private final FStat dcRestrictedTime = factory.getFStat();
        private final FStat dcFullTime = factory.getFStat();
        private final FStat mrRestrictedTime = factory.getFStat();
        private final FStat mrFullTime = factory.getFStat();

        private final boolean runPl = true;
        private final boolean runBcOptimized = true;
        private final boolean runDcRestricted = true;
        private final boolean runDcFull = false;
        private final boolean runMrRestricted = true;
        private final boolean runMrFull = false;

        private final FConfigCCPL configPL = factory.getFConfigCCPL(FConfigCCPL.Preset.WINDOW);
        private final FConfigBC configBcOptimized = factory.getFConfigBC(FConfigBC.Preset.OPTIMIZED);
        private final FConfigDC configDcRestricted = factory.getFConfigDC(FConfigDC.Preset.RESTRICTED).setRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
        private final FConfigDC configDcFull = factory.getFConfigDC(FConfigDC.Preset.FULL).setRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
        private final FConfigMR configMrRestricted = factory.getFConfigMR(FConfigMR.Preset.RESTRICTED).setRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
        private final FConfigMR configMrFull = factory.getFConfigMR(FConfigMR.Preset.FULL).setRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

        public Container(double df, double kf, int size, int repetitions) {
           this.df = df;
           this.kf = kf;
           this.size = size;
           this.repetitions = repetitions;
        }

        private void measure() {
            System.out.printf("Df=%s, Kf=%s\n", df, kf);

            for (int i = 0 ; i < repetitions ; i++) {

                if (i > 0 && i % 100 == 0) {
                    System.out.println();
                }

                try {
                    measureCore();

                    System.out.print(".");
                } catch (Exception e) {
                    i--;

                    System.out.print("X");
                }

                System.out.flush();
            }
        }

        private void measureCore() {
            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

            var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
            fModel.addStepMonitor(fMonitor);

            fModel.build();

            update(fAggregate, fMonitor);
        }

        public void update(FAggregate aggregate, FMonitorCCRadiusOfGyration monitor) {
            double resPl;

            double resBc, resDcl, resDcf, resMrl, resMrf;
            long timeBc, timeDcl, timeDcf, timeMrl, timeMrf;

            if (runPl) {
                resPl = monitor.getPowerLawDimension(configPL);
            }

            if (runBcOptimized) {
                long time = System.currentTimeMillis();
                resBc = aggregate.getFractalDimension(configBcOptimized);
                timeBc = System.currentTimeMillis() - time;
            }

            if (runDcRestricted) {
                long time = System.currentTimeMillis();
                resDcl = aggregate.getFractalDimension(configDcRestricted);
                timeDcl = System.currentTimeMillis() - time;
            }

            if (runDcFull) {
                long time = System.currentTimeMillis();
                resDcf = aggregate.getFractalDimension(configDcFull);
                timeDcf = System.currentTimeMillis() - time;
            }

            if (runMrRestricted) {
                long time = System.currentTimeMillis();
                resMrl = aggregate.getFractalDimension(configMrRestricted);
                timeMrl = System.currentTimeMillis() - time;
            }

            if (runMrFull) {
                long time = System.currentTimeMillis();
                resMrf = aggregate.getFractalDimension(configMrFull);
                timeMrf = System.currentTimeMillis() - time;
            }

            if (runPl) {
                pl.add(resPl);
            }

            if (runBcOptimized) {
                bcOptimized.add(resBc);
                bcOptimizedTime.add(timeBc);
            }

            if (runDcRestricted) {
                dcRestricted.add(resDcl);
                dcRestrictedTime.add(timeDcl);
            }

            if (runDcFull) {
                dcFull.add(resDcf);
                dcFullTime.add(timeDcf);
            }

            if (runMrRestricted) {
                mrRestricted.add(resMrl);
                mrRestrictedTime.add(timeMrl);
            }

            if (runMrFull) {
                mrFull.add(resMrf);
                mrFullTime.add(timeMrf);
            }
        }

        public void show() {

            System.out.println();
            if (runPl)          System.out.printf("Power law:      %1.6f, %1.6f\n%s\n",
                    pl.mean(), pl.std(true), pl.toJSON());
            if (runBcOptimized) System.out.printf("Box counting O: %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    bcOptimized.mean(), bcOptimized.std(true), bcOptimizedTime.mean(), bcOptimizedTime.std(true), bcOptimized.toJSON());
            if (runDcRestricted)   System.out.printf("Density:        %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    dcRestricted.mean(), dcRestricted.std(true), dcRestrictedTime.mean(), dcRestrictedTime.std(true), dcRestricted.toJSON());
            if (runDcFull)      System.out.printf("Density (full): %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    dcFull.mean(), dcFull.std(true), dcFullTime.mean(), dcFullTime.std(true), dcFull.toJSON());
            if (runMrRestricted)   System.out.printf("Mass:           %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    mrRestricted.mean(), mrRestricted.std(true), mrRestrictedTime.mean(), mrRestrictedTime.std(true), mrRestricted.toJSON());
            if (runMrFull)      System.out.printf("Mass (full):    %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    mrFull.mean(), mrFull.std(true), mrFullTime.mean(), mrFullTime.std(true), mrFull.toJSON());
            System.out.println();
        }
    }
}
