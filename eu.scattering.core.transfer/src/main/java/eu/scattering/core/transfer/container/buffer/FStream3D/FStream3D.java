package eu.scattering.core.transfer.container.buffer.FStream3D;

import eu.scattering.core.transfer.container.buffer.Buffer;
import org.json.JSONArray;
import org.json.JSONObject;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FStream3D implements Buffer<FStream3D> {
    private static final String JSON_MAIN = "str3D";
    private static final String JSON_LENGTH = "length";
    private static final String JSON_VAL = "data";

    private final int length;

    private final double[] d0;
    private final double[] d1;
    private final double[] d2;

    private final double[] value;

    private int index;

    private FStream3D(int length) {
        this.length = length;

        this.index = 0;

        this.d0 = new double[length];
        this.d1 = new double[length];
        this.d2 = new double[length];

        this.value = new double[length];
    }

    private FStream3D(double[] d0, double[] d1, double[] d2, double[] value) {
        this.length = value.length;

        this.index = value.length;

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;

        this.value = value;
    }

    protected static FStream3D create(int length) {

        return new FStream3D(length);
    }

    protected static FStream3D create(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        int index = json.getInt(JSON_LENGTH);

        double[] d0 = new double[index];
        double[] d1 = new double[index];
        double[] d2 = new double[index];

        double[] value = new double[index];

        JSONArray structure = json.getJSONArray(JSON_VAL);

        for (int pos = 0, i = 0 ; i < structure.length() ; pos++) {
            d0[pos] = structure.getDouble(i++);
            d1[pos] = structure.getDouble(i++);
            d2[pos] = structure.getDouble(i++);

            value[pos] = structure.getDouble(i++);
        }

        return new FStream3D(d0, d1, d2, value);
    }

    public void add(double d0, double d1, double d2) {

        add(d0, d1, d2, 0);
    }

    public void add(double d0, double d1, double d2, double value) {

        if (index > length) {
            throw new IndexOutOfBoundsException("The index exceeded the size limit");
        }

        this.d0[index] = d0;
        this.d1[index] = d1;
        this.d2[index] = d2;

        this.value[index] = value;

        index++;
    }

    public double getD0(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current stream size");
        }

        return this.d0[index];
    }

    public double getD1(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current stream size");
        }

        return this.d1[index];
    }

    public double getD2(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current stream size");
        }

        return this.d2[index];
    }

    public double getValue(int index) {

        if (index >= this.index) {
            throw new IndexOutOfBoundsException("The index exceeded the current stream size");
        }

        return this.value[index];
    }

    public int getNumberOfElements() {

        return index;
    }

    public void iterate(FStream3DConsumer consumer) {

        for (int i = 0 ; i < index ; i++) {
            consumer.apply(i, d0[i], d1[i], d2[i], value[i]);
        }
    }

    public void reset() {

        index = 0;
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        json.put(JSON_LENGTH, index);

        for (int i = 0 ; i < index ; i++) {
            json.append(JSON_VAL, d0[i]);
            json.append(JSON_VAL, d1[i]);
            json.append(JSON_VAL, d2[i]);
            json.append(JSON_VAL, value[i]);
        }

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
    public boolean equals(Object object) {

        if (object instanceof FStream3D) {
            FStream3D fStream = (FStream3D) object;

            if (index != fStream.index) {
                return false;
            }

            for (int i = 0 ; i < index ; i++) {
                if (value[i] != fStream.getValue(i) ||
                        d0[i] != fStream.getD0(i) ||
                        d1[i] != fStream.getD1(i) ||
                        d2[i] != fStream.getD2(i)) {
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
    public interface FStream3DConsumer {

        void apply(int index, double d0, double d1, double d2, double value);
    }
}
