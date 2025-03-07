package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;
import eu.scattering.core.transfer.container.storage.Storage;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FCache")
public class FCacheTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FCacheBasicTest {

        @Test
        @DisplayName("Creation")
        void creationTest() {
            FCache fCache = factory.getFCache();

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fCache.getSize(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Put with key")
        void putWithKeyTest() {
            FCache fCache = factory.getFCache();

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            boolean isReplaced = fCache.put("data", fPos3D);

            FPos3D result = fCache.getOptional("data", FPos3D.class).orElseThrow();
            Storage<?> resultSuper = fCache.getOptional("data", Storage.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.getSize(),
                            "The number of elements is incorrect"),
                    () -> assertSame(fPos3D, result,
                            "The object should be the same"),
                    () -> assertSame(fPos3D, resultSuper,
                            "The object should be the same"),
                    () -> assertFalse(isReplaced,
                            "The object should not be replaced")
            );
        }

        @Test
        @DisplayName("Put with key - replace")
        void putWithKeyReplaceTest() {
            FCache fCache = factory.getFCache();

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            fCache.put("data", factory.getFPos3D(3, 2, 1));
            boolean isReplaced = fCache.put("data", fPos3D);

            FPos3D result = fCache.getOptional("data", FPos3D.class).orElseThrow();
            Storage<?> resultSuper = fCache.getOptional("data", Storage.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.getSize(),
                            "The number of elements is incorrect"),
                    () -> assertSame(fPos3D, result,
                            "The object should be the same"),
                    () -> assertSame(fPos3D, resultSuper,
                            "The object should be the same"),
                    () -> assertTrue(isReplaced,
                            "The object should not be replaced")
            );
        }

        @Test
        @DisplayName("Put with class")
        void putWithClassTest() {
            FCache fCache = factory.getFCache();

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            boolean isReplaced = fCache.put(FPos3D.class, fPos3D);

            FPos3D result = fCache.getOptional(FPos3D.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.getSize(),
                            "The number of elements is incorrect"),
                    () -> assertSame(fPos3D, result,
                            "The object should be the same"),
                    () -> assertFalse(isReplaced,
                            "The object should not be replaced")
            );
        }

        @Test
        @DisplayName("Put with class - replace")
        void putWithClassReplaceTest() {
            FCache fCache = factory.getFCache();

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            fCache.put(FPos3D.class, factory.getFPos3D(3, 2, 1));
            boolean isReplaced = fCache.put(FPos3D.class, fPos3D);

            FPos3D result = fCache.getOptional(FPos3D.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.getSize(),
                            "The number of elements is incorrect"),
                    () -> assertSame(fPos3D, result,
                            "The object should be the same"),
                    () -> assertTrue(isReplaced,
                            "The object should not be replaced")
            );
        }

        @Test
        @DisplayName("Get with key - Non-existent")
        void getWithKeyEmptyTest() {
            FCache fCache = factory.getFCache();

            assertThrows(IllegalArgumentException.class, () -> fCache.get("data", FPos3D.class),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get with class - Non-existent")
        void getWithClassEmptyTest() {
            FCache fCache = factory.getFCache();

            assertThrows(IllegalArgumentException.class, () -> fCache.get(FPos3D.class),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get optional with key - Non-existent")
        void getOptionalWithKeyEmptyTest() {
            FCache fCache = factory.getFCache();

            assertTrue(fCache.getOptional("data", FPos3D.class).isEmpty(),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get optional with class - Non-existent")
        void getOptionalWithClassEmptyTest() {
            FCache fCache = factory.getFCache();

            assertTrue(fCache.getOptional(FPos3D.class).isEmpty(),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get with key (fail) - Cast error")
        void getWithKeyFailCastTest() {
            FCache fCache = factory.getFCache();

            FPos3D data = factory.getFPos3D(1, 2, 3);

            fCache.put("data", data);

            assertThrows(IllegalArgumentException.class,
                    () -> fCache.getOptional("data", FPos4D.class),
                    "The element type is erroneous");
        }

        @Test
        @DisplayName("Get with key and supplier")
        void getWithKeyAndSupplierTest() {
            FCache fCache = factory.getFCache();
            fCache.put(ContainerFactory.class, factory);

            FPos3D resultA = fCache.get("data", FPos3D.class,
                    (cache) -> cache.get(ContainerFactory.class).getFPos3D(1, 2, 3));
            FPos3D resultB = fCache.get("data", FPos3D.class);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.getSize(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(factory.getFPos3D(1, 2, 3), resultA,
                            "The object should be equal"),
                    () -> assertSame(resultA, resultB,
                            "The object should be the same")
            );
        }

        @Test
        @DisplayName("Get with class and supplier")
        void getWithClassAndSupplierTest() {
            FCache fCache = factory.getFCache();
            fCache.put(ContainerFactory.class, factory);

            FPos3D resultA = fCache.get(FPos3D.class,
                    (cache) -> cache.get(ContainerFactory.class).getFPos3D(1, 2, 3));
            FPos3D resultB = fCache.get(FPos3D.class);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.getSize(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(factory.getFPos3D(1, 2, 3), resultA,
                            "The object should be equal"),
                    () -> assertSame(resultA, resultB,
                            "The object should be the same")
            );
        }

        @Test
        @DisplayName("Delete with key")
        void deleteWithKeyTest() {
            FCache fCache = factory.getFCache();

            fCache.put("val1", factory.getFPos3D(1, 2, 3));
            fCache.put("val2", factory.getFPos3D(4, 5, 6));
            fCache.put(FPos3D.class, factory.getFPos3D(7, 8, 9));

            assertEquals(3, fCache.getSize(), "The size is incorrect");

            boolean isDeletedA = fCache.delete("val2");
            boolean isDeletedB = fCache.delete("val2");

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.getSize(),
                            "The size is incorrect"),
                    () -> assertFalse(fCache.getOptional("val2", FPos3D.class).isPresent(),
                            "The object should not be available"),
                    () -> assertTrue(isDeletedA,
                            "The object should exist"),
                    () -> assertFalse(isDeletedB,
                            "The object should not exist")
            );
        }

        @Test
        @DisplayName("Delete with class")
        void deleteWithClassTest() {
            FCache fCache = factory.getFCache();

            fCache.put("val1", factory.getFPos3D(1, 2, 3));
            fCache.put("val2", factory.getFPos3D(4, 5, 6));
            fCache.put(FPos3D.class, factory.getFPos3D(7, 8, 9));

            assertEquals(3, fCache.getSize(), "The size is incorrect");

            boolean isDeletedA = fCache.delete(FPos3D.class);
            boolean isDeletedB = fCache.delete(FPos3D.class);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.getSize(),
                            "The size is incorrect"),
                    () -> assertFalse(fCache.getOptional(FPos3D.class).isPresent(),
                            "The object should not be available"),
                    () -> assertTrue(isDeletedA,
                            "The object should exist"),
                    () -> assertFalse(isDeletedB,
                            "The object should not exist")
            );
        }

        @Test
        @DisplayName("Reset")
        void resetTest() {
            FCache fCache = factory.getFCache();

            fCache.put("val1", factory.getFPos3D(1, 2, 3));
            fCache.put("val2", factory.getFPos3D(4, 5, 6));
            fCache.put(FPos3D.class, factory.getFPos3D(7, 8, 9));

            assertEquals(3, fCache.getSize(), "The size is incorrect");

            int size = fCache.reset();

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fCache.getSize(),
                            "The size is incorrect"),
                    () -> assertEquals(3, size,
                            "The number of deleted elements is incorrect")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FCacheAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            FCache dtoOrigin = factory.getFCache();

            dtoOrigin.put("val1", factory.getFPos3D(1, 2, 3));
            dtoOrigin.put("val2", factory.getFPos3D(4, 5, 6));
            dtoOrigin.put(FPos4D.class, factory.getFPos4D(7, 8, 9, 0));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FCache dtoCopy = factory.getFCache(jsonOrigin);

            assertEquals(0, dtoCopy.getSize(),
                    "The parsed JSON object should be empty");
        }
    }
}
