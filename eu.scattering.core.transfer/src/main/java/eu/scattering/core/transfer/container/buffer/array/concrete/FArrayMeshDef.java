package eu.scattering.core.transfer.container.buffer.array.concrete;

import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayMeshConsumer;
import eu.scattering.core.transfer.container.storage.FPos3DI.FPos3DI;
import eu.scattering.core.transfer.container.storage.FPos4DI.FPos4DI;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FArrayMeshDef implements FArrayMesh {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final String JSON_MAIN = "arrayMesh";
    private static final String JSON_LENGTH = "length";
    private static final String JSON_CAPACITY = "capacity";

    private final int capacity;

    private final int[] d0;
    private final int[] d1;
    private final int[] d2;

    private final double[] value;

    private int index;

    private FArrayMeshDef(int capacity) {
        this.capacity = capacity;

        this.index = 0;

        this.d0 = new int[capacity];
        this.d1 = new int[capacity];
        this.d2 = new int[capacity];

        this.value = new double[capacity];
    }

    public static FArrayMesh create(int capacity) {

        return new FArrayMeshDef(capacity);
    }

    public static FArrayMesh create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        int capacity = json.getInt(JSON_CAPACITY);

        return new FArrayMeshDef(capacity);
    }

    @Override
    public void add(int d0, int d1, int d2) {

        addWithValue(d0, d1, d2, 0);
    }

    @Override
    public void add(FPos3DI pos) {

        add(pos.getD0(), pos.getD1(), pos.getD2());
    }

    @Override
    public void addWithValue(int d0, int d1, int d2, double value) {

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
    public void addWithValue(FPos3DI pos, double value) {

        addWithValue(pos.getD0(), pos.getD1(), pos.getD2(), value);
    }

    @Override
    public void addWithValue(FPos4DI pos) {

        addWithValue(pos.getD0(), pos.getD1(), pos.getD2(), pos.getD3());
    }

    @Override
    public FPos3DI getFPos3DI(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return factory.getFPos3DI(this.d0[index], this.d1[index], this.d2[index]);
    }

    @Override
    public FPos4DI getFPos4DI(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return factory.getFPos4DI(this.d0[index], this.d1[index], this.d2[index], (int) this.value[index]);
    }

    @Override
    public int getD0(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.d0[index];
    }

    @Override
    public int getD1(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.d1[index];
    }

    @Override
    public int getD2(int index) {

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
    public void forEach(FArrayMeshConsumer consumer) {

        for (int i = 0; i < index; i++) {
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

        for (int i = 0; i < index; i++) {
            hashCode = 31 * hashCode + (int) (d0[i] + d1[i] + d2[i] + value[i]);
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FArrayMeshDef fArray) {

            if (index != fArray.index) {
                return false;
            }

            for (int i = 0; i < index; i++) {
                if (value[i] != fArray.getValue(i) ||
                        d0[i] != fArray.getD0(i) ||
                        d1[i] != fArray.getD1(i) ||
                        d2[i] != fArray.getD2(i)) {
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
    public Iterator<double[]> iterator() {

        return new FArrayMeshIterator();
    }

    class FArrayMeshIterator implements Iterator<double[]> {
        private final double[] data = new double[4];
        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < FArrayMeshDef.this.size();
        }

        @Override
        public double[] next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            this.data[0] = FArrayMeshDef.this.getD0(index);
            this.data[1] = FArrayMeshDef.this.getD1(index);
            this.data[2] = FArrayMeshDef.this.getD2(index);
            this.data[3] = FArrayMeshDef.this.getValue(index);

            this.index++;

            return this.data;
        }
    }
}
