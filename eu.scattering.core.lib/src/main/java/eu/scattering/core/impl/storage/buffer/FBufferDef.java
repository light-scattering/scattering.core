package eu.scattering.core.impl.storage.buffer;

import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.util.FBufferConsumer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.impl.storage.mesh.FMeshDef;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class FBufferDef<T> implements FBuffer<T> {
    private static final String JSON_MAIN = "array";
    private static final String JSON_TYPE = "type";
    private static final String JSON_SIZE = "size";
    private static final String JSON_CAPACITY = "capacity";

    private final TransferFactory factoryExt;

    private final int capacity;

    private final double[][] value;
    private final Object[] meta;

    private int index;

    private FBufferDef(TransferFactory factoryExt, int capacity) {

        if (capacity < 1) {
            throw new IllegalArgumentException("The capacity must be greater than zero");
        }

        this.factoryExt = factoryExt;

        this.index = 0;

        this.capacity = capacity;

        this.value = new double[4][this.capacity];
        this.meta = new Object[this.capacity];
    }

    public static <T> FBuffer<T> create(TransferFactory factoryExt, int capacity) {

        return new FBufferDef<>(factoryExt, capacity);
    }

    @Override
    public void add(double d0, double d1, double d2) {

        if (index > capacity) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.value[0][index] = d0;
        this.value[1][index] = d1;
        this.value[2][index] = d2;

        this.meta[index] = null;

        index++;
    }

    @Override
    public void add(FPos3D pos) {

        add(pos.getD0(), pos.getD1(), pos.getD2());
    }

    @Override
    public void addWithMeta(double d0, double d1, double d2, T meta) {

        if (index > capacity) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.value[0][index] = d0;
        this.value[1][index] = d1;
        this.value[2][index] = d2;

        this.meta[index] = meta;

        index++;
    }

    @Override
    public void addWithMeta(FPos3D pos, T meta) {

        addWithMeta(pos.getD0(), pos.getD1(), pos.getD2(), meta);
    }

    @Override
    public void addWithData(double d0, double d1, double d2, double data) {

        if (index > capacity) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.value[0][index] = d0;
        this.value[1][index] = d1;
        this.value[2][index] = d2;

        this.value[3][index] = data;

        index++;
    }

    @Override
    public void addWithData(FPos3D pos, double data) {

        addWithData(pos.getD0(), pos.getD1(), pos.getD2(), data);
    }

    @Override
    public void addWithDataAndMeta(double d0, double d1, double d2, double data, T meta) {

        if (index > capacity) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.value[0][index] = d0;
        this.value[1][index] = d1;
        this.value[2][index] = d2;
        this.value[3][index] = data;

        this.meta[index] = meta;

        index++;
    }

    @Override
    public void addWithDataAndMeta(FPos3D pos, double data, T meta) {

        addWithDataAndMeta(pos.getD0(), pos.getD1(), pos.getD2(), data, meta);
    }

    @Override
    public FPos3D getFPos3D(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return factoryExt.getFPos3D(
                this.value[0][index], this.value[1][index], this.value[2][index]
        );
    }

    @Override
    public double getD0(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[0][index];
    }

    @Override
    public double getD1(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[1][index];
    }

    @Override
    public double getD2(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[2][index];
    }

    @Override
    public double getData(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[3][index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getMeta(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return (T) this.meta[index];
    }

    @Override
    public int findIndex(double d0, double d1, double d2) {

        for (int i = 0 ; i < this.index ; i++) {

            if (this.value[0][i] != d0) {
                continue;
            }

            if (this.value[1][i] != d1) {
                continue;
            }

            if (this.value[2][i] != d2) {
                continue;
            }

            return i;
        }

        return -1;
    }

    @Override
    public int findIndex(FPos3D pos) {

        return findIndex(pos.getD0(), pos.getD1(), pos.getD2());
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
    @SuppressWarnings("unchecked")
    public void forEach(FBufferConsumer<T> consumer) {

        for (int i = 0; i < index; i++) {
            consumer.apply(
                    i, this.value[0][i], this.value[1][i], this.value[2][i], this.value[3][i], (T) this.meta[i]
            );
        }
    }

    @Override
    public void clear() {

        this.index = 0;
    }

    @Override
    public int deduplicate() {
        Set<FPos3D> elements = new HashSet<>();

        int i = 0, j = 0;
        while (i < this.index) {
            boolean isUnique = elements.add(getFPos3D(i));

            if (isUnique) {
                this.meta[j] = this.meta[i];

                this.value[0][j] = this.value[0][i];
                this.value[1][j] = this.value[1][i];
                this.value[2][j] = this.value[2][i];

                j++;
            }

            i++;
        }

        elements.clear();

        this.index = j;

        return i - j;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int deduplicate(BiFunction<T, T, Boolean> collision) {
        Set<FPos3D> elements = new HashSet<>();

        int i = 0, j = 0;
        while (i < this.index) {
            FPos3D position = getFPos3D(i);
            boolean isPositionUnique = elements.add(position);

            if (isPositionUnique) {
                this.meta[j] = this.meta[i];

                this.value[0][j] = this.value[0][i];
                this.value[1][j] = this.value[1][i];
                this.value[2][j] = this.value[2][i];

                j++;
            } else {
                int indexOld = findIndex(position);

                T metaNew = (T) this.meta[i];
                T metaOld = getMeta(indexOld);

                if (collision.apply(metaOld, metaNew)) {
                    this.meta[indexOld] = metaNew;
                }
            }

            i++;
        }

        elements.clear();

        this.index = j;

        return i - j;
    }

    @Override
    public FMesh<T> toFArrayMesh() {
        double dataGlobal = isDataUnique();

        if (dataGlobal < 0) {
            throw new IllegalStateException("The data value must be consistent for all elements");
        }

        FMesh<T> fArrayMesh = FMeshDef.create(this.factoryExt, size());

        double factor = 1d / dataGlobal;

        forEach((index, d0, d1, d2, _dataLocal, meta) -> fArrayMesh.addWithMeta(
                (int) Math.round(d0 * factor),
                (int) Math.round(d1 * factor),
                (int) Math.round(d2 * factor),
                meta
        ));

        fArrayMesh.setData(dataGlobal);

        return fArrayMesh;
    }

    //--------------------------------------------------

    private double isDataUnique() {

        if (size() == 0) {
            throw new IllegalStateException("The array cannot be empty");
        }

        double data = getData(0);

        for (int i = 1 ; i < size() ; i++) {
            if (getData(i) != data) {
                return -1;
            }
        }

        return data;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        json.put(JSON_TYPE, "integer");
        json.put(JSON_CAPACITY, capacity);
        json.put(JSON_SIZE, index);

        return json;
    }

    //--------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 1;

        for (int i = 0; i < this.index; i++) {
            hashCode = 31 * hashCode + (int) (this.value[0][i] + this.value[1][i] + this.value[2][i] + this.value[3][i]);
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FBufferDef fArray) {

            if (index != fArray.index) {
                return false;
            }

            for (int i = 0; i < index; i++) {

                if (this.value[0][i] != fArray.getD0(i)) {
                    return false;
                }

                if (this.value[1][i] != fArray.getD1(i)) {
                    return false;
                }

                if (this.value[2][i] != fArray.getD2(i)) {
                    return false;
                }

                if (this.value[3][i] != fArray.getData(i)) {
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
}
