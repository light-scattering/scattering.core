package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiConsumer;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FAggregateDef implements FAggregate {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_MAIN = "aggregate";
    private static final String JSON_PARTICLES = "particles";

    private final Map<String, double[]> materialDensity = new HashMap<>();

    private FAssembly<Shape> particles;
    private FArray<FMetaData> elements;

    private FAggregateDef(FAssembly<Shape> particles, FArray<FMetaData> elements) {

        this.particles = particles;
        this.elements = elements;
    }

    public static FAggregate create(FAssembly<Shape> particles, FArray<FMetaData> elements) {

        return new FAggregateDef(particles, elements);
    }

    @Override
    public FAssembly<Shape> getParticles() {

        return this.particles.copy();
    }

    @Override
    public FAggregate setParticles(FAssembly<Shape> particles) {

        this.particles = particles.copy();

        return this;
    }

    @Override
    public FAggregate setMaterialDensity(String tag, double... density) {

        this.materialDensity.put(tag, density);

        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_PARTICLES, this.particles.toJSON());

        return json;
    }

    @Override
    public double getSurface() {
        FLayerCounter fLayer = factoryExt.getFLayerCounter();

        List<Shape> field = getUniqueShapes();

        double surface = 0;
        double surfaceUnit;
        for (Shape element : field) {

            if (element.overlaps(field) == 0) {
                surface += element.getSurfaceAlgebraic();
            } else {
                fLayer.reset();

                surfaceUnit = element.fillSurfaceLayerOverlap(fLayer, field);

                surface += fLayer.get(0) * surfaceUnit;
            }
        }

        return surface;
    }

    @Override
    public double getSurface(double[] layers) {
        int layerCount = valLayerCount(getParticles());

        FLayerCounter fLayer = factoryExt.getFLayerCounter();

        List<Shape> field = getUniqueShapes();

        double surface = 0;
        double surfaceUnit;
        for (Shape element : field) {

            if (element.overlaps(field) == 0) {
                for (int i = 0 ; i < element.getLayerCount() ; i++) {
                    layers[i] += element.getLayerSurface(i);
                }
            } else {
                fLayer.reset();

                surfaceUnit = element.fillSurfaceLayer(fLayer, field);

                for (int i = 0 ; i < element.getLayerCount() ; i++) {
                    layers[i] += fLayer.get(i) * surfaceUnit;
                }
            }
        }

        for (int i = 0 ; i < layerCount ; i++) {
            surface += layers[i];
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
        double volumeUnit;
        for (Shape element : field) {

            if (element.overlaps(queue) == 0) {
                volume += element.getVolumeAlgebraic();
            } else {
                fLayer.reset();

                volumeUnit = element.fillVolumeLayerOverlap(fLayer, queue);

                volume += fLayer.get() * volumeUnit;
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

    @Override
    public FArrayMesh<FMetaData> getVolumeMesh() {
        double unit = valDelta(this.particles);

        this.elements.clear();

        List<Shape> field = getUniqueShapes();

        for (Shape shape : field) {
            shape.fillVolumeArray(this.elements, field);
        }

        FArrayMesh<FMetaData> mesh = this.elements.toFArrayMesh(unit);

        mesh.deduplicate((a, b) -> b.getLayer() < a.getLayer());

        return mesh;
    }

    private int getVol(FLayerCounter fLayer, List<? extends Shape> field, Shape shape, double[] volume) {

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

    private int getVolMsh(FLayerCounter fLayer, List<? extends Shape> field, Shape shape, double[] volume) {
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

        for (Shape shape : this.particles) {
            getOFac(fLayer, this.particles, shape, layer);
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

        shape.fillVolumeLayerOverlap(fLayer, field);

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
        int oFacCount = 0;
        double oFacTotal = 0;
        Shape shapeA, shapeB;
        for (int i = 0 ; i < this.particles.size() ; i++) {
            shapeA = this.particles.asList().get(i);

            for (int j = i + 1 ; j < this.particles.size() ; j++) {
                shapeB = this.particles.asList().get(j);

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
        List<Shape> candidates = new ArrayList<>();

        Queue<Shape> queue = new LinkedList<>(this.particles.asList());

        queue.poll();

        for (Shape shape : this.particles) {
            candidates.clear();

            shape.touchesOrOverlaps(queue, candidates);

            candidates.forEach(e -> consumer.accept(shape, e));

            queue.poll();
        }
    }

    private List<Shape> getUniqueShapes() {

        return this.particles.asList().stream()
                .distinct()
                .toList();
    }

    private int valLayerCount(FAssembly<? extends Shape> fAssembly) {
        List<? extends Shape> particles = fAssembly.asList();

        if (particles.size() == 0) {
            return -1;
        }

        int layerCount = particles.get(0).getLayerCount();

        for (Shape shape : particles) {

            if (shape.getLayerCount() != layerCount) {
                throw new IllegalStateException("The number of layers must be equal for all particles");
            }
        }

        return layerCount;
    }

    private double valDelta(FAssembly<? extends Shape> fAssembly) {
        List<? extends Shape> particles = fAssembly.asList();

        if (particles.size() == 0) {
            return -1;
        }

        double delta = particles.get(0).getDelta();

        for (Shape shape : particles) {

            if (shape.getDelta() != delta) {
                throw new IllegalStateException("The value of delta must be equal for all particles");
            }
        }

        return delta;
    }

    //--------------------------------------------------

    @Override
    public FAssembly<Shape> getRefParticles() {

        return this.particles;
    }

    @Override
    public FAggregate setRefParticles(FAssembly<Shape> particles) {

        this.particles = particles;

        return this;
    }

    @Override
    public FArray<FMetaData> getRefElements() {

        return this.elements;
    }

    @Override
    public FAggregate setRefElements(FArray<FMetaData> elements) {

        this.elements = elements;

        return this;
    }
}
