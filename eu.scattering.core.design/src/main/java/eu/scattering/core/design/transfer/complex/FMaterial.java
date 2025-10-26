package eu.scattering.core.design.transfer.complex;

import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FMaterial implements Transfer {
    private final Map<String, FMaterialData> data = new HashMap<>();

    private FMaterial() {}

    public static FMaterial create() {
        FMaterial results = new FMaterial();

        results.setDensity("", 1);
        results.setRefIndex("", 1, 0);

        return results;
    }

    public static FMaterial create(JSONObject json) {
        FMaterial results = new FMaterial();

        for (String key : json.keySet()) {
            FMaterialData material = FMaterialData.create(json.getJSONObject(key));
            results.addMaterial(key, material);
        }

        return results;
    }

    public int size() {

        return this.data.size();
    }

    public double getDensity(String tag) {

        return getMaterial(tag, false).getDensity();
    }

    public void setDensity(String tag, double density) {

        getMaterial(tag, true).setDensity(density);
    }

    public double getRefIndexRe(String tag) {

        return getMaterial(tag, false).getRefIndexRe();
    }

    public void setRefIndexRe(String tag, double refIndexRe) {

        getMaterial(tag, true).setRefIndexRe(refIndexRe);
    }

    public double getRefIndexIm(String tag) {

        return getMaterial(tag, false).getRefIndexIm();
    }

    public void setRefIndexIm(String tag, double refIndexIm) {

        getMaterial(tag, true).setRefIndexIm(refIndexIm);
    }

    //--------------------------------------------------

    public void setRefIndex(String tag, double refIndexRe, double refIndexIm) {
        FMaterialData material = getMaterial(tag, true);

        material.setRefIndexRe(refIndexRe);
        material.setRefIndexIm(refIndexIm);
    }

    //--------------------------------------------------

    private FMaterialData getMaterial(String tag, boolean create) {

        FMaterialData material = this.data.get(tag);

        if (material != null) {
            return material;
        }

        if (!create) {
            throw new IllegalArgumentException("The material is not defined");
        }

        material = FMaterialData.create();

        this.data.put(tag, material);

        return material;
    }

    private void addMaterial(String name, FMaterialData material) {

        this.data.put(name, material);
    }

    //--------------------------------------------------

    public FMaterial copy() {
        FMaterial results = FMaterial.create();

        for (Map.Entry<String, FMaterialData> entry : this.data.entrySet()) {
            results.addMaterial(entry.getKey(), entry.getValue().copy());
        }

        return results;
    }

    public boolean isEqual(FMaterial material) {

        if (size() != material.size()) {
            return false;
        }

        for (String key : this.data.keySet()) {
            if (!this.data.get(key).isEqual(material.getMaterial(key, true))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        for (String key : this.data.keySet()) {
            json.put(key, this.data.get(key).toJSON());
        }

        return json;
    }
}
