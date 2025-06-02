package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FAssemblyDef<T extends Geometry> implements FAssembly<T> {
    private static final String JSON_MAIN = "assembly";
    private static final String JSON_VAL = "val";

    private final GeometryFactory factorySelf;

    private final Collection<T> geometries = new ArrayList<>();
    private final Collection<FPoint> fPoints = new ArrayList<>();

    private FAssemblyDef(GeometryFactory factorySelf) {

        this.factorySelf = factorySelf;
    }

    public static <T extends Geometry> FAssembly<T> create(GeometryFactory factorySelf) {

        return new FAssemblyDef<>(factorySelf);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public Collection<T> getGeometries() {

        return this.geometries;
    }

    @Override
    public boolean register(T element) {

        boolean newGeometry = registerGeometry(element);
        boolean newFPoint = registerFPoints(element);

        return newGeometry || newFPoint;
    }

    @Override
    public void applyFPoint(Consumer<FPoint> consumer) {

        for (FPoint fPoint : this.fPoints) {
            consumer.accept(fPoint);
        }
    }

    @Override
    public void applyGeometry(Consumer<T> consumer) {

        for (T geometry : this.geometries) {
            consumer.accept(geometry);
        }
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

        if (getGeometries().size() != arg.getGeometries().size()) {
            return false;
        }

        Collection<T> geoCopy = new ArrayList<>(arg.getGeometries());

        main:
        for (T geoL : getGeometries()) {
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

        if (getGeometries().size() != arg.getGeometries().size()) {
            return false;
        }

        Collection<T> geoCopy = new ArrayList<>(arg.getGeometries());

        main:
        for (T geoL : getGeometries()) {
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
            copy.register((T) element.copyGeometry());
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

        for (Geometry geo : getGeometries()) {
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

            register((T) geometry);
        }

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    private FAssembly<T> supplyFAssembly() {

        return factorySelf.getFAssembly();
    }
}
