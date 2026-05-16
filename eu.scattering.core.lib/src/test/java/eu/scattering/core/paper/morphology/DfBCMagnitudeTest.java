package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.utility.type.option.Length;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@DisplayName("Paper - Morphology (magnitude)")
public class DfBCMagnitudeTest {

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4 - 10000")
    void df14_10000() {
        double df = 1.4;
        double kf = 1.5;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(10000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.4, Kf=1.5, Np=10000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8 - 10000")
    void df18_10000() {
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(10000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.8, Kf=1.3, Np=10000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2 - 10000")
    void df22_10000() {
        double df = 2.2;
        double kf = 0.8;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(10000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=2.2, Kf=0.8, Np=10000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4 - 5000")
    void df14_5000() {
        double df = 1.4;
        double kf = 1.5;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(5000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.4, Kf=1.5, Np=5000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8 - 5000")
    void df18_5000() {
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(5000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.8, Kf=1.3, Np=5000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2 - 5000")
    void df22_5000() {
        double df = 2.2;
        double kf = 0.8;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(5000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=2.2, Kf=0.8, Np=5000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4 - 2500")
    void df14_2500() {
        double df = 1.4;
        double kf = 1.5;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(2500, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.4, Kf=1.5, Np=2500");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8 - 2500")
    void df18_2500() {
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(2500, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.8, Kf=1.3, Np=2500");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2 - 2500")
    void df22_2500() {
        double df = 2.2;
        double kf = 0.8;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(2500, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=2.2, Kf=0.8, Np=2500");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4 - 1000")
    void df14_1000() {
        double df = 1.4;
        double kf = 1.5;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(1000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.4, Kf=1.5, Np=1000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8 - 1000")
    void df18_1000() {
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(1000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=1.8, Kf=1.3, Np=1000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2 - 1000")
    void df22_1000() {
        double df = 2.2;
        double kf = 0.8;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(1000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Df=2.2, Kf=0.8, Np=1000");
        System.out.println("Radius: " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude: " + magnitude);

        assertTrue(radius > 0);
    }
}
