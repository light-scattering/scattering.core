package eu.scattering.core.transfer.container.buffer.array.concrete;

import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayConsumer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FArrayDef implements FArray {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_MAIN = "array";
    private static final String JSON_LENGTH = "length";
    private static final String JSON_CAPACITY = "capacity";

    private final int capacity;

    private final double[] d0;
    private final double[] d1;
    private final double[] d2;

    private final double[] value;

    private int index;

    private FArrayDef(int capacity) {
        this.capacity = capacity;

        this.index = 0;

        this.d0 = new double[capacity];
        this.d1 = new double[capacity];
        this.d2 = new double[capacity];

        this.value = new double[capacity];
    }

    public static FArray create(int capacity) {

        return new FArrayDef(capacity);
    }

    public static FArray create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        int capacity = json.getInt(JSON_CAPACITY);

        return new FArrayDef(capacity);
    }

    @Override
    public void add(double d0, double d1, double d2) {

        addWithValue(d0, d1, d2, 0);
    }

    @Override
    public void add(FPos3D pos) {

        add(pos.getD0(), pos.getD1(), pos.getD2());
    }

    @Override
    public void addWithValue(double d0, double d1, double d2, double value) {

        if (index > capacity) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.d0[index] = d0;
        this.d1[index] = d1;
        this.d2[index] = d2;

        this.value[index] = value;

        index++;
    }

    @Override
    public void addWithValue(FPos3D pos, double value) {

        addWithValue(pos.getD0(), pos.getD1(), pos.getD2(), value);
    }

    @Override
    public void addWithValue(FPos4D pos) {

        addWithValue(pos.getD0(), pos.getD1(), pos.getD2(), pos.getD3());
    }

    @Override
    public FPos4D getFPos4D(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return factoryExt.getFPos4D(this.d0[index], this.d1[index], this.d2[index], this.value[index]);
    }

    @Override
    public FPos3D getFPos3D(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return factoryExt.getFPos3D(this.d0[index], this.d1[index], this.d2[index]);
    }

    @Override
    public double getD0(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.d0[index];
    }

    @Override
    public double getD1(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.d1[index];
    }

    @Override
    public double getD2(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.d2[index];
    }

    @Override
    public double getValue(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[index];
    }

    @Override
    public int size() {

        return index;
    }

    @Override
    public int capacity() {

        return this.capacity;
    }

    @Override
    public void forEach(FArrayConsumer consumer) {

        for (int i = 0 ; i < index ; i++) {
            consumer.apply(i, d0[i], d1[i], d2[i], value[i]);
        }
    }

    @Override
    public void reset() {

        index = 0;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        json.put(JSON_CAPACITY, capacity);
        json.put(JSON_LENGTH, index);

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 1;

        for (int i = 0 ; i < index ; i++) {
            hashCode = 31 * hashCode + (int) (d0[i] + d1[i] + d2[i] + value[i]);
        }

        return hashCode;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    //--------------------------------------------------

    @Override
    public Iterator<double[]> iterator() {

        return new FArrayIterator();
    }

    class FArrayIterator implements Iterator<double[]> {
        private final double[] data = new double[4];
        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < FArrayDef.this.size();
        }

        @Override
        public double[] next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            this.data[0] = FArrayDef.this.getD0(index);
            this.data[1] = FArrayDef.this.getD1(index);
            this.data[2] = FArrayDef.this.getD2(index);
            this.data[3] = FArrayDef.this.getValue(index);

            this.index++;

            return this.data;
        }
    }
}
