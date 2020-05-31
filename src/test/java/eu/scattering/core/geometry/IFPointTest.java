package eu.scattering.core.geometry;

import eu.scattering.core.GeometryFactory;
import eu.scattering.core.geometry.d0.IFPoint;
import eu.scattering.core.helper.Randomization;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IFPoint")
public class IFPointTest {

    private static double refX, refY, refZ;

    @BeforeAll
    static void beforeAll() {
        refX = Randomization.getTestValue();
        refY = Randomization.getTestValue();
        refZ = Randomization.getTestValue();
    }

    @Nested
    @DisplayName("Basic functionality")
    class IFPointBase {

        @Test
        @DisplayName("Constructor")
        void create() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            assertNotNull(fPoint, "The instance is null");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values using primitives")
        void setPrimitives() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.set(refX, refY, refZ);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Set values using an IFPoint")
        void setIFPoint() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            IFPoint position = GeometryFactory.getIFPoint();
            position.set(refX, refY, refZ);

            fPoint.set(position);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Set values using an IFPoint (NullPointerException)")
        void resetWithIFPointThrowNullPointerException() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            assertThrows(NullPointerException.class, () -> fPoint.set(null), "The reference cannot be null" );

        }

        @Test
        @DisplayName("Modify X")
        void modifyX() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.setX(refX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Modify Y")
        void modifyY() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.setY(refY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Modify Z")
        void modifyZ() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.setZ(refZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );

        }

    }

    @Nested
    @DisplayName("Algebra")
    class IBaseAlgebra {

        private IFPoint fPointRef;
        private double opX, opY, opZ;

        @BeforeEach
        void beforeEach() {
            fPointRef = GeometryFactory.getIFPoint().set(refX, refY, refZ);

            opX = Randomization.getTestValue();
            opY = Randomization.getTestValue();
            opZ = Randomization.getTestValue();
        }

        @Nested
        @DisplayName("Addition")
        class Addition {

            @Test
            @DisplayName("Add IFPoint")
            void addIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint().set(opX, opY, opZ);

                fPointRef.add(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Add IFPoint (NullPointerException)")
            void addIFPointThrowNullPointerException() {
                assertThrows(NullPointerException.class,
                        () -> fPointRef.add(null), "The operand cannot be null");
            }

            @Test
            @DisplayName("Add primitives")
            void addPrimitives() {
                fPointRef.add(opX, opY, opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Add X")
            void addX() {
                fPointRef.addX(opX);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Add Y")
            void addY() {
                fPointRef.addY(opY);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Add Z")
            void addZ() {
                fPointRef.addZ(opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

        }

        @Nested
        @DisplayName("Subtraction")
        class Subtraction {

            @Test
            @DisplayName("Sub IFPoint")
            void subIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint().set(opX, opY, opZ);

                fPointRef.sub(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Sub IFPoint (NullPointerException)")
            void subIFPointThrowNullPointerException() {
                assertThrows(NullPointerException.class,
                        () -> fPointRef.sub(null), "The operand cannot be null");
            }

            @Test
            @DisplayName("Sub primitives")
            void subPrimitives() {
                fPointRef.sub(opX, opY, opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Sub X")
            void subX() {
                fPointRef.subX(opX);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Sub Y")
            void subY() {
                fPointRef.subY(opY);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Sub Z")
            void subZ() {
                fPointRef.subZ(opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

        }

        @Nested
        @DisplayName("Multiplication")
        class Multiplication {

            @Test
            @DisplayName("Mul IFPoint")
            void mulIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint().set(opX, opY, opZ);

                fPointRef.mul(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Mul IFPoint (NullPointerException)")
            void mulIFPointThrowNullPointerException() {
                assertThrows(NullPointerException.class,
                        () -> fPointRef.mul(null), "The operand cannot be null");

            }

            @Test
            @DisplayName("Mul primitives")
            void mulPrimitives() {
                fPointRef.mul(opX, opY, opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Mul X")
            void mulX() {
                fPointRef.mulX(opX);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Mul Y")
            void mulY() {
                fPointRef.mulY(opY);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Mul Z")
            void mulZ() {
                fPointRef.mulZ(opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

        }

        @Nested
        @DisplayName("Division")
        class Division {

            @Test
            @DisplayName("Div IFPoint")
            void divIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint().set(opX, opY, opZ);

                fPointRef.div(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Div IFPoint (ArithmeticException)")
            void divIFPointThrowArithmeticException() {

                assertAll("Division by zero",
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(GeometryFactory.getIFPoint().set(0, 1, 1)),
                                "The X value is zero"),
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(GeometryFactory.getIFPoint().set(1, 0, 1)),
                                "The Y value is zero"),
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(GeometryFactory.getIFPoint().set(0, 1, 1)),
                                "The Z value is zero")
                );

            }

            @Test
            @DisplayName("Div IFPoint (NullPointerException)")
            void divIFPointThrowNullPointerException() {
                assertThrows(NullPointerException.class, () -> fPointRef.div(null),
                        "The reference cannot be null");
            }

            @Test
            @DisplayName("Div primitives")
            void divPrimitives() {
                fPointRef.div(opX, opY, opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Div primitives (ArithmeticException)")
            void divPrimitivesThrowArithmeticException() {

                assertAll("Division by zero",
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(0, 1, 1), "The X value is zero"),
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(1, 0, 1), "The Y value is zero"),
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(0, 1, 1), "The Z value is zero")
                );

            }

            @Test
            @DisplayName("Div X")
            void divX() {
                fPointRef.divX(opX);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Div X (ArithmeticException)")
            void divXThrowArithmeticException() {
                assertThrows(ArithmeticException.class, () -> fPointRef.divX(0), "The X value is zero");
            }

            @Test
            @DisplayName("Div Y")
            void divY() {
                fPointRef.divY(opY);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Div Y (ArithmeticException)")
            void divYThrowArithmeticException() {
                assertThrows(ArithmeticException.class, () -> fPointRef.divY(0), "The Y value is zero");
            }

            @Test
            @DisplayName("Div Z")
            void divZ() {
                fPointRef.divZ(opZ);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Div Z (ArithmeticException)")
            void divZThrowArithmeticException() {
                assertThrows(ArithmeticException.class, () -> fPointRef.divZ(0), "The Z value is zero");
            }

        }

        @Nested
        @DisplayName("Other")
        class Other {

            @Test
            @DisplayName("Scale")
            void scale() {
                double op = opX * opY * opZ;

                fPointRef.scale(op);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX * op, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY * op, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ * op, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("IFPoint list")
            void getIFPoints() {
                List<IFPoint> list = fPointRef.getIFPoints();

                assertAll("Validate IFPoint list",
                        () -> assertEquals(1, list.size(), "The Size of the list is incorrect"),
                        () -> assertEquals(refX, list.get(0).getX(), "The X value is incorrect"),
                        () -> assertEquals(refY, list.get(0).getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ, list.get(0).getZ(), "The Z value is incorrect")
                );
            }

        }
    }

}
