package eu.scattering.core.impl.storage;

import eu.scattering.core.design.storage.layer.FLayer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class FLayerDef implements FLayer {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "layer";
    private static final String JSON_SIZE = "size";

    private final List<FLayerUnit> layers;

    private int index;

    private FLayerDef() {
        this.index = 0;

        this.layers = new ArrayList<>();
        this.layers.add(FLayerUnit.create());
    }

    public static FLayer create() {

        return new FLayerDef();
    }

    public static FLayer create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FLayer fLayer = new FLayerDef();

        fLayer.set(json.getInt(JSON_SIZE) - 1, 0);

        return fLayer;
    }

    @Override
    public int get(int layer) {

        if (layer < 0) {
            throw new IllegalArgumentException("The layer index must be positive");
        }

        if (layer > index) {
            return 0;
        }

        return layers.get(layer).get();
    }

    @Override
    public int inc(int layer) {

        if (layer < 0) {
            throw new IllegalArgumentException("The layer index must be positive");
        }

        int layersAdded = 0;
        while (layer > index) {
            index++;

            if (layers.size() - 1 < index) {
                layers.add(FLayerUnit.create());
            } else {
                layers.get(index).reset();
            }

            layersAdded++;
        }

        layers.get(layer).inc();

        return layersAdded;
    }

    @Override
    public int set(int layer, int value) {

        if (layer < 0) {
            throw new IllegalArgumentException("The layer index must be positive");
        }

        if (value < 0) {
            throw new IllegalArgumentException("The value must nut be lower than zero");
        }

        int layersAdded = 0;
        while (layer > index) {
            index++;

            if (layers.size() - 1 < index) {
                layers.add(FLayerUnit.create());
            } else {
                layers.get(index).reset();
            }

            layersAdded++;
        }

        layers.get(layer).set(value);

        return layersAdded;
    }

    @Override
    public void add(FLayer... fLayers) {
        int index = size();

        for (FLayer fLayer : fLayers) {
            if (fLayer.size() > index) {
                index = fLayer.size();
            }
        }

        for (int i = 0 ; i < index ; i++) {
            int sum = get(i);

            for (FLayer fLayer : fLayers) {
                sum += fLayer.get(i);
            }

            set(i, sum);
        }
    }

    @Override
    public void avg(FLayer... fLayers) {
        int index = size();

        for (FLayer fLayer : fLayers) {
            if (fLayer.size() > index) {
                index = fLayer.size();
            }
        }

        for (int i = 0 ; i < index ; i++) {
            int sum = get(i);

            for (FLayer fLayer : fLayers) {
                sum += fLayer.get(i);
            }

            set(i, Math.round((float) sum / (fLayers.length + 1)));
        }
    }

    @Override
    public void max(FLayer... fLayers) {
        int index = size();

        for (FLayer fLayer : fLayers) {
            if (fLayer.size() > index) {
                index = fLayer.size();
            }
        }

        for (int i = 0 ; i < index ; i++) {
            int max = get(i);

            for (FLayer fLayer : fLayers) {
                if (fLayer.get(i) > max) {
                    max = fLayer.get(i);
                }
            }

            set(i, max);
        }
    }

    @Override
    public double addSelf() {
        double results = 0;

        for (int i = 0 ; i <= index ; i++) {
            results += layers.get(i).get();
        }

        return results;
    }

    @Override
    public double avgSelf() {
        double results = 0;

        for (int i = 0 ; i <= index ; i++) {
            results += layers.get(i).get();
        }

        return results / size();
    }

    @Override
    public double maxSelf() {
        double max = 0;

        for (int i = 0 ; i <= index ; i++) {
            if (layers.get(i).get() > max) {
                max = layers.get(i).get();
            }
        }

        return max;
    }

    @Override
    public int size() {

        return index + 1;
    }

    @Override
    public void reset() {

        this.index = 0;
        this.layers.get(0).reset();
    }

    @Override
    public boolean isEmpty() {

        return addSelf() == 0;
    }

    @Override
    public boolean isZeroLayerOnly() {

        return size() == 1;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_SIZE, size());

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

        if (object instanceof FLayerDef fLayer) {

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

    @Override
    public Iterator<Integer> iterator() {

        return new FLayerIterator();
    }

    class FLayerIterator implements Iterator<Integer> {
        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < FLayerDef.this.size();
        }

        @Override
        public Integer next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return FLayerDef.this.get(index++);
        }
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

        public int get() {

            return this.value;
        }

        public void inc() {

            this.value++;
        }

        public void set(int value) {

            this.value = value;
        }

        public void reset() {

            this.value = 0;
        }
    }
}

