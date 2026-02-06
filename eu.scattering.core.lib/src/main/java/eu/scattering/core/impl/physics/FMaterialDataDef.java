package eu.scattering.core.impl.physics;

import eu.scattering.core.design.physics.material.data.FMaterialData;
import org.json.JSONObject;

public class FMaterialDataDef implements FMaterialData {
    private static final String JSON_DENSITY = "rho";
    private static final String JSON_REF_INDEX_RE = "re";
    private static final String JSON_REF_INDEX_IM = "im";

    private double density = 1;
    private double refIndexRe = 1;
    private double refIndexIm = 0;

    private FMaterialDataDef() {}

    public static FMaterialData create() {

        return new FMaterialDataDef();
    }

    public static FMaterialData create(JSONObject json) {
        FMaterialData material = new FMaterialDataDef();

        material.setDensity(json.getDouble(JSON_DENSITY));
        material.setRefIndexRe(json.getDouble(JSON_REF_INDEX_RE));
        material.setRefIndexIm(json.getDouble(JSON_REF_INDEX_IM));

        return material;
    }

    public double getDensity() {

        return density;
    }

    public void setDensity(double density) {

        this.density = density;
    }

    public double getRefIndexRe() {

        return this.refIndexRe;
    }

    public void setRefIndexRe(double refIndexRe) {

        this.refIndexRe = refIndexRe;
    }

    public double getRefIndexIm() {

        return this.refIndexIm;
    }

    public void setRefIndexIm(double refIndexIm) {

        this.refIndexIm = refIndexIm;
    }

    //--------------------------------------------------

    public FMaterialData copy() {
        FMaterialData results = FMaterialDataDef.create();

        results.setDensity(getDensity());
        results.setRefIndexRe(getRefIndexRe());
        results.setRefIndexIm(getRefIndexIm());

        return results;
    }

    public boolean isEqual(FMaterialData material) {

        if (getRefIndexRe() != material.getRefIndexRe()) {
            return false;
        }

        if (getRefIndexIm() != material.getRefIndexIm()) {
            return false;
        }

        return getDensity() == material.getDensity();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_DENSITY, getDensity());
        json.put(JSON_REF_INDEX_RE, getRefIndexRe());
        json.put(JSON_REF_INDEX_IM, getRefIndexIm());

        return json;
    }
}
