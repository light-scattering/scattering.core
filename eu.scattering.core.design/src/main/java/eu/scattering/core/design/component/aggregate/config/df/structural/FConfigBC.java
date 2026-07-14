package eu.scattering.core.design.component.aggregate.config.df.structural;

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
        NAIVE, BASELINE, OPTIMIZED,
        MAN_072026_BASELINE,
        MAN_072026_PCA,
        MAN_072026_SHIFT,
        MAN_072026_SHIFT_PCA,
        MAN_072026_SHIFT_STEP,
        MAN_072026_SHIFT_PCA_STEP
    }
}
