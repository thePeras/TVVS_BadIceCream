package badIceCream.model.game.elements.fruits;

import net.jqwik.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class FruitsTest {
    private static final Map<Class<? extends Fruit>, Integer> classToTypeMap = Map.of(
            PineappleFruit.class, 4,
            StrawberryFruit.class, 5,
            CherryFruit.class, 3,
            BananaFruit.class, 2,
            AppleFruit.class, 1
    );

    private static final Map<Class<? extends Fruit>, BiFunction<Integer, Integer, Fruit>> classToFruitFactoryMap = Map.of(
            PineappleFruit.class, PineappleFruit::new,
            StrawberryFruit.class, StrawberryFruit::new,
            CherryFruit.class, CherryFruit::new,
            BananaFruit.class, BananaFruit::new,
            AppleFruit.class, AppleFruit::new
    );

    @Provide
    Arbitrary<Supplier<Fruit>> fruitSupplier() {
        return Arbitraries.of(
                () -> new PineappleFruit(0, 0),
                () -> new StrawberryFruit(0, 0),
                () -> new CherryFruit(0, 0),
                () -> new BananaFruit(0, 0),
                () -> new AppleFruit(0, 0)
        );
    }

    @Property
    void testFruitGetTypeForAllFruits(@ForAll("fruitSupplier") Supplier<Fruit> fruitSupplier) {
        Fruit fruit = fruitSupplier.get();
        int expectedType = classToTypeMap.get(fruit.getClass());
        assertEquals(expectedType, fruit.getType());
    }

    @Property
    void testFruitPositionBoundaryValues(@ForAll int x, @ForAll int y) {
        for (Map.Entry<Class<? extends Fruit>, BiFunction<Integer, Integer, Fruit>> entry : classToFruitFactoryMap.entrySet()) {
            Fruit fruit = entry.getValue().apply(x, y);
            assertEquals(x, fruit.getPosition().getX());
            assertEquals(y, fruit.getPosition().getY());
        }
    }

    @Property
    void testFruitEdgePositionCases(@ForAll("edgeCases") int x, @ForAll("edgeCases") int y) {
        for (Map.Entry<Class<? extends Fruit>, BiFunction<Integer, Integer, Fruit>> entry : classToFruitFactoryMap.entrySet()) {
            Fruit fruit = entry.getValue().apply(x, y);
            assertEquals(x, fruit.getPosition().getX());
            assertEquals(y, fruit.getPosition().getY());
        }
    }

    @Provide
    Arbitrary<Integer> edgeCases() {
        return Arbitraries.of(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    }
}
