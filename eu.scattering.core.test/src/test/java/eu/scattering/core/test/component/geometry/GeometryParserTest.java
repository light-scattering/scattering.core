package eu.scattering.core.test.component.geometry;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryParser;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.number.complex.FComplex;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("GeometryParser")
public class GeometryParserTest {

    @Test
    @DisplayName("Parse FPoint")
    void parseFPoint() {
        GeometryParser parser = factory.getGeometryParser();

        FPoint fPoint = factory.getFPoint(1, 2, 3);
        JSONObject fPointJSON = fPoint.toJSON();

        Geometry fPointParsed = parser.parse(fPointJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fPointParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertFalse(fPointParsed instanceof FVector,
                        "The element type is erroneous"),
                () -> assertEquals(fPoint, fPointParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fPoint, fPointParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FVector")
    void parseFVector() {
        GeometryParser parser = factory.getGeometryParser();

        FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
        JSONObject fPointJSON = fVector.toJSON();

        Geometry fVectorParsed = parser.parse(fPointJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fVectorParsed instanceof FVector,
                        "The element type is erroneous"),
                () -> assertFalse(fVectorParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fVector, fVectorParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fVector, fVectorParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FDraft")
    void parseFDraft() {
        GeometryParser parser = factory.getGeometryParser();

        FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3, 4, 5, 6));
        JSONObject fDraftJSON = fDraft.toJSON();

        Geometry fDraftParsed = parser.parse(fDraftJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fDraftParsed instanceof FDraft,
                        "The element type is erroneous"),
                () -> assertFalse(fDraftParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fDraft, fDraftParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fDraft, fDraftParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FLine")
    void parseFLine() {
        GeometryParser parser = factory.getGeometryParser();

        FLine fLine = factory.getRefFLine(factory.getFVector(1, 2, 3, 4, 5, 6));
        JSONObject fLineJSON = fLine.toJSON();

        Geometry fLineParsed = parser.parse(fLineJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fLineParsed instanceof FLine,
                        "The element type is erroneous"),
                () -> assertFalse(fLineParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fLine, fLineParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fLine, fLineParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FPlane")
    void parseFPlane() {
        GeometryParser parser = factory.getGeometryParser();

        FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 2, 3, 4, 5, 6));
        JSONObject fPlaneJSON = fPlane.toJSON();

        Geometry fPlaneParsed = parser.parse(fPlaneJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fPlaneParsed instanceof FPlane,
                        "The element type is erroneous"),
                () -> assertFalse(fPlaneParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fPlane, fPlaneParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fPlane, fPlaneParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FRay")
    void parseFRay() {
        GeometryParser parser = factory.getGeometryParser();

        FRay fRay = factory.getRefFRay(factory.getFVector(1, 2, 3, 4, 5, 6));
        JSONObject fRayJSON = fRay.toJSON();

        Geometry fRayParsed = parser.parse(fRayJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fRayParsed instanceof FRay,
                        "The element type is erroneous"),
                () -> assertFalse(fRayParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fRay, fRayParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fRay, fRayParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FSegment")
    void parseFSegment() {
        GeometryParser parser = factory.getGeometryParser();

        FSegment fSegment = factory.getRefFSegment(factory.getFVector(1, 2, 3, 4, 5, 6));
        JSONObject fSegmentJSON = fSegment.toJSON();

        Geometry fSegmentParsed = parser.parse(fSegmentJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fSegmentParsed instanceof FSegment,
                        "The element type is erroneous"),
                () -> assertFalse(fSegmentParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fSegment, fSegmentParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fSegment, fSegmentParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FSphere")
    void parseFSphere() {
        GeometryParser parser = factory.getGeometryParser();

        FSphere fSphere = factory.getFSphere(1);
        JSONObject fSphereJSON = fSphere.toJSON();

        Geometry fSphereParsed = parser.parse(fSphereJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fSphereParsed instanceof FSphere,
                        "The element type is erroneous"),
                () -> assertFalse(fSphereParsed instanceof FPoint,
                        "The element type is erroneous"),
                () -> assertEquals(fSphere, fSphereParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fSphere, fSphereParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse FAssembly")
    void parseFAssembly() {
        GeometryParser parser = factory.getGeometryParser();

        FAssembly<Geometry> fAssembly = factory.getFAssembly();

        FPoint fPoint = factory.getFPoint(1, 2, 3);
        FVector fVector = factory.getFVector(4, 5, 6, 7, 8, 9);
        FDraft fDraft = factory.getFDraft();
        FLine fLine = factory.getRefFLine(factory.getFVector(-1, -2, -3, -4, -5, -6));
        FPlane fPlane = factory.getRefFPlane(factory.getFVector(-6, -5, -4, -3, -2, -1));
        FRay fRay = factory.getRefFRay(factory.getFVector(1, -2, 3, -4, 5, -6));
        FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 2, -3, 4, -5, 6));
        FSphere fSphere = factory.getFSphere(2);

        fAssembly.registerWithCheck(fPoint);
        fAssembly.registerWithCheck(fVector);
        fAssembly.registerWithCheck(fDraft);
        fAssembly.registerWithCheck(fLine);
        fAssembly.registerWithCheck(fPlane);
        fAssembly.registerWithCheck(fRay);
        fAssembly.registerWithCheck(fSegment);
        fAssembly.registerWithCheck(fSphere);

        JSONObject fAssemblyJSON = fAssembly.toJSON();

        Geometry fAssemblyParsed = parser.parse(fAssemblyJSON);

        Assertions.assertAll("Validate parser results",
                () -> assertTrue(fAssemblyParsed instanceof FAssembly,
                        "The element type is erroneous"),
                () -> assertEquals(fAssembly, fAssemblyParsed,
                        "Elements should be equal"),
                () -> assertNotSame(fAssembly, fAssemblyParsed,
                        "Elements should not be the same object")
        );
    }

    @Test
    @DisplayName("Parse (exception)")
    void parseException() {
        GeometryParser parser = factory.getGeometryParser();

        FComplex fComplex = factory.getFComplex(1, 2);
        JSONObject fComplexJSON = fComplex.toJSON();

        assertThrows(IllegalArgumentException.class, () -> parser.parse(fComplexJSON),
                "The element should not be parsable");
    }
}
