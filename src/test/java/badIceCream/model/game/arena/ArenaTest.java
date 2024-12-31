package badIceCream.model.game.arena;

import badIceCream.audio.AudioController;
import badIceCream.model.Position;
import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.model.game.elements.monsters.*;
import badIceCream.GUI.GUI;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class ArenaTest {
    @Property
    void testArenaInitialization(@ForAll int width, @ForAll int height) {
        Arena arena = new Arena(width, height);
        assertEquals(width, arena.getWidth());
        assertEquals(height, arena.getHeight());
        assertEquals(0, arena.getLevel());
        assertNull(arena.getIceCream());
        assertNull(arena.getMonsters());
        assertNull(arena.getWalls());
        assertNull(arena.getFruits());
    }

    @Property
    void testGenerateNewFruits(@ForAll("levels") int level) {
        Arena arena = new Arena(10, 10);
        arena.setWalls(new ArrayList<>());
        arena.setFruits(new ArrayList<>());
        arena.generateNewFruits(level);

        if(level == 6) return;

        List<Fruit> fruits = arena.getFruits();
        final int[] expectedFruitCounts = {6, 8, 10, 12, 14};
        assertEquals(expectedFruitCounts[level-1], fruits.size());

        final Class<?>[] expectedFruitClasses = {AppleFruit.class, CherryFruit.class, PineappleFruit.class, BananaFruit.class, AppleFruit.class};
        assertTrue(fruits.stream().allMatch(f -> f.getClass().equals(expectedFruitClasses[level - 1])));
    }

    @Property
    void testIsEmpty(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        arena.setWalls(Collections.singletonList(new StoneWall(x, y)));
        Position position = new Position(x, y);
        assertFalse(arena.isEmpty(position));

        position = new Position(x + 1, y + 1);
        assertTrue(arena.isEmpty(position));
    }

    @Property
    void testIsEmptyMonsters(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        arena.setWalls(Collections.singletonList(new StoneWall(x, y)));
        arena.setMonsters(Collections.singletonList(new DefaultMonster(x + 1, y + 1)));

        Position wallPos = new Position(x, y);
        Position monsterPos = new Position(x + 1, y + 1);

        assertFalse(arena.isEmptyMonsters(wallPos));
        assertFalse(arena.isEmptyMonsters(monsterPos));

        Position emptyPos = new Position(x + 2, y + 2);
        assertTrue(arena.isEmptyMonsters(emptyPos));
    }

    @Property
    void testIsEmptyNoStoneWall(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        arena.setWalls(Collections.singletonList(new StoneWall(x, y)));
        arena.setMonsters(Collections.singletonList(new DefaultMonster(x + 1, y + 1)));

        Position wallPos = new Position(x, y);
        Position monsterPos = new Position(x + 1, y + 1);

        assertFalse(arena.isEmptyNoStoneWall(wallPos));
        assertFalse(arena.isEmptyNoStoneWall(monsterPos));

        Position emptyPos = new Position(x + 2, y + 2);
        assertTrue(arena.isEmptyNoStoneWall(emptyPos));
    }

    @Property
    void testIceWallDestroyed(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        StoneWall wall = new StoneWall(x, y);
        arena.setWalls(new ArrayList<>(List.of(wall)));

        arena.iceWallDestroyed(new Position(x, y));
        assertTrue(arena.getWalls().isEmpty());

        arena.iceWallDestroyed(new Position(x, y));
        assertTrue(arena.getWalls().isEmpty());
    }

    @Property
    void testIceWallPosition(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        IceWall iceWall = new IceWall(x, y);
        arena.setWalls(new ArrayList<>(List.of(iceWall)));

        Position iceWallPos = new Position(x, y);
        assertTrue(arena.isIceWall(iceWallPos));
    }

    @Property
    void testHasMonster(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        Monster monster = new DefaultMonster(x, y);
        arena.setMonsters(new ArrayList<>(List.of(monster)));

        Position monsterPos = new Position(x, y);
        assertNotNull(arena.hasMonster(monsterPos));

        Position emptyPos = new Position(x + 1, y + 1);
        assertNull(arena.hasMonster(emptyPos));
    }

    @Test
    void testEatFruit() {
        Arena arena = new Arena(10, 10);
        Fruit fruit = new AppleFruit(1,1);
        arena.setFruits(new ArrayList<>(List.of(fruit, new AppleFruit(0, 0))));

        Position fruitPos = new Position(1, 1);
        int fruitType = arena.eatFruit(fruitPos);
        assertEquals(1, fruitType);

        assertTrue(arena.getFruits().size() == 1);

        Position nonFruitPos = new Position(2, 2);
        assertEquals(-1, arena.eatFruit(nonFruitPos));
    }

    @Property
    void testIsHotFloor(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        HotFloor hotFloor = new HotFloor(x, y);
        arena.setHotFloors(Collections.singletonList(hotFloor));

        Position hotFloorPos = new Position(x, y);
        assertTrue(arena.isHotFloor(hotFloorPos));

        Position nonHotFloorPos = new Position(x + 1, y + 1);
        assertFalse(arena.isHotFloor(nonHotFloorPos));
    }

    @Property
    void testIsIceWall(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        IceWall iceWall = new IceWall(x, y);
        arena.setWalls(Collections.singletonList(iceWall));

        Position iceWallPos = new Position(x, y);
        assertTrue(arena.isIceWall(iceWallPos));

        Position nonIceWallPos = new Position(x + 1, y + 1);
        assertFalse(arena.isIceWall(nonIceWallPos));
    }

    @Test
    void testIsFruit() {
        Arena arena = new Arena(10, 10);
        Fruit fruit = new AppleFruit(5, 5);
        arena.setFruits(Collections.singletonList(fruit));

        Position fruitPos = new Position(5, 5);
        assertNotEquals(-1, arena.isFruit(fruitPos));

        Position nonFruitPos = new Position(6, 6);
        assertEquals(-1, arena.isFruit(nonFruitPos));
    }


    @Property
    void testSetHotFloors(@ForAll("positions") Position position) {
        Arena arena = new Arena(10, 10);
        HotFloor hotFloor = new HotFloor(position.getX(), position.getY());
        arena.setHotFloors(new ArrayList<>(List.of(hotFloor)));

        List<HotFloor> hotFloors = arena.getHotFloors();
        assertEquals(1, hotFloors.size());
        assertTrue(hotFloors.contains(hotFloor));
    }

    @Property
    void testSetIceCream(@ForAll("positions") Position position) {
        Arena arena = new Arena(10, 10);
        IceCream iceCream = new IceCream(position.getX(), position.getY());
        arena.setIceCream(iceCream);

        assertEquals(iceCream, arena.getIceCream());
    }

    @Property
    void testGetHotFloors() {
        Arena arena = new Arena(10, 10);
        HotFloor hotFloor1 = new HotFloor(1, 1);
        HotFloor hotFloor2 = new HotFloor(2, 2);
        arena.setHotFloors(Arrays.asList(hotFloor1, hotFloor2));

        List<HotFloor> hotFloors = arena.getHotFloors();
        assertEquals(2, hotFloors.size());
        assertTrue(hotFloors.contains(hotFloor1));
        assertTrue(hotFloors.contains(hotFloor2));
    }

    @Property
    void testSetLevel(@ForAll int level) {
        Arena arena = new Arena(10, 10);
        arena.setLevel(level);

        assertEquals(level, arena.getLevel());
    }

    @Property
    void testPowerIceCream(@ForAll("movements") GUI.ACTION action, @ForAll boolean wallToDestroy){
        try(MockedStatic<AudioController> audioController = mockStatic(AudioController.class)) {
            Arena arena = getArena(action, wallToDestroy);

            arena.powerIceCream(action);

            if(action == GUI.ACTION.NONE) return;
            if(wallToDestroy){
                assertTrue(arena.getWalls().isEmpty());
                audioController.verify(AudioController::playBreakWallSound, times(1));
            } else {
                assertFalse(arena.getWalls().isEmpty());
                audioController.verify(AudioController::playBuildWallSound, times(1));
                if(action == GUI.ACTION.UP) assertTrue(arena.isIceWall(new Position(5, 3)));
                if(action == GUI.ACTION.LEFT) assertTrue(arena.isIceWall(new Position(3, 5)));
            }
        }
    }

    private static Arena getArena(GUI.ACTION action, boolean wallToDestroy) {
        Arena arena = new Arena(10, 10);
        IceCream iceCream = new IceCream(5, 5);
        arena.setIceCream(iceCream);
        arena.setWalls(new ArrayList<>(List.of(new IceWall(5, 0), new IceWall(5, 10), new IceWall(0, 5), new IceWall(10, 5))));
        arena.setMonsters(new ArrayList<>());
        arena.setHotFloors(new ArrayList<>());

        if(wallToDestroy){
            switch(action) {
                case UP -> arena.setWalls(new ArrayList<>(List.of(new IceWall(5, 4), new IceWall(5, 3))));
                case DOWN -> arena.setWalls(new ArrayList<>(List.of(new IceWall(5, 6))));
                case LEFT -> arena.setWalls(new ArrayList<>(List.of(new IceWall(4, 5), new IceWall(3, 5))));
                case RIGHT -> arena.setWalls(new ArrayList<>(List.of(new IceWall(6, 5))));
                default -> arena.setWalls(new ArrayList<>(List.of(new IceWall(0,0))));
            }
        }
        return arena;
    }

    @Test
    void testNotEmptySpawnFruit(){
        Arena arena = new Arena(2, 2);
        arena.setWalls(new ArrayList<>(List.of(new IceWall(0, 1), new StoneWall(1, 1))));
        arena.setFruits(new ArrayList<>(List.of(new AppleFruit(0, 0))));
        assertFalse(arena.isEmptySpawnFruit(new Position(1, 1)));
        assertTrue(arena.isEmptySpawnFruit(new Position(0, 1)));
        assertFalse(arena.isEmptySpawnFruit(new Position(0, 0)));
    }

    @Provide
    Arbitrary<Integer> levels() {
        return Arbitraries.of(1, 2, 3, 4, 5, 6);
    }

    @Provide
    Arbitrary<GUI.ACTION> movements() {
        return Arbitraries.of(GUI.ACTION.UP, GUI.ACTION.DOWN, GUI.ACTION.LEFT, GUI.ACTION.RIGHT, GUI.ACTION.NONE);
    }

    @Provide
    Arbitrary<Position> positions() {
        return Arbitraries.integers()
                .between(0, 9)
                .flatMap(x -> Arbitraries.integers()
                        .between(0, 9)
                        .map(y -> new Position(x, y)));
    }
}
