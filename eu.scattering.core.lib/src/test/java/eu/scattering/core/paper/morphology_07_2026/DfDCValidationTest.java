//package eu.scattering.core.paper.morphology_07_2026;
//
//import eu.scattering.core.design.component.aggregate.FAggregate;
//import eu.scattering.core.design.component.aggregate.meta.df.FMetaDC;
//import eu.scattering.core.design.statistics.base.FStat;
//import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
//import static eu.scattering.core.test.Config.factory;
//
//@Disabled
//@DisplayName("Paper - Morphology (Df method)")
//public class DfDCValidationTest {
//    private final int size = 5000;
//    private final int repetitions = 100;
//
//    @Test
//    @Tag("Comparison")
//    @DisplayName("Dimension 1.4")
//    void df14() {
//        double df = 1.4;
//        double kf = 1.5;
//
//        Container container = new Container(df, kf, size, repetitions);
//
//        container.measure();
//        container.show();
//    }
//
//    @Test
//    @Tag("Comparison")
//    @DisplayName("Dimension 1.8")
//    void df18() {
//        double df = 1.8;
//        double kf = 1.3;
//
//        Container container = new Container(df, kf, size, repetitions);
//
//        container.measure();
//        container.show();
//    }
//
//    @Test
//    @Tag("Comparison")
//    @DisplayName("Dimension 2.2")
//    void df22() {
//        double df = 2.2;
//        double kf = 0.8;
//
//        Container container = new Container(df, kf, size, repetitions);
//
//        container.measure();
//        container.show();
//    }
//
//    private static class Container {
//        private final double df, kf;
//        private final int size, repetitions;
//
//        private final FStat dcLimited = factory.getFStat();
//        private final FStat dcFull = factory.getFStat();
//
//        private final FStat dcLimitedTime = factory.getFStat();
//        private final FStat dcFullTime = factory.getFStat();
//
//        public Container(double df, double kf, int size, int repetitions) {
//           this.df = df;
//           this.kf = kf;
//           this.size = size;
//           this.repetitions = repetitions;
//        }
//
//        private void measure() {
//            System.out.printf("Df=%s, Kf=%s\n", df, kf);
//
//            for (int i = 0 ; i < repetitions ; i++) {
//
//                if (i > 0 && i % 100 == 0) {
//                    System.out.println();
//                }
//
//                try {
//                    measureCore();
//
//                    System.out.print(".");
//                } catch (Exception e) {
//                    System.out.print("X");
//                    i--;
//                }
//
//                System.out.flush();
//            }
//        }
//
//        private void measureCore() {
//            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);
//
//            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
//            fModel.setEarlyStageCorrection(true);
//
//            fModel.build();
//
//            update(fAggregate);
//        }
//
//        public void update(FAggregate aggregate) {
//            FMetaDC metaReduced = factory.getFMetaDC();
//            double dimLimited = aggregate.getFractalDimensionDensityCorrelation(0.9, RadiusOfGyration.SIMPLE_POLY, 1.1, true, metaReduced);
//
//            dcLimited.add(dimLimited);
//            dcLimitedTime.add(metaReduced.getExecutionTime());
//        }
//
//        public void show() {
//            System.out.println();
//            System.out.printf("Density:        %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
//                    dcLimited.mean(), dcLimited.std(true), dcLimitedTime.mean(), dcLimitedTime.std(true), dcLimited.toJSON());
//            System.out.println();
//        }
//    }
//}
