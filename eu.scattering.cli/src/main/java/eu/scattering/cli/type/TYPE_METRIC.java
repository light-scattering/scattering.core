package eu.scattering.cli.type;

public enum TYPE_METRIC {
    np,
    rp, rp__avg, rp__std, rp__max, rp__min,
    df_bc, df_mr, df_dc,
    //--------------------
    Rg_mesh, Rg_mono, Rg_mono_06R1, Rg_mono_10R2, Rg_poly, Rg_poly_06R1, Rg_poly_10R2,
    Length_x,
    Length_y,
    Length_z,
    MassCenter, MassCenter_mono, MassCenter_poly, MassCenter_mesh,
    SphericalCenter,
    BoxCenter,
    RadiusFromMassCenter, RadiusFromMassCenter_mono, RadiusFromMassCenter_poly, RadiusFromMassCenter_mesh,
    RadiusFromSphericalCenter,
    RadiusFromBoxCenter,
    Volume, Volume_simple, Volume_complex,
    VolumeRadius, VolumeRadius_simple, VolumeRadius_complex,
    Surface, Surface_simple, Surface_complex,
    SurfaceRadius, SurfaceRadius_simple, SurfaceRadius_complex,
    OverlapFactor_p_vol, OverlapFactor_p_vol__avg, OverlapFactor_p_vol__std, OverlapFactor_p_vol__max, OverlapFactor_p_vol__min,
    OverlapFactor_p_lin, OverlapFactor_p_lin__avg, OverlapFactor_p_lin__std, OverlapFactor_p_lin__max, OverlapFactor_p_lin__min,
    OverlapFactor_p_count, OverlapFactor_p_count__avg, OverlapFactor_p_count__std, OverlapFactor_p_count__max, OverlapFactor_p_count__min,
    OverlapFactor_c_vol,
    IsConnected,
    IsPointConnected,
    IsNonOverlapping,
    TripletAngle, TripletAngle__fun, TripletAngle__avg, TripletAngle__std, TripletAngle__max, TripletAngle__min,
    PairDistance, PairDistance__fun, PairDistance__avg, PairDistance__std, PairDistance__max, PairDistance__min,
    CoordinationNumber, CoordinationNumber__fun, CoordinationNumber__avg, CoordinationNumber__std, CoordinationNumber__max, CoordinationNumber__min,
    BoxCoverage__fun,
    DensityCorrelation__fun
}
