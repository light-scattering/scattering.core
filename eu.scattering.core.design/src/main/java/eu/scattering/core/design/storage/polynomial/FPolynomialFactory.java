package eu.scattering.core.design.storage.polynomial;

import eu.scattering.core.design.storage.polynomial.variant.FPoly;
import org.json.JSONObject;

public interface FPolynomialFactory {

    FPoly getFPoly(double... core);

    FPoly getFPoly(JSONObject json);
}
