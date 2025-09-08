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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FAssemblyDef<T extends Geometry> implements FAssembly<T> {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

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
    public <U extends T> FAssembly<T> mutate(Class<U> type, Consumer<U> action) {

        this.elements.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .forEach(action);

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
    public double getSurface() {
        FLayerCounter fLayer = factoryExt.getFLayerCounter();

        List<Shape> field = getUniqueShapes();

        double surface = 0;
        for (Shape element : field) {

            if (element.overlaps(field) == 0) {
                surface += element.getSurfaceAlgebraic();
            } else {
                fLayer.reset();
                element.fillSurfaceLayer(fLayer, field);
                surface += fLayer.get() * Math.pow(element.getDelta(), 2);
            }
        }

        return surface;
    }

    @Override
    public double getSurface(double[] layers) {
        FLayerCounter fLayer = factoryExt.getFLayerCounter();

        List<Shape> field = getUniqueShapes();

        double surface = 0;
        for (Shape element : field) {

            if (element.overlaps(field) == 0) {
                surface += element.getSurfaceAlgebraic();
            } else {
                fLayer.reset();
                element.fillSurfaceLayer(fLayer, field);
                surface += fLayer.get() * Math.pow(element.getDelta(), 2);
            }
        }

        return surface;
    }

    @Override
    public double getVolume() {
        FLayerCounter fLayer = factoryExt.getFLayerCounter();

        List<Shape> field = getUniqueShapes();

        Queue<Shape> queue = new LinkedList<>(field);

        queue.poll();

        double volume = 0;
        for (Shape element : field) {

            if (element.overlaps(queue) == 0) {
                volume += element.getVolumeAlgebraic();
            } else {
                fLayer.reset();
                element.fillVolumeOverlapLayer(fLayer, queue);
                volume += fLayer.get() * Math.pow(element.getDelta(), 3);
            }

            queue.poll();
        }

        return volume;
    }

    @Override
    public double getVolume(double[] layers) {
        FLayerCounter fLayer = factoryExt.getFLayerCounter();

        List<Shape> field = getUniqueShapes();

        int layerCountMax = Integer.MIN_VALUE;
        for (Shape shape : field) {
            int layerCount = getVol(fLayer, field, shape, layers);

            if (layerCount > layerCountMax) {
                layerCountMax = layerCount;
            }
        }

        double volTotal = 0;

        for (int i = 0 ; i < layerCountMax ; i++) {
            volTotal += layers[i];
        }

        return volTotal;
    }

    private int getVol(FLayerCounter fLayer, Iterable<? extends Shape> field, Shape shape, double[] volume) {

        if (shape.overlaps(field) == 0) {
            return getVolAlg(shape, volume);
        }

        return getVolMsh(fLayer, field, shape, volume);
    }

    private int getVolAlg(Shape shape, double[] volume) {

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume[i] += shape.getLayerVolume(i);
        }

        return shape.getLayerCount();
    }

    private int getVolMsh(FLayerCounter fLayer, Iterable<? extends Shape> field, Shape shape, double[] volume) {
        fLayer.reset();

        shape.fillVolumeLayer(fLayer, field);
        double volUnit = Math.pow(shape.getDelta(), 3);

        for (int i = 0 ; i < fLayer.size() ; i++) {
            volume[i] += fLayer.get(i) * volUnit;
        }

        return fLayer.size();
    }

    @Override
    public double getOverlapFactor() {
        FLayerCounter fLayer = factoryExt.getFLayerCounter();
        List<Double> layer = new ArrayList<>();

        List<Shape> field = getShapes();

        for (Shape shape : field) {
            getOFac(fLayer, field, shape, layer);
        }

       return getOFacPost(layer);
    }

    private void getOFac(FLayerCounter fLayer, Iterable<? extends Shape> field, Shape shape, List<Double> volume) {

        if (shape.overlaps(field) == 0) {
            getOFacAlg(shape, volume);
        } else {
            getOFacMsh(fLayer, field, shape, volume);
        }
    }

    private void getOFacAlg(Shape shape, List<Double> layer) {

        if (layer.size() < 1) {
            layer.add(0d);
        }

        layer.set(0, layer.get(0) + shape.getVolumeAlgebraic());
    }

    private void getOFacMsh(FLayerCounter fLayer, Iterable<? extends Shape> field, Shape shape, List<Double> volume) {
        fLayer.reset();

        shape.fillVolumeOverlapLayer(fLayer, field);

        double volUnit = Math.pow(shape.getDelta(), 3);

        while (fLayer.size() > volume.size()) {
            volume.add(0d);
        }

        for (int i = 0 ; i < fLayer.size() ; i++) {
            volume.set(i, volume.get(i) + (fLayer.get(i) * volUnit));
        }
    }

    private double getOFacPost(List<Double> volume) {
        double volTmp;
        double volTotal = 0;
        double volOverlap = 0;

        for (int i = 0 ; i < volume.size() ; i++) {
            volTmp = volume.get(i) / (i + 1);

            volTotal += volTmp;

            if (i > 0) {
                volOverlap += volTmp;
            }
        }

        return volOverlap / volTotal;
    }

    @Override
    public double getOverlapFactorLegacy() {
        List<Shape> field = getShapes();

        int oFacCount = 0;
        double oFacTotal = 0;
        Shape shapeA, shapeB;
        for (int i = 0 ; i < field.size() ; i++) {
            shapeA = field.get(i);

            for (int j = i + 1 ; j < field.size() ; j++) {
                shapeB = field.get(j);

                if (shapeA == shapeB) {
                    continue;
                }

                if (shapeA.repels(shapeB)) {
                    continue;
                }

                oFacTotal += getOFacLeg(shapeA, shapeB);
                oFacCount += 1;
            }
        }

        if (oFacCount == 0) {
            return 0;
        }

        return oFacTotal / oFacCount;
    }

    private double getOFacLeg(Shape shapeA, Shape shapeB) {

        double dist = shapeA.getDistCenter(shapeB);
        double oFacRaw = 1 - (dist / (shapeA.getRadius() + shapeB.getRadius()));

        if (oFacRaw > 1) {
            return 1;
        }

        if (oFacRaw < 0) {
            return 0;
        }

        return oFacRaw;
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

            validateSpatialGeometry(element);

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

        return factoryExt.getFPairPos3D(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public void setSpatialCenter(FPoint center) {
        FPairPos3D dimension = getRange();

        double x = (dimension.getPosA().getD0() + dimension.getPosB().getD0()) * 0.5;
        double y = (dimension.getPosA().getD1() + dimension.getPosB().getD1()) * 0.5;
        double z = (dimension.getPosA().getD2() + dimension.getPosB().getD2()) * 0.5;

        center.set(x, y, z);
    }

    @Override
    public void setSphericalCenter(FPoint center) {
        setSpatialCenter(center);

        double learningRate = 0.1;
        double learningRateChange = 0.9;
        int stepMax = 100;

        for (int step = 0; step < stepMax; step++) {
            FPoint candidate = null;
            double distMax = 0;
            double radius = 0;

            for (Geometry element : asList()) {

                validateSpatialGeometry(element);

                if (element instanceof Shape shape) {
                    double dist = center.getDistance(shape.getRefCenter()) + shape.getRadius();

                    if (dist > distMax) {
                        radius = shape.getRadius();
                        distMax = dist;
                        candidate = shape.getRefCenter();
                    }
                } else {

                    for (FPoint point : element.toFPoints()) {
                        double dist = center.getDistance(point);

                        if (dist > distMax) {
                            radius = 0;
                            distMax = dist;
                            candidate = point;
                        }
                    }
                }
            }

            if (candidate != null) {
                center.setDistance(candidate,(distMax - radius) * (1 - learningRate));
            }

            if (learningRate < EPSILON) {
                return;
            }

            learningRate *= learningRateChange;
        }
    }

    @Override
    public boolean isCompact() {
        List<Shape> field = getUniqueShapes();
        List<Shape> processed = new ArrayList<>();

        if (field.size() == 0) {
            return false;
        }

        isCompactCheck(field.get(0), field, processed);

        return field.size() == processed.size();
    }

    private void isCompactCheck(Shape shape, List<Shape> field, List<Shape> processed) {

        if (processed.contains(shape)) {
            return;
        }

        processed.add(shape);

        List<Shape> candidates = new ArrayList<>();
        shape.touchesOrOverlaps(field, candidates);

        for (Shape candidate : candidates) {
            isCompactCheck(candidate, field, processed);
        }
    }

    @Override
    public void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {
        List<Shape> field = getShapes();
        List<Shape> candidates = new ArrayList<>();

        Queue<Shape> queue = new LinkedList<>(field);

        queue.poll();

        for (Shape shape : field) {
            candidates.clear();

            shape.touchesOrOverlaps(queue, candidates);

            candidates.forEach(e -> consumer.accept(shape, e));

            queue.poll();
        }
    }

    private List<Shape> getShapes() {

        return  this.elements.stream()
                .filter(e -> e instanceof Shape)
                .map(e -> (Shape) e)
                .toList();
    }

    private List<Shape> getUniqueShapes() {

        return  this.elements.stream()
                .filter(e -> e instanceof Shape)
                .map(e -> (Shape) e)
                .distinct()
                .toList();
    }

    private void validateSpatialGeometry(Geometry element) {

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
