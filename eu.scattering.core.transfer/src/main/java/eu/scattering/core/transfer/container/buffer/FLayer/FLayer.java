package eu.scattering.core.transfer.container.buffer.FLayer;

import eu.scattering.core.transfer.container.buffer.Buffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FLayer implements Buffer<FLayer> {
    private static final String JSON_MAIN = "layer";
    private static final String JSON_VAL = "val";

    private final List<FLayerUnit> layers;

    private int index;

    private FLayer() {
        this.index = 0;

        this.layers = new ArrayList<>();
        this.layers.add(FLayerUnit.create());
    }

    private FLayer(List<FLayerUnit> layers) {
        this.index = layers.size() - 1;

        this.layers = layers;
    }

    protected static FLayer create() {

        return new FLayer();
    }

    protected static FLayer create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        List<FLayerUnit> layers = new ArrayList<>();

        JSONArray structure = json.getJSONArray(JSON_VAL);

        for (int i = 0 ; i < structure.length() ; i++) {
            layers.add(new FLayerUnit(structure.getInt(i)));
        }

        return new FLayer(layers);
    }

    public void inc() {

        this.layers.get(0).inc();
    }

    public void inc(int layer) {

        if (layer < 0) {
            throw new IllegalArgumentException("The layer index must be positive");
        }

        while (layer > index) {

            index++;

            if (layers.size() - 1 < index) {
                layers.add(FLayerUnit.create());
            } else {
                layers.get(index).reset();
            }
        }

        layers.get(layer).inc();
    }

    public int get() {

        return layers.get(0).get();
    }

    public int get(int layer) {

        if (layer < 0) {
            throw new IllegalArgumentException("The layer index must be positive");
        }

        if (layer > index) {
            return 0;
        }

        return layers.get(layer).get();
    }

    public int getSum() {
        int results = 0;

        for (int i = 0 ; i <= index ; i++) {
            results += layers.get(i).get();
        }

        return results;
    }

    public int getNumberOfLayers() {

        return index;
    }

    public void iterate(FLayerConsumer consumer) {

        for (int i = 0 ; i < layers.size() ; i++) {
            consumer.apply(i, layers.get(i).get());
        }
    }

    public void reset() {

        this.index = 0;
        this.layers.get(0).reset();
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        for (int i = 0 ; i <= index ; i++) {
            json.append(JSON_VAL, get(i));
        }

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 1;

        for (int i = 0 ; i <= index ; i++) {
            hashCode = 31 * hashCode + layers.get(i).get();
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FLayer) {
            FLayer fLayer = (FLayer) object;

            if (index != fLayer.index) {
                return false;
            }

            for (int i = 0 ; i < index ; i++) {
                if (layers.get(i).get() != fLayer.get(i)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    //--------------------------------------------------

    @FunctionalInterface
    public interface FLayerConsumer {

        void apply(int index, int value);
    }

    private static class FLayerUnit {
        private int value;

        private FLayerUnit() {

            this.value = 0;
        }

        private FLayerUnit(int value) {

            this.value = value;
        }

        protected static FLayerUnit create() {

            return new FLayerUnit();
        }

        protected static FLayerUnit create(int value)  {

            return new FLayerUnit(value);
        }

        public void inc() {

            this.value++;
        }

        public int get() {

            return this.value;
        }

        public void reset() {

            this.value = 0;
        }
    }
}

