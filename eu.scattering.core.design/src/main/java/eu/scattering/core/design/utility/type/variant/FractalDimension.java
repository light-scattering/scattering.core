package eu.scattering.core.design.utility.type.variant;

public enum FractalDimension {
    BC_RAW, BC_BASELINE, BC_OPTIMIZED,
    CD_RESTRICTED, CD_FULL,
    MR_RESTRICTED, MR_FULL,

    // -------------------------------------------------------------------------------------------------

    @Deprecated(forRemoval = true) BC_MANUSCRIPT_BASELINE,
    @Deprecated(forRemoval = true) BC_MANUSCRIPT_PCA,
    @Deprecated(forRemoval = true) BC_MANUSCRIPT_SHIFT,
    @Deprecated(forRemoval = true) BC_MANUSCRIPT_SHIFT_PCA,
    @Deprecated(forRemoval = true) BC_MANUSCRIPT_SHIFT_FACTOR,
    @Deprecated(forRemoval = true) BC_MANUSCRIPT_SHIFT_PCA_FACTOR
}
