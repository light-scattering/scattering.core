package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiFunction;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FAssemblyDef<T extends Geometry> implements FAssembly<T> {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "assembly";
    private static final String JSON_VAL = "val";

    private final GeometryFactory factorySelf;

    private final List<T> elements;
    private final List<FPoint> fPoints = new ArrayList<>();

    private FAssemblyDef(GeometryFactory factorySelf, List<T> elements) {

        this.factorySelf = factorySelf;
        this.elements = elements;

        for (T geometry : elements) {
            registerFPoints(geometry);
        }
    }

    public static <T extends Geometry> FAssembly<T> create(GeometryFactory factorySelf, List<? extends T> elements) {

        return new FAssemblyDef<>(factorySelf, new ArrayList<>(elements));
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public List<T> asList() {

        return this.elements;
    }

    @Override
    public boolean registerWithCheck(T element) {

        boolean newGeometry = registerGeometry(element);
        boolean newFPoint = registerFPoints(element);

        return newGeometry || newFPoint;
    }

    @Override
    public boolean registerWithCheck(T element, BiFunction<T, Collection<T>, Boolean> rule) {

        if (rule.apply(element, this.elements)) {
            return registerWithCheck(element);
        }

        return false;
    }

    @Override
    public boolean registerWithCheck(Collection<? extends T> elements) {
        boolean updated = false;

        for (T element : elements) {
            if (registerWithCheck(element)) {
                updated = true;
            }
        }

        return updated;
    }

    @Override
    public boolean registerWithCheck(Collection<? extends T> elements, BiFunction<T, Collection<T>, Boolean> rule) {
        boolean updated = false;

        for (T element : elements) {
            if (rule.apply(element, this.elements)) {
                if (registerWithCheck(element)) {
                    updated = true;
                }
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
    public FAssembly<T> register(T element, BiFunction<T, Collection<T>, Boolean> rule) {

        if (rule.apply(element, this.elements)) {
            register(element);
        }

        return this;
    }

    @Override
    public FAssembly<T> register(Collection<? extends T> elements) {

        elements.forEach(this::registerWithCheck);

        return this;
    }

    @Override
    public FAssembly<T> register(Collection<? extends T> elements, BiFunction<T, Collection<T>, Boolean> rule) {

        for (T element : elements) {
            if (rule.apply(element, this.elements)) {
                register(element);
            }
        }

        return this;
    }

    @Override
    public boolean deregisterWithCheck(T element) {
        boolean updated = this.elements.remove(element);

        if (updated) {
            this.fPoints.clear();

            this.elements.forEach(this::registerFPoints);
        }

        return updated;
    }

    @Override
    public boolean deregisterWithCheck(Collection<T> elements) {
        boolean updated = false;

        for (T element : elements) {
            if (this.elements.remove(element)) {
                updated = true;
            }
        }

        if (updated) {
            this.fPoints.clear();

            this.elements.forEach(this::registerFPoints);
        }

        return updated;
    }

    @Override
    public FAssembly<T> deregister(T element) {
        boolean updated = this.elements.remove(element);

        if (updated) {
            this.fPoints.clear();

            this.elements.forEach(this::registerFPoints);
        }

        return this;
    }

    @Override
    public FAssembly<T> deregister(Collection<T> elements) {
        boolean updated = false;

        for (T element : elements) {
            if (this.elements.remove(element)) {
                updated = true;
            }
        }

        if (updated) {
            this.fPoints.clear();

            this.elements.forEach(this::registerFPoints);
        }

        return this;
    }

    private boolean registerGeometry(T candidate) {

        return register(this.elements, candidate);
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

        if (asList().size() != arg.asList().size()) {
            return false;
        }

        Collection<T> geoCopy = new ArrayList<>(arg.asList());

        main:
        for (T geoL : asList()) {
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

        if (asList().size() != arg.asList().size()) {
            return false;
        }

        Collection<T> geoCopy = new ArrayList<>(arg.asList());

        main:
        for (T geoL : asList()) {
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

        for (T element : this.elements) {
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

        this.elements.forEach(e -> json.append(JSON_VAL, e.toJSON()));

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int code = 0;

        for (Geometry geo : asList()) {
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

        this.elements.clear();
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

    @Override
    public FAssembly<T> translate(double x, double y, double z) {

        this.fPoints.forEach(e -> e.add(x, y, z));

        return this;
    }

    @Override
    public FAssembly<T> translate(FPos3D offset) {

        return translate(offset.getD0(), offset.getD1(), offset.getD2());
    }

    @Override
    public FAssembly<T> scale(double factor) {

        this.fPoints.forEach(e -> e.mulFactor(factor));

        return this;
    }

    @Override
    public double getVolume() {
        FLayerCounter fLayer = factory.getFLayerCounter();

        List<Shape> list = this.elements.stream()
                .filter(e -> e instanceof Shape)
                .map(e -> (Shape) e)
                .distinct()
                .toList();

        Queue<Shape> queue = new LinkedList<>(list);

        queue.poll();

        double volume = 0;
        for (Shape element : list) {

            if (element.overlaps(queue) == 0) {
                volume += element.getVolume();
            } else {
                fLayer.reset();
                element.fillOverlapLayer(fLayer, queue);
                volume += fLayer.get() * Math.pow(element.getDelta(), 3);
            }

            queue.poll();
        }

        return volume;
    }

    @Override
    public double getSurface() {
        FLayerCounter fLayer = factory.getFLayerCounter();

        List<Shape> list = this.elements.stream()
                .filter(e -> e instanceof Shape)
                .map(e -> (Shape) e)
                .distinct()
                .toList();

        double surface = 0;
        for (Shape element : list) {

            if (element.overlaps(list) == 0) {
                surface += element.getSurface();
            } else {
                fLayer.reset();
                element.fillSurfaceLayer(fLayer, list);
                surface += fLayer.get() * Math.pow(element.getDelta(), 2);
            }
        }

        return surface;
    }

    @Override
    public double getVolumeLayer(double[] layers) {
        FLayerCounter fLayer = factory.getFLayerCounter();

        List<Shape> list = getUniqueShapes();

        int maxLayer = Integer.MIN_VALUE;
        double volUnit;
        for (Shape shape : list) {
            fLayer.reset();

            shape.fillVolumeLayer(fLayer, list);
            volUnit = Math.pow(shape.getDelta(), 3);

            if (fLayer.size() > maxLayer) {
                maxLayer = fLayer.size();
            }

            for (int i = 0 ; i < fLayer.size() ; i++) {
                layers[i] += fLayer.get(i) * volUnit;
            }
        }

        double volTotal = 0;

        for (int i = 0 ; i < maxLayer ; i++) {
            volTotal += layers[i];
        }

        return volTotal;
    }

    private List<Shape> getUniqueShapes() {

        return  this.elements.stream()
                .filter(e -> e instanceof Shape)
                .map(e -> (Shape) e)
                .distinct()
                .toList();
    }

    @Override
    public FPos3D getSpatialCenter() {
        FPairPos3D dimension = getRange();

        double x = (dimension.getPosA().getD0() + dimension.getPosB().getD0()) * 0.5;
        double y = (dimension.getPosA().getD1() + dimension.getPosB().getD1()) * 0.5;
        double z = (dimension.getPosA().getD2() + dimension.getPosB().getD2()) * 0.5;

        return factory.getFPos3D(x, y, z);
    }

    @Override
    public FAssembly<T> resetSpatialCenter() {
        FPos3D center = getSpatialCenter();

        translate(-center.getD0(), -center.getD1(), -center.getD2());

        return this;
    }

    @Override
    public FPairPos3D getRange() {
        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxZ = -Double.MAX_VALUE;

        for (T element : asList()) {

            if (element instanceof FDraft) {
                throw new IllegalStateException("The dimension of FDraft is undefined");
            }

            if (element instanceof FLine) {
                throw new IllegalStateException("The dimension of FLine is undefined");
            }

            if (element instanceof FPlane) {
                throw new IllegalStateException("The dimension of FPlane is undefined");
            }

            if (element instanceof FRay) {
                throw new IllegalStateException("The dimension of FRay is undefined");
            }

            if (element instanceof Shape shape) {
                if (shape.getCenterX() - shape.getRadius() < minX) {
                    minX = shape.getCenterX() - shape.getRadius();
                }
                if (shape.getCenterX() + shape.getRadius() > maxX) {
                    maxX = shape.getCenterX() + shape.getRadius();
                }

                if (shape.getCenterY() - shape.getRadius() < minY) {
                    minY = shape.getCenterY() - shape.getRadius();
                }
                if (shape.getCenterY() + shape.getRadius() > maxY) {
                    maxY = shape.getCenterY() + shape.getRadius();
                }

                if (shape.getCenterZ() - shape.getRadius() < minZ) {
                    minZ = shape.getCenterZ() - shape.getRadius();
                }
                if (shape.getCenterZ() + shape.getRadius() > maxZ) {
                    maxZ = shape.getCenterZ() + shape.getRadius();
                }
            } else {
                for (FPoint point : element.toFPoints()) {
                    if (point.getX() < minX) {
                        minX = point.getX();
                    }
                    if (point.getX() > maxX) {
                        maxX = point.getX();
                    }

                    if (point.getY() < minY) {
                        minY = point.getY();
                    }
                    if (point.getY() > maxY) {
                        maxY = point.getY();
                    }

                    if (point.getZ() < minZ) {
                        minZ = point.getZ();
                    }
                    if (point.getZ() > maxZ) {
                        maxZ = point.getZ();
                    }
                }
            }
        }

        return factory.getFPairPos3D(minX, minY, minZ, maxX, maxY, maxZ);
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

            return index < FAssemblyDef.this.asList().size();
        }

        @Override
        public T next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return FAssemblyDef.this.asList().get(index++);
        }
    }
}
