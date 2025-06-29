package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Consumer;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FAssemblyDef<T extends Geometry> implements FAssembly<T> {
    private static final String JSON_MAIN = "assembly";
    private static final String JSON_VAL = "val";

    private final GeometryFactory factorySelf;

    private final List<T> geometries = new ArrayList<>();
    private final List<FPoint> fPoints = new ArrayList<>();

    private FAssemblyDef(GeometryFactory factorySelf) {

        this.factorySelf = factorySelf;
    }

    public static <T extends Geometry> FAssembly<T> create(GeometryFactory factorySelf) {

        return new FAssemblyDef<>(factorySelf);
    }

    public static <T extends Geometry> FAssembly<T> create(GeometryFactory factorySelf, T[] elements) {
        FAssemblyDef<T> fAssembly = new FAssemblyDef<>(factorySelf);

        for (T element : elements) {
            fAssembly.register(element);
        }

        return fAssembly;
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public List<T> getListGeometry() {

        return this.geometries;
    }

    @Override
    public boolean registerWithCheck(T element) {

        boolean newGeometry = registerGeometry(element);
        boolean newFPoint = registerFPoints(element);

        return newGeometry || newFPoint;
    }

    @Override
    public boolean registerWithCheck(Collection<T> elements) {
        boolean updated = false;

        for (T element : elements) {
            if (registerWithCheck(element)) {
                updated = true;
            }
        }

        return updated;
    }

    @Override
    public FAssembly<T> register(T element) {

        registerWithCheck(element);

        return this;
    }

    @Override
    public FAssembly<T> register(Collection<T> elements) {

        elements.forEach(this::registerWithCheck);

        return this;
    }

    @Override
    public boolean deregisterWithCheck(T element) {
        boolean updated = this.geometries.remove(element);

        if (updated) {
            this.fPoints.clear();

            this.geometries.forEach(this::registerFPoints);
        }

        return updated;
    }

    @Override
    public boolean deregisterWithCheck(Collection<T> elements) {
        boolean updated = false;

        for (T element : elements) {
            if (this.geometries.remove(element)) {
                updated = true;
            }
        }

        if (updated) {
            this.fPoints.clear();

            this.geometries.forEach(this::registerFPoints);
        }

        return updated;
    }

    @Override
    public FAssembly<T> deregister(T element) {
        boolean updated = this.geometries.remove(element);

        if (updated) {
            this.fPoints.clear();

            this.geometries.forEach(this::registerFPoints);
        }

        return this;
    }

    @Override
    public FAssembly<T> deregister(Collection<T> elements) {
        boolean updated = false;

        for (T element : elements) {
            if (this.geometries.remove(element)) {
                updated = true;
            }
        }

        if (updated) {
            this.fPoints.clear();

            this.geometries.forEach(this::registerFPoints);
        }

        return this;
    }

    @Override
    public FAssembly<T> applyFPoint(Consumer<FPoint> consumer) {

        for (FPoint fPoint : this.fPoints) {
            consumer.accept(fPoint);
        }

        return this;
    }

    @Override
    public FAssembly<T> applyGeometry(Consumer<T> consumer) {

        for (T geometry : this.geometries) {
            consumer.accept(geometry);
        }

        return this;
    }

    private boolean registerGeometry(T candidate) {

        return register(this.geometries, candidate);
    }

    private boolean registerFPoints(T candidate) {
        boolean hasFPoint = false;

        for (FPoint fPoint : candidate.toFPoints()) {
            if (register(this.fPoints, fPoint)) {
                hasFPoint = true;
            }
        }

        return hasFPoint;
    }

    private <U> boolean register(Collection<U> collection, U candidate) {

        if (contains(collection, candidate)) {
            return false;
        }

        collection.add(candidate);

        return true;
    }

    private <U> boolean contains(Collection<U> collection, U candidate) {

        for (U item : collection) {
            if (item == candidate) {
                return true;
            }
        }

        return false;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FAssembly<T> arg) {

        if (getListGeometry().size() != arg.getListGeometry().size()) {
            return false;
        }

        Collection<T> geoCopy = new ArrayList<>(arg.getListGeometry());

        main:
        for (T geoL : getListGeometry()) {
            for (T geoE : geoCopy) {
                if (geoL.isExact(geoE)) {
                    geoCopy.remove(geoE);
                    continue main;
                }
            }

            return false;
        }

        return geoCopy.isEmpty();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean isExact(Geometry arg) {

        if (arg instanceof FAssembly) {
            return isExact((FAssembly) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(FAssembly<T> arg) {

        if (getListGeometry().size() != arg.getListGeometry().size()) {
            return false;
        }

        Collection<T> geoCopy = new ArrayList<>(arg.getListGeometry());

        main:
        for (T geoL : getListGeometry()) {
            for (T geoE : geoCopy) {
                if (geoL.isSimilar(geoE)) {
                    geoCopy.remove(geoE);
                    continue main;
                }
            }

            return false;
        }

        return geoCopy.isEmpty();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FAssembly) {
            return isSimilar((FAssembly) arg);
        }

        return false;
    }

    @Override
    public FAssembly<T> self() {

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FAssembly<T> copy() {
        FAssembly<T> copy = supplyFAssembly();

        for (T element : this.geometries) {
            copy.registerWithCheck((T) element.copyGeometry());
        }

        return copy;
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        this.geometries.forEach(e -> json.append(JSON_VAL, e.toJSON()));

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int code = 0;

        for (Geometry geo : getListGeometry()) {
            code += geo.hashCode();
        }

        return code;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Geometry)) {
            return false;
        }

        return isExact((Geometry) object);
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Collection<FPoint> toFPoints() {

        return this.fPoints;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FAssembly<T> set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        this.geometries.clear();
        this.fPoints.clear();

        GeometryParser parser = factorySelf.getGeometryParser();

        JSONArray candidates = json.getJSONArray(JSON_VAL);

        for (int i = 0 ; i < candidates.length() ; i++) {
            JSONObject candidate = candidates.getJSONObject(i);
            Geometry geometry = parser.parse(candidate);

            registerWithCheck((T) geometry);
        }

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    private FAssembly<T> supplyFAssembly() {

        return factorySelf.getFAssembly();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Iterator<T> iterator() {

        return new FAssemblyIteratorDef();
    }

    class FAssemblyIteratorDef implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < FAssemblyDef.this.getListGeometry().size();
        }

        @Override
        public T next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return FAssemblyDef.this.getListGeometry().get(index++);
        }
    }
}
