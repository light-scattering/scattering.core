package eu.scattering.core.design.core.immutable.position;

public interface FPositionFactory {

    FPosition getFPosition(int x, int y, int z);
    FPosition getFPosition(String structure);
}
