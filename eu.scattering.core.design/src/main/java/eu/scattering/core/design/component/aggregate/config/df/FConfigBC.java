package eu.scattering.core.design.component.aggregate.config.df;

public interface FConfigBC {

    int getShiftsPerAxis();
    FConfigBC setShiftsPerAxis(int shifts);

    double getScalingFactor();
    FConfigBC setScalingFactor(double factor);

    boolean isAlignedOrigin();
    FConfigBC setAlignedOrigin(boolean isAligned);

    boolean isAlignedPCA();
    FConfigBC setAlignedPCA(boolean isAligned);

    double getWindowRatio();
    FConfigBC setWindowRatio(double ratio);

    // -------------------------------------------------------------------------------------------------

    enum Preset {
        RAW, BASELINE, OPTIMIZED,
        MAN_07072026_BASELINE,
        MAN_07072026_PCA,
        MAN_07072026_SHIFT,
        MAN_07072026_SHIFT_PCA,
        MAN_07072026_SHIFT_STEP,
        MAN_07072026_SHIFT_PCA_STEP
    }
}
