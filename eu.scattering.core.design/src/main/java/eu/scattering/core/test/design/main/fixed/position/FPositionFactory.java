package eu.scattering.core.test.design.main.fixed.position;

public interface FPositionFactory {

    FPosition getFPosition(int x, int y, int z);
    FPosition getFPosition(String structure);
}
