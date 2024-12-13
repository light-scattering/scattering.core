package eu.scattering.core.impl.production.core.engine.random;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.core.data.position.FPos3D;
import eu.scattering.core.design.core.engine.random.FRandom;
import org.json.JSONObject;

import java.util.concurrent.ThreadLocalRandom;

public class FRandomProd implements FRandom {

    private final Factory factory;

    private FRandomProd(Factory factory) {

        this.factory = factory;
    }

    public static FRandomProd create(Factory factory) {

        return new FRandomProd(factory);
    }

    @Override
    public long getSeed() {

        return 0;
    }

    @Override
    public FPos3D getPositionOnUnitSphere() {
        double x1 = 0, x2 = 0, f = 10;

        while (f >= 1) {
            x1 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
            x2 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
            f = x1 * x1 + x2 * x2;
        }

        double x = 2 * x1 * Math.sqrt(1 - f);
        double y = 2 * x2 * Math.sqrt(1 - f);
        double z = 1 - 2 * f;

        return factory.getFPos3D(x, y, z);
    }

    @Override
    public JSONObject exportToJSON() {
        return null;
    }
}
