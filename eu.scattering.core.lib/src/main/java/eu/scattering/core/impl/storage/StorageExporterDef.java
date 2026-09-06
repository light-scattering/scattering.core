package eu.scattering.core.impl.storage;

import eu.scattering.core.design.storage.StorageExporter;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

public class StorageExporterDef implements StorageExporter {

    private StorageExporterDef() {
    }

    public static StorageExporter create() {

        return new StorageExporterDef();
    }

    //--------------------------------------------------

    @Override
    public String toCLI(FPos3D fPos3D) {

        return "[" + fPos3D.getD0() + "," + fPos3D.getD1() + "," + fPos3D.getD2() + "]";
    }

    @Override
    public String toCLI(FPairPos3D fPairPos3D) {

        return "[" + toCLI(fPairPos3D.getPosA()) + "," + toCLI(fPairPos3D.getPosB()) + "]";
    }
}
