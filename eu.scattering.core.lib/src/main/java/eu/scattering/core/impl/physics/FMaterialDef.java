package eu.scattering.core.impl.physics;

import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.physics.material.data.FMaterialData;
import eu.scattering.core.design.physics.material.data.FMaterialDataFactory;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FMaterialDef implements FMaterial {
    private final Map<String, FMaterialData> data = new HashMap<>();
    private final FMaterialDataFactory factory;

    private FMaterialDef(FMaterialDataFactory factory) {

        this.factory = factory;
    }

    public static FMaterial create(FMaterialDataFactory factory) {
        FMaterial results = new FMaterialDef(factory);

        results.setDensity("", 1);
        results.setRefIndex("", 1, 0);

        return results;
    }

    public static FMaterial create(FMaterialDataFactory factory, JSONObject json) {
        FMaterial results = new FMaterialDef(factory);

        for (String key : json.keySet()) {
            FMaterialData material = factory.getFMaterialData(json.getJSONObject(key));
            results.addMaterial(key, material);
        }

        return results;
    }

    @Override
    public int size() {

        return this.data.size();
    }

    @Override
    public double getDensity(String tag) {

        return getMaterial(tag, false).getDensity();
    }

    @Override
    public void setDensity(String tag, double density) {

        getMaterial(tag, true).setDensity(density);
    }

    @Override
    public double getRefIndexRe(String tag) {

        return getMaterial(tag, false).getRefIndexRe();
    }

    @Override
    public void setRefIndexRe(String tag, double refIndexRe) {

        getMaterial(tag, true).setRefIndexRe(refIndexRe);
    }

    @Override
    public double getRefIndexIm(String tag) {

        return getMaterial(tag, false).getRefIndexIm();
    }

    @Override
    public void setRefIndexIm(String tag, double refIndexIm) {

        getMaterial(tag, true).setRefIndexIm(refIndexIm);
    }

    //--------------------------------------------------

    @Override
    public void setRefIndex(String tag, double refIndexRe, double refIndexIm) {
        FMaterialData material = getMaterial(tag, true);

        material.setRefIndexRe(refIndexRe);
        material.setRefIndexIm(refIndexIm);
    }

    //--------------------------------------------------

    @Override
    public FMaterialData getMaterial(String tag, boolean create) {

        FMaterialData material = this.data.get(tag);

        if (material != null) {
            return material;
        }

        if (!create) {
            throw new IllegalArgumentException("The material is not defined");
        }

        material = factory.getFMaterialData();

        this.data.put(tag, material);

        return material;
    }

    @Override
    public void addMaterial(String name, FMaterialData material) {

        this.data.put(name, material);
    }

    //--------------------------------------------------

    @Override
    public FMaterial copy() {
        FMaterial results = FMaterialDef.create(this.factory);

        for (Map.Entry<String, FMaterialData> entry : this.data.entrySet()) {
            results.addMaterial(entry.getKey(), entry.getValue().copy());
        }

        return results;
    }

    @Override
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
