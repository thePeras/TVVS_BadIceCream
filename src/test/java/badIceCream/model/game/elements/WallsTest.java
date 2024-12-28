package badIceCream.model.game.elements;

import badIceCream.model.Position;
import net.jqwik.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class WallsTest {
    private static final Map<Class<? extends Element>, Integer> classToTypeMap = Map.of(
            StoneWall.class, 2,
            IceWall.class, 1,
            HotFloor.class, 0
    );

    private static final Map<Class<? extends Element>, BiFunction<Integer, Integer, Element>> classToElementFactoryMap = Map.of(
            StoneWall.class, StoneWall::new,
            IceWall.class, IceWall::new,
            HotFloor.class, HotFloor::new
    );

    @Provide
    Arbitrary<Supplier<Element>> elementSupplier() {
        return Arbitraries.of(
                () -> new StoneWall(0, 0),
                () -> new IceWall(0, 0),
                () -> new HotFloor(0, 0)
        );
    }

    @Property
    void testElementGetTypeForAllWallsAndFloors(@ForAll("elementSupplier") Supplier<Element> elementSupplier) {
        Element element = elementSupplier.get();
        int expectedType = classToTypeMap.get(element.getClass());
        assertEquals(expectedType, element.getType());
    }

    @Property
    void testPositionBoundaryValues(@ForAll int x, @ForAll int y) {
        // Test position for all wall types defined in the map
        for (Map.Entry<Class<? extends Element>, BiFunction<Integer, Integer, Element>> entry : classToElementFactoryMap.entrySet()) {
            Element element = entry.getValue().apply(x, y);  // Create element with dynamic position
            Position position = element.getPosition();
            assertEquals(x, position.getX());
            assertEquals(y, position.getY());
        }
    }

    @Property
    void testPositionEdgeCases(@ForAll("edgeCases") int x, @ForAll("edgeCases") int y) {
        for (Map.Entry<Class<? extends Element>, BiFunction<Integer, Integer, Element>> entry : classToElementFactoryMap.entrySet()) {
            Element element = entry.getValue().apply(x, y);
            Position position = element.getPosition();
            assertEquals(x, position.getX());
            assertEquals(y, position.getY());
        }
    }

    @Provide
    Arbitrary<Integer> edgeCases() {
        return Arbitraries.of(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    }
}
