package eu.scattering.core.impl.storage;

import eu.scattering.core.design.storage.StorageAspectSave;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public class StorageAspectSaveDef implements StorageAspectSave {

    private StorageAspectSaveDef() {
    }

    public static StorageAspectSave create() {

        return new StorageAspectSaveDef();
    }

    //--------------------------------------------------

    @Override
    public String toCLI(FPos3D fPos3D) {

        return "[" + fPos3D.getD0() + "," + fPos3D.getD1() + "," + fPos3D.getD2() + "]";
    }
}
