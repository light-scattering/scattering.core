package eu.scattering.core.design.main.box.position;

public interface FPositionFactory {

    FPosition getFPosition(int x, int y, int z);

    FPosition getFPosition(String structure);

}
