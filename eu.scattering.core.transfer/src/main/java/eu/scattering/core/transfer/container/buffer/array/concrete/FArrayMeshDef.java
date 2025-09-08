package eu.scattering.core.transfer.container.buffer.array.concrete;

import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.buffer.array.utils.FArrayMeshConsumer;
import eu.scattering.core.transfer.container.storage.FPos3DI.FPos3DI;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class FArrayMeshDef<T> implements FArrayMesh<T> {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_MAIN = "array";
    private static final String JSON_TYPE = "type";
    private static final String JSON_SIZE = "size";
    private static final String JSON_CAPACITY = "capacity";

    private final int capacity;

    private final int[] d0;
    private final int[] d1;
    private final int[] d2;

    private List<T> meta;

    private int index;

    private FArrayMeshDef(int capacity) {
        this.capacity = capacity;

        this.index = 0;

        this.d0 = new int[capacity];
        this.d1 = new int[capacity];
        this.d2 = new int[capacity];

        setUpMeta();
    }

    public static <T> FArrayMesh<T> create(int capacity) {

        return new FArrayMeshDef<>(capacity);
    }

    @Override
    public void add(int d0, int d1, int d2) {

        if (index > capacity) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.d0[index] = d0;
        this.d1[index] = d1;
        this.d2[index] = d2;

        this.meta.set(index, null);

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

        this.d0[index] = d0;
        this.d1[index] = d1;
        this.d2[index] = d2;

        this.meta.set(index, meta);

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

        return factoryExt.getFPos3DI(this.d0[index], this.d1[index], this.d2[index]);
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
    public T getMeta(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current array size");
        }

        return this.meta.get(index);
    }

    @Override
    public int findIndex(int d0, int d1, int d2) {

        for (int i = 0 ; i < this.index ; i++) {

            if (this.d0[i] != d0) {
                continue;
            }

            if (this.d1[i] != d1) {
                continue;
            }

            if (this.d2[i] != d2) {
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
    public void forEach(FArrayMeshConsumer<T> consumer) {

        for (int i = 0; i < index; i++) {
            consumer.apply(i, d0[i], d1[i], d2[i], meta.get(i));
        }
    }

    @Override
    public void clear() {

        index = 0;
    }

    @Override
    public int deduplicate() {
        Set<FPos3DI> elements = new HashSet<>();

        int i = 0, j = 0;
        while (i < this.index) {
            boolean isUnique = elements.add(getFPos3DI(i));

            if (isUnique) {
                this.meta.set(j, this.meta.get(i));

                this.d0[j] = this.d0[i];
                this.d1[j] = this.d1[i];
                this.d2[j] = this.d2[i];

                j++;
            }

            i++;
        }

        elements.clear();

        this.index = j;

        return i - j;
    }

    @Override
    public int deduplicate(BiFunction<T, T, Boolean> collision) {
        Set<FPos3DI> elements = new HashSet<>();

        int i = 0, j = 0;
        while (i < this.index) {
            FPos3DI position = getFPos3DI(i);
            boolean isPositionUnique = elements.add(position);

            if (isPositionUnique) {
                this.meta.set(j, this.meta.get(i));

                this.d0[j] = this.d0[i];
                this.d1[j] = this.d1[i];
                this.d2[j] = this.d2[i];

                j++;
            } else {
                int indexOld = findIndex(position);

                T metaNew = this.meta.get(i);
                T metaOld = getMeta(indexOld);

                if (collision.apply(metaOld, metaNew)) {
                    this.meta.set(indexOld, metaNew);
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

        for (int i = 0; i < index; i++) {
            hashCode = 31 * hashCode + d0[i] + d1[i] + d2[i];
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
                if (d0[i] != fArray.getD0(i) || d1[i] != fArray.getD1(i) || d2[i] != fArray.getD2(i)) {
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

    private void setUpMeta() {

        if (this.meta != null) {
            return;
        }

        this.meta = new ArrayList<>(capacity);

        while(this.meta.size() < capacity) {
            this.meta.add(null);
        }
    }
}
