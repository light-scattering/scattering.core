package eu.scattering.core.impl.storage.mutable.buffer;

import eu.scattering.core.design.storage.mutable.buffer.array.FArrayMesh;
import eu.scattering.core.design.storage.mutable.buffer.array.utils.FArrayMeshConsumer;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPos3DI.FPos3DI;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class FArrayMeshDef<T> implements FArrayMesh<T> {
    private static final int DEF_CAPACITY = 1_000_000;

    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_MAIN = "array";
    private static final String JSON_TYPE = "type";
    private static final String JSON_SIZE = "size";
    private static final String JSON_CAPACITY = "capacity";

    private final int capacity;

    private final int[][] value;
    private final Object[] meta;

    private int index;

    private double data;

    private FArrayMeshDef() {

        this(DEF_CAPACITY);
    }

    private FArrayMeshDef(int capacity) {
        this.index = 0;

        this.capacity = capacity;

        this.value = new int[3][this.capacity];
        this.meta = new Object[this.capacity];
    }

    public static <T> FArrayMesh<T> create() {

        return new FArrayMeshDef<>();
    }

    public static <T> FArrayMesh<T> create(int capacity) {

        return new FArrayMeshDef<>(capacity);
    }

    @Override
    public double getData() {

        return this.data;
    }

    @Override
    public void setData(double data) {

        this.data = data;
    }

    @Override
    public void add(int d0, int d1, int d2) {

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
    public void add(FPos3DI pos) {

        add(pos.getD0(), pos.getD1(), pos.getD2());
    }

    @Override
    public void addWithMeta(int d0, int d1, int d2, T meta) {

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
    public void addWithMeta(FPos3DI pos, T meta) {

        addWithMeta(pos.getD0(), pos.getD1(), pos.getD2(), meta);
    }

    @Override
    public FPos3DI getFPos3DI(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return factoryExt.getFPos3DI(
                this.value[0][index], this.value[1][index], this.value[2][index]
        );
    }

    @Override
    public int getD0(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[0][index];
    }

    @Override
    public int getD1(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[1][index];
    }

    @Override
    public int getD2(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.value[2][index];
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
    public int findIndex(int d0, int d1, int d2) {

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
    public int findIndex(FPos3DI pos) {

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
    public void forEach(FArrayMeshConsumer<T> consumer) {

        for (int i = 0; i < index; i++) {
            consumer.apply(
                    i, this.value[0][i], this.value[1][i], this.value[2][i], (T) this.meta[i]
            );
        }
    }

    @Override
    public void clear() {

        this.index = 0;
    }

    @Override
    public int deduplicate() {
        Set<FPos3DI> elements = new HashSet<>();

        int i = 0, j = 0;
        while (i < this.index) {
            boolean isUnique = elements.add(getFPos3DI(i));

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
        Set<FPos3DI> elements = new HashSet<>();

        int i = 0, j = 0;
        while (i < this.index) {
            FPos3DI position = getFPos3DI(i);
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
            hashCode = 31 * hashCode + this.value[0][i] + this.value[1][i] + this.value[2][i];
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

                if (this.value[0][i] != fArray.getD0(i)) {
                    return false;
                }

                if (this.value[1][i] != fArray.getD1(i)) {
                    return false;
                }

                if (this.value[2][i] != fArray.getD2(i)) {
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
