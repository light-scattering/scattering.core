package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyFactory;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class FAssemblyDef<T extends Geometry> implements FAssembly<T> {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "asb";
    private static final String JSON_VAL = "val";

    private final FAssemblyFactory factorySelf;

    private final Collection<T> elements = new ArrayList<>();
    private final Collection<FPoint> units = new ArrayList<>();

    private FAssemblyDef(FAssemblyFactory factorySelf) {

        this.factorySelf = factorySelf;
    }

    public static <T extends Geometry> FAssembly<T> create(FAssemblyFactory factorySelf) {

        return new FAssemblyDef<T>(factorySelf);
    }

    @Override
    public Collection<FPoint> explode() {

        return this.units;
    }

    @Override
    public boolean register(T element) {

        boolean newGeometry = registerGeometry(element);
        boolean newFPoint = registerFPoints(element);

        return newGeometry || newFPoint;
    }

    @Override
    public void applyUnit(Consumer<FPoint> consumer) {

        for (FPoint fPoint : this.units) {
            consumer.accept(fPoint);
        }
    }

    @Override
    public void applyGeometry(Consumer<T> consumer) {

        for (T geometry : this.elements) {
            consumer.accept(geometry);
        }
    }

    private boolean registerGeometry(T candidate) {

        return register(this.elements, candidate);
    }

    private boolean registerFPoints(T candidate) {
        boolean hasFPoint = false;

        for (FPoint fPoint : candidate.explode()) {
            if (register(this.units, fPoint)) {
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




//------------------

    @Override
    public FAssembly<T> self() {

        return this;
    }



    @Override
    @SuppressWarnings("unchecked")
    public FAssembly<T> copy() {
        FAssembly<T> copy = supplyFAssembly();

        for (T element : this.elements) {
            copy.register((T) element.replicate());
        }

        return copy;
    }

    @Override
    public Geometry replicate() {

        return copy();
    }

    @Override
    public FAssembly<T> set(JSONObject json) {
        return null;
    }




    @Override
    public JSONObject toJSON() {
        return null;
    }

    // -------------------------------------------------------------------------------------------------

    private FAssembly<T> supplyFAssembly() {

        return factorySelf.getFAssembly();
    }
}
