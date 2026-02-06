package eu.scattering.core.impl.component.aggregate.extension;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.extension.FExtension;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.transfer.complex.FBufferData;
import org.json.JSONObject;

public class FExtensionDef implements FExtension {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "extension";
    private static final String JSON_CAPACITY = "capacity";
    private static final String JSON_MATERIAL = "material";

    private final ScatFactory factory;

    private FBuffer<FBufferData> buffer;
    private FMaterial material;

    private FExtensionDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FExtension create(ScatFactory factory) {

        return new FExtensionDef(factory);
    }

    public static FExtension create(ScatFactory factory, JSONObject json) {
        FExtension results = new FExtensionDef(factory);

        if (json.has(JSON_MATERIAL)) {
            results.setRefFMaterial(factory.getFMaterial(json.getJSONObject(JSON_MATERIAL)));
        }

        if (json.has(JSON_CAPACITY)) {
            results.addFBuffer(json.getInt(JSON_CAPACITY));
        }

        return results;
    }

    @Override
    public FBuffer<FBufferData> getRefFBuffer() {

        return this.buffer;
    }

    @Override
    public void setRefFBuffer(FBuffer<FBufferData> buffer) {

        this.buffer = buffer;
    }

    @Override
    public FMaterial getRefFMaterial() {

        return this.material;
    }

    @Override
    public void setRefFMaterial(FMaterial material) {

        this.material = material;
    }

    @Override
    public void addFBuffer(int capacity) {

        if (capacity < 1) {
            throw new IllegalArgumentException("The buffer must consist of at least one element");
        }

        setRefFBuffer(this.factory.getFBuffer(capacity));
    }

    @Override
    public void addFMaterial() {

        setRefFMaterial(this.factory.getFMaterial());
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        if (getRefFBuffer() != null) {
            json.put(JSON_CAPACITY, getRefFBuffer().capacity());
        }

        if (getRefFMaterial() != null) {
            json.put(JSON_MATERIAL, getRefFMaterial().toJSON());
        }

        return json;
    }

    @Override
    public FExtension copy() {
        FExtension results = FExtensionDef.create(this.factory);

        if (getRefFBuffer() != null) {
            results.addFBuffer(getRefFBuffer().capacity());
        }

        if (getRefFMaterial() != null) {
            results.setRefFMaterial(getRefFMaterial());
        }

        return results;
    }

    @Override
    public boolean isExact(FExtension arg) {

        if (getRefFMaterial() == null && arg.getRefFMaterial() != null) {
            return false;
        }

        if (getRefFMaterial() != null && arg.getRefFMaterial() == null) {
            return false;
        }

        if (getRefFMaterial() != null && arg.getRefFMaterial() != null) {
            return getRefFMaterial().isEqual(arg.getRefFMaterial());
        }

        return true;
    }

    //--------------------------------------------------

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
