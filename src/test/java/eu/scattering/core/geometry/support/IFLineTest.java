package eu.scattering.core.geometry.support;

import eu.scattering.core.exception.ProjectionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import static eu.scattering.core.Configuration.jitter;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFLine")
public class IFLineTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFLineBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            IFLine fLine = FactoryGeometry.getIFLine();

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate positions)")
        void constructValidatePositions() {
            IFLine fLine = FactoryGeometry.getIFLine();

            assertEquals(FactoryGeometry.getIFVector(), fLine.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector (validate positions)")
        void constructWithIFVectorValidatePositions() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointBase = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refAX, fLine.getOrigin().getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fLine.getOrigin().getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fLine.getOrigin().getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fLine.getOrigin().getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fLine.getOrigin().getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fLine.getOrigin().getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFVector (throw NullPointerException)")
        void constructWithIFVectorThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> FactoryGeometry.getIFLine(null),
                    "The reference cannot be null" );
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            IFLine fLine = FactoryGeometry.getIFLine(fVectorA);

            IFLine fLineRef = fLine.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            IFLine fLine = FactoryGeometry.getIFLine(HelperRandom.getTestVector());

            assertThrows(NullPointerException.class, () -> fLine.setOriginRef(null),
                    "The reference cannot be null" );
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());

            assertEquals(fVector, fLine.getOrigin(), "The IFVector positions are erroneous");
        }

    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = FactoryGeometry.getIFLine().importFromJSON(fLineA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fLineA, fLineB,
                            "IFLine references should point at different objects"),
                    () -> assertEquals(fLineA.getOrigin(), fLineB.getOrigin(),
                            "The origin of IFLines should be exact")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isExact(fLineB), "IFLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "IFLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "IFLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "IFLines should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (throw NullPointerException)")
        void isExactThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertThrows(NullPointerException.class,
                    () -> fLine.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(1.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "IFLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "IFLines should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            assertEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two identical IFLines should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = FactoryGeometry.getIFLine(HelperRandom.getTestVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different IFLines should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = fLineA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "IFLines represent different objects"),
                    () -> assertEquals(fLineA, fLineB,
                            "IFLines should have the same values")
            );
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFLineAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project());

            assertTrue(FactoryGeometry.getIFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (validate references)")
        void projectValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.project());

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Project (validate positions)")
        void projectValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.project());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Project (position base)")
        void projectPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project (position head)")
        void projectPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project - Line")
        void projectModeLine() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project(IFLine.Mode.LINE));

            assertTrue(FactoryGeometry.getIFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project - Line (validate references)")
        void projectModeLineValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.project(IFLine.Mode.LINE));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Project - Line (validate positions)")
        void projectModeLineValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.project(IFLine.Mode.LINE));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Project - Line (position base)")
        void projectModeLinePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.project(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Project - Line (position head)")
        void projectModeLinePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.project(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Project - Segment")
        void projectModeSegment() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project(IFLine.Mode.SEGMENT));

            assertTrue(FactoryGeometry.getIFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project - Segment (validate references)")
        void projectModeSegmentValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.project(IFLine.Mode.SEGMENT));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Project - Segment (validate positions)")
        void projectModeSegmentValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.project(IFLine.Mode.SEGMENT));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Project - Segment (position base)")
        void projectModeSegmentPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.project(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Project - Segment (position head)")
        void projectModeSegmentPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.project(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Project - Ray")
        void projectModeRay() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project(IFLine.Mode.RAY));

            assertTrue(FactoryGeometry.getIFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project - Ray (validate references)")
        void projectModeRayValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.project(IFLine.Mode.RAY));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Project - Ray (validate positions)")
        void projectModeRayValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.project(IFLine.Mode.RAY));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Project - Ray (position base)")
        void projectModeRayPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.project(IFLine.Mode.RAY)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Project - Ray (position head)")
        void projectModeRayPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.project(IFLine.Mode.RAY));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect());

            assertTrue(FactoryGeometry.getIFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate references)")
        void reflectValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.reflect());

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect (validate positions)")
        void reflectValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Reflect (position base)")
        void reflectPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect (position head)")
        void reflectPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect - Line")
        void reflectModeLine() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.LINE));

            assertTrue(FactoryGeometry.getIFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect - Line (validate references)")
        void reflectModeLineValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.reflect(IFLine.Mode.LINE));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect - Line (validate positions)")
        void reflectModeLineValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.LINE));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Reflect - Line (position base)")
        void reflectModeLinePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Reflect - Line (position head)")
        void reflectModeLinePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Reflect - Segment")
        void reflectModeSegment() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.SEGMENT));

            assertTrue(FactoryGeometry.getIFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect - Segment (validate references)")
        void reflectModeSegmentValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.reflect(IFLine.Mode.SEGMENT));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect - Segment (validate positions)")
        void reflectModeSegmentValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.SEGMENT));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Reflect - Segment (position base)")
        void reflectModeSegmentPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.reflect(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Reflect - Segment (position head)")
        void reflectModeSegmentPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.reflect(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Reflect - Ray")
        void reflectModeRay() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.RAY));

            assertTrue(FactoryGeometry.getIFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect - Ray (validate references)")
        void reflectModeRayValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.reflect(IFLine.Mode.RAY));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect - Ray (validate positions)")
        void reflectModeRayValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.RAY));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Reflect - Ray (position base)")
        void reflectModeRayPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.reflect(IFLine.Mode.RAY)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Reflect - Ray (position head)")
        void reflectModeRayPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.reflect(IFLine.Mode.RAY));
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fPoint.extLog(fLine.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fPoint.extLog(fLine.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (validate positions)")
        void isPartOfValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Location (position base)")
        void isPartOfPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(-4, -4, -4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location (position head)")
        void isPartOfPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, 4, 4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location - Line")
        void isPartOfModeLine() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fPoint.extLog(fLine.isPartOf(IFLine.Mode.LINE)).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location - Line (fail)")
        void isPartOfModeLineFail() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fPoint.extLog(fLine.isPartOf(IFLine.Mode.LINE)).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location - Line (validate positions)")
        void isPartOfModeLineValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf(IFLine.Mode.LINE));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Location - Line (position base)")
        void isPartOfModeLinePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(-4, -4, -4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Location - Line (position head)")
        void isPartOfModeLinePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, 4, 4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Location - Segment")
        void isPartOfModeSegment() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fPoint.extLog(fLine.isPartOf(IFLine.Mode.SEGMENT)).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location - Segment (fail)")
        void isPartOfModeSegmentFail() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fPoint.extLog(fLine.isPartOf(IFLine.Mode.SEGMENT)).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location - Segment (validate positions)")
        void isPartOfModeSegmentValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf(IFLine.Mode.SEGMENT));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Location - Segment (position base)")
        void isPartOfModeSegmentPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(-4, -4, -4).addY(0.5 * jitter);

            assertThrows(ProjectionException.class, () -> fPoint.extLog(fLine.isPartOf(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Location - Line (position head)")
        void isPartOfModeSegmentPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, 4, 4).addY(0.5 * jitter);

            assertThrows(ProjectionException.class, () -> fPoint.extLog(fLine.isPartOf(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Location - Ray")
        void isPartOfModeRay() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fPoint.extLog(fLine.isPartOf(IFLine.Mode.RAY)).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location - Ray (fail)")
        void isPartOfModeRayFail() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fPoint.extLog(fLine.isPartOf(IFLine.Mode.RAY)).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location - Ray (validate positions)")
        void isPartOfModeRayValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf(IFLine.Mode.RAY));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Location - Ray (position base)")
        void isPartOfModeRayPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(-4, -4, -4).addY(0.5 * jitter);

            assertThrows(ProjectionException.class, () -> fPoint.extLog(fLine.isPartOf(IFLine.Mode.RAY)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Location - Ray (position head)")
        void isPartOfModeRayPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, 4, 4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf(IFLine.Mode.RAY));
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extVal(fLine.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate positions)")
        void getDistanceValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.extVal(fLine.getDistance());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Get distance (position base)")
        void getDistancePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.extVal(fLine.getDistance());
        }

        @Test
        @DisplayName("Distance (position head)")
        void getDistancePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.extVal(fLine.getDistance());
        }

        @Test
        @DisplayName("Get distance - Line")
        void getDistanceModeLine() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extVal(fLine.getDistance(IFLine.Mode.LINE)).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance - Line (validate positions)")
        void getDistanceModeLineValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.extVal(fLine.getDistance(IFLine.Mode.LINE));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Get distance - Line (position base)")
        void getDistanceModeLinePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.extVal(fLine.getDistance(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Get distance - Line (position head)")
        void distanceModeLinePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.extVal(fLine.getDistance(IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Get distance - Segment")
        void getDistanceModeSegment() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extVal(fLine.getDistance(IFLine.Mode.SEGMENT)).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance - Segment (validate positions)")
        void getDistanceModeSegmentValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.extVal(fLine.getDistance(IFLine.Mode.SEGMENT));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Get distance - Segment (position base)")
        void getDistanceModeSegmentPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.extVal(fLine.getDistance(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Get distance - Segment (position head)")
        void getDistanceModeSegmentPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.extVal(fLine.getDistance(IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Distance - Ray")
        void getDistanceModeRay() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extVal(fLine.getDistance(IFLine.Mode.RAY)).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance - Ray (validate positions)")
        void getDistanceModeRayValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.extVal(fLine.getDistance(IFLine.Mode.RAY));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Get distance - Ray (position base)")
        void getDistanceModeRayPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.extVal(fLine.getDistance(IFLine.Mode.RAY)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Get distance - Ray (position head)")
        void getDistanceModeRayPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

           fPoint.extVal(fLine.getDistance(IFLine.Mode.RAY));
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1));

            assertTrue(Math.abs(fPoint.extVal(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (position base)")
        void setDistancePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance (position head)")
        void setDistancePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance (validate positions)")
        void setDistanceValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.setDistance(1));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Set distance (validate references)")
        void setDistanceValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.setDistance(1));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set distance (throw IllegalArgumentException")
        void setDistanceThrowIllegalArgumentException() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            assertThrows(IllegalArgumentException.class, () -> fPoint.ext(fLine.setDistance(-1)),
                    "The distance must be positive");
        }

        @Test
        @DisplayName("Set distance - Line")
        void setDistanceModeLine() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.LINE));

            assertTrue(Math.abs(fPoint.extVal(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance - Line (position base)")
        void setDistanceModeLinePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Set distance - Line (position head)")
        void setDistanceModeLinePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.LINE));
        }

        @Test
        @DisplayName("Set distance - Line (validate positions)")
        void setDistanceModeLineValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.LINE));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Set distance - Line (validate references)")
        void setDistanceModeLineValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.setDistance(1, IFLine.Mode.LINE));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set distance - Line (throw IllegalArgumentException")
        void setDistanceModeLineThrowIllegalArgumentException() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            assertThrows(IllegalArgumentException.class,
                    () -> fPoint.ext(fLine.setDistance(-1, IFLine.Mode.LINE)),
                    "The distance must be positive");
        }

        @Test
        @DisplayName("Set distance - Segment")
        void setDistanceModeSegment() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.SEGMENT));

            assertTrue(Math.abs(fPoint.extVal(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance - Segment (position base)")
        void setDistanceModeSegmentPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.setDistance(1, IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Set distance - Segment (position head)")
        void setDistanceModeSegmentPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.setDistance(1, IFLine.Mode.SEGMENT)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Set distance - Segment (validate positions)")
        void setDistanceModeSegmentValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.SEGMENT));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Set distance - Segment (validate references)")
        void setDistanceModeSegmentValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.setDistance(1, IFLine.Mode.SEGMENT));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set distance - Segment (throw IllegalArgumentException")
        void setDistanceModeSegmentThrowIllegalArgumentException() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            assertThrows(IllegalArgumentException.class,
                    () -> fPoint.ext(fLine.setDistance(-1, IFLine.Mode.SEGMENT)),
                    "The distance must be positive");
        }

        @Test
        @DisplayName("Set distance - Ray")
        void setDistanceModeRay() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.RAY));

            assertTrue(Math.abs(fPoint.extVal(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance - Ray (position base)")
        void setDistanceModeRayPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertThrows(ProjectionException.class, () -> fPoint.ext(fLine.setDistance(1, IFLine.Mode.RAY)),
                    "It should not be possible to project the IFPoint");
        }

        @Test
        @DisplayName("Set distance - Ray (position head)")
        void setDistanceModeRayPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.RAY));
        }

        @Test
        @DisplayName("Set distance - Ray (validate positions)")
        void setDistanceModeRayValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.setDistance(1, IFLine.Mode.RAY));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Set distance - Ray (validate references)")
        void setDistanceModeRayValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.setDistance(1, IFLine.Mode.RAY));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set distance - Ray (throw IllegalArgumentException")
        void setDistanceModeRayThrowIllegalArgumentException() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            assertThrows(IllegalArgumentException.class,
                    () -> fPoint.ext(fLine.setDistance(-1, IFLine.Mode.RAY)),
                    "The distance must be positive");
        }

    }
}
