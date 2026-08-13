package eu.scattering.core.test.storage;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.cache.FCache;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FCache")
public class FCacheTest {
    private final boolean multi = true;

    @Nested
    @DisplayName("Basic")
    class FCacheBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FCache fCache = factory.getFCache();

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fCache.size(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Construct (thread)")
        void constructThread() {
            FCache fCache = factory.getFCache(multi);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fCache.size(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Put with key")
        void putWithKey() {
            FCache fCache = factory.getFCache(multi);

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            boolean isReplaced = fCache.put("data", fPos3D);

            FPos3D result = fCache.getOptional("data", FPos3D.class).orElseThrow();
            Storage resultSuper = fCache.getOptional("data", Storage.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.size(),
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
        void putWithKeyReplace() {
            FCache fCache = factory.getFCache(multi);

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            fCache.put("data", factory.getFPos3D(3, 2, 1));
            boolean isReplaced = fCache.put("data", fPos3D);

            FPos3D result = fCache.getOptional("data", FPos3D.class).orElseThrow();
            Storage resultSuper = fCache.getOptional("data", Storage.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.size(),
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
        void putWithClass() {
            FCache fCache = factory.getFCache(multi);

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            boolean isReplaced = fCache.put(FPos3D.class, fPos3D);

            FPos3D result = fCache.getOptional(FPos3D.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.size(),
                            "The number of elements is incorrect"),
                    () -> assertSame(fPos3D, result,
                            "The object should be the same"),
                    () -> assertFalse(isReplaced,
                            "The object should not be replaced")
            );
        }

        @Test
        @DisplayName("Put with class - replace")
        void putWithClassReplace() {
            FCache fCache = factory.getFCache(multi);

            FPos3D fPos3D = factory.getFPos3D(1, 2, 3);

            fCache.put(FPos3D.class, factory.getFPos3D(3, 2, 1));
            boolean isReplaced = fCache.put(FPos3D.class, fPos3D);

            FPos3D result = fCache.getOptional(FPos3D.class).orElseThrow();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fCache.size(),
                            "The number of elements is incorrect"),
                    () -> assertSame(fPos3D, result,
                            "The object should be the same"),
                    () -> assertTrue(isReplaced,
                            "The object should not be replaced")
            );
        }

        @Test
        @DisplayName("Get with key - Non-existent")
        void getWithKeyEmpty() {
            FCache fCache = factory.getFCache(multi);

            assertThrows(IllegalArgumentException.class, () -> fCache.get("data", FPos3D.class),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get with class - Non-existent")
        void getWithClassEmpty() {
            FCache fCache = factory.getFCache(multi);

            assertThrows(IllegalArgumentException.class, () -> fCache.get(FPos3D.class),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get optional with key - Non-existent")
        void getOptionalWithKeyEmpty() {
            FCache fCache = factory.getFCache(multi);

            assertTrue(fCache.getOptional("data", FPos3D.class).isEmpty(),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get optional with class - Non-existent")
        void getOptionalWithClassEmpty() {
            FCache fCache = factory.getFCache(multi);

            assertTrue(fCache.getOptional(FPos3D.class).isEmpty(),
                    "The element should not exist");
        }

        @Test
        @DisplayName("Get with key (fail) - Cast error")
        void getWithKeyFailCast() {
            FCache fCache = factory.getFCache(multi);

            FPos3D data = factory.getFPos3D(1, 2, 3);

            fCache.put("data", data);

            assertThrows(IllegalArgumentException.class,
                    () -> fCache.getOptional("data", FPos4D.class),
                    "The element type is erroneous");
        }

        @Test
        @DisplayName("Get with key and supplier")
        void getWithKeyAndSupplier() {
            FCache fCache = factory.getFCache(multi);
            fCache.put(ScatterFactory.class, factory);

            FPos3D resultA = fCache.get("data", FPos3D.class,
                    (cache) -> cache.get(ScatterFactory.class).getFPos3D(1, 2, 3));
            FPos3D resultB = fCache.get("data", FPos3D.class);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(factory.getFPos3D(1, 2, 3), resultA,
                            "The object should be equal"),
                    () -> assertSame(resultA, resultB,
                            "The object should be the same")
            );
        }

        @Test
        @DisplayName("Get with class and supplier")
        void getWithClassAndSupplier() {
            FCache fCache = factory.getFCache(multi);
            fCache.put(ScatterFactory.class, factory);

            FPos3D resultA = fCache.get(FPos3D.class,
                    (cache) -> cache.get(ScatterFactory.class).getFPos3D(1, 2, 3));
            FPos3D resultB = fCache.get(FPos3D.class);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(factory.getFPos3D(1, 2, 3), resultA,
                            "The object should be equal"),
                    () -> assertSame(resultA, resultB,
                            "The object should be the same")
            );
        }

        @Test
        @DisplayName("Delete with key")
        void deleteWithKey() {
            FCache fCache = factory.getFCache(multi);

            fCache.put("val1", factory.getFPos3D(1, 2, 3));
            fCache.put("val2", factory.getFPos3D(4, 5, 6));
            fCache.put(FPos3D.class, factory.getFPos3D(7, 8, 9));

            assertEquals(3, fCache.size(), "The size is incorrect");

            boolean isDeletedA = fCache.delete("val2");
            boolean isDeletedB = fCache.delete("val2");

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.size(),
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
        void deleteWithClass() {
            FCache fCache = factory.getFCache(multi);

            fCache.put("val1", factory.getFPos3D(1, 2, 3));
            fCache.put("val2", factory.getFPos3D(4, 5, 6));
            fCache.put(FPos3D.class, factory.getFPos3D(7, 8, 9));

            assertEquals(3, fCache.size(), "The size is incorrect");

            boolean isDeletedA = fCache.delete(FPos3D.class);
            boolean isDeletedB = fCache.delete(FPos3D.class);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fCache.size(),
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
        void reset() {
            FCache fCache = factory.getFCache(multi);

            fCache.put("val1", factory.getFPos3D(1, 2, 3));
            fCache.put("val2", factory.getFPos3D(4, 5, 6));
            fCache.put(FPos3D.class, factory.getFPos3D(7, 8, 9));

            assertEquals(3, fCache.size(), "The size is incorrect");

            int size = fCache.reset();

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fCache.size(),
                            "The size is incorrect"),
                    () -> assertEquals(3, size,
                            "The number of deleted elements is incorrect")
            );
        }
    }

    @Nested
    @DisplayName("Advanced")
    class FCacheAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSON() {
            FCache dtoOrigin = factory.getFCache();

            dtoOrigin.put("val1", factory.getFPos3D(1, 2, 3));
            dtoOrigin.put("val2", factory.getFPos3D(4, 5, 6));
            dtoOrigin.put(FPos4D.class, factory.getFPos4D(7, 8, 9, 0));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FCache dtoCopy = factory.getFCache(jsonOrigin);

            assertEquals(0, dtoCopy.size(),
                    "The parsed JSON object should be empty");
        }

        @Test
        @DisplayName("JSON (thread)")
        void parseJSONThread() {
            FCache dtoOrigin = factory.getFCache(multi);

            dtoOrigin.put("val1", factory.getFPos3D(1, 2, 3));
            dtoOrigin.put("val2", factory.getFPos3D(4, 5, 6));
            dtoOrigin.put(FPos4D.class, factory.getFPos4D(7, 8, 9, 0));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FCache dtoCopy = factory.getFCache(jsonOrigin);

            assertEquals(0, dtoCopy.size(),
                    "The parsed JSON object should be empty");
        }
    }
}
