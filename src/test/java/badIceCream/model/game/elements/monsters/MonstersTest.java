package badIceCream.model.game.elements.monsters;

import badIceCream.GUI.GUI;
import net.jqwik.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class MonstersTest {
    private static final Map<Class<? extends Monster>, Integer> classToTypeMap = Map.of(
            RunnerMonster.class, 3,
            WallBreakerMonster.class, 4,
            JumperMonster.class, 2,
            DefaultMonster.class, 1
    );

    private static final Map<Class<? extends Monster>, BiFunction<Integer, Integer, Monster>> classToMonsterFactoryMap = Map.of(
            RunnerMonster.class, RunnerMonster::new,
            WallBreakerMonster.class, WallBreakerMonster::new,
            JumperMonster.class, JumperMonster::new,
            DefaultMonster.class, DefaultMonster::new
    );

    @Provide
    Arbitrary<Supplier<Monster>> monsterSupplier() {
        return Arbitraries.of(
                () -> new RunnerMonster(0, 0),
                () -> new WallBreakerMonster(0, 0),
                () -> new JumperMonster(0, 0),
                () -> new DefaultMonster(0, 0)
        );
    }

    @Property
    void testMonsterGetTypeForAllMonsters(@ForAll("monsterSupplier") Supplier<Monster> monsterSupplier) {
        Monster monster = monsterSupplier.get();
        int expectedType = classToTypeMap.get(monster.getClass());
        assertEquals(expectedType, monster.getType());
    }

    @Property
    void testMonsterRunningState(@ForAll("monsterSupplier") Supplier<Monster> monsterSupplier) {
        Monster monster = monsterSupplier.get();
        assertFalse(monster.isRunning());
        monster.startRunning();
        assertEquals(monster instanceof RunnerMonster, monster.isRunning());
        monster.stopRunning();
        assertFalse(monster.isRunning());
    }

    @Property
    void testMonsterLastAction(@ForAll("monsterSupplier") Supplier<Monster> monsterSupplier, @ForAll GUI.ACTION action) {
        Monster monster = monsterSupplier.get();
        monster.setLastAction(action);
        assertEquals(action, monster.getLastAction());
    }

    @Property
    void testMonsterBoundaryPositionValues(@ForAll int x, @ForAll int y) {
        for (Map.Entry<Class<? extends Monster>, BiFunction<Integer, Integer, Monster>> entry : classToMonsterFactoryMap.entrySet()) {
            Monster monster = entry.getValue().apply(x, y);
            assertEquals(x, monster.getPosition().getX());
            assertEquals(y, monster.getPosition().getY());
        }
    }

    @Property
    void testMonsterEdgePositionCases(@ForAll("edgeCases") int x, @ForAll("edgeCases") int y) {
        for (Map.Entry<Class<? extends Monster>, BiFunction<Integer, Integer, Monster>> entry : classToMonsterFactoryMap.entrySet()) {
            Monster monster = entry.getValue().apply(x, y);
            assertEquals(x, monster.getPosition().getX());
            assertEquals(y, monster.getPosition().getY());
        }
    }

    @Provide
    Arbitrary<Integer> edgeCases() {
        return Arbitraries.of(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    }
}
