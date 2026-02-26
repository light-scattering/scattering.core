package eu.scattering.core.design.storage.transfer.polynomial;

import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import org.json.JSONObject;

public interface FPolyFactory {

    FPoly getFPoly(double... core);

    FPoly getFPoly(JSONObject json);
}
