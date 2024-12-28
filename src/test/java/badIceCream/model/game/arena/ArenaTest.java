package badIceCream.model.game.arena;

import badIceCream.model.Position;
import badIceCream.model.game.elements.*;
import badIceCream.model.game.elements.fruits.*;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.model.game.elements.monsters.*;
import badIceCream.GUI.GUI;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

//TODO: Improve this code
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

        List<Fruit> fruits = arena.getFruits();
        int expectedFruitCount = getExpectedFruitCountForLevel(level);

        assertEquals(expectedFruitCount, fruits.size());
        if (level == 1) {
            assertTrue(fruits.stream().allMatch(f -> f instanceof AppleFruit));
        } else if (level == 2) {
            assertTrue(fruits.stream().allMatch(f -> f instanceof CherryFruit));
        } else if (level == 3) {
            assertTrue(fruits.stream().allMatch(f -> f instanceof PineappleFruit));
        } else if (level == 4) {
            assertTrue(fruits.stream().allMatch(f -> f instanceof BananaFruit));
        } else if (level == 5) {
            assertTrue(fruits.stream().allMatch(f -> f instanceof AppleFruit));
        }
    }

    private int getExpectedFruitCountForLevel(int level) {
        return switch (level) {
            case 1 -> 6;
            case 2 -> 8;
            case 3 -> 10;
            case 4 -> 12;
            case 5 -> 14;
            default -> 0;
        };
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

    @Property
    void testEatFruit(@ForAll int x, @ForAll int y) {
        Arena arena = new Arena(10, 10);
        Fruit fruit = new AppleFruit(x, y);
        arena.setFruits(new ArrayList<>(List.of(fruit)));

        Position fruitPos = new Position(x, y);
        int fruitType = arena.eatFruit(fruitPos);
        assertEquals(1, fruitType);

        assertTrue(arena.getFruits().isEmpty());

        Position nonFruitPos = new Position(x + 1, y + 1);
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
        Arena arena = new Arena(10, 10);
        IceCream iceCream = new IceCream(5, 5);
        arena.setIceCream(iceCream);
        Wall wall1 = new IceWall(5, 0);
        Wall wall2 = new IceWall(5, 10);
        Wall wall3 = new IceWall(0, 5);
        Wall wall4 = new IceWall(10, 5);
        arena.setWalls(new ArrayList<>(List.of(wall1, wall2, wall3, wall4)));
        arena.setMonsters(new ArrayList<>());
        arena.setHotFloors(new ArrayList<>());

        Wall wall = new IceWall(0, 0);
        if (action == GUI.ACTION.UP) {
            wall = new IceWall(5, 4);
        } else if (action == GUI.ACTION.DOWN) {
            wall = new IceWall(5, 6);
        } else if (action == GUI.ACTION.LEFT) {
            wall = new IceWall(4, 5);
        } else if (action == GUI.ACTION.RIGHT) {
            wall = new IceWall(6, 5);
        }
        if(wallToDestroy) {
            arena.setWalls(new ArrayList<>(List.of(wall)));
        }

        arena.powerIceCream(action);

        if(wallToDestroy){
            assertFalse(arena.getWalls().contains(wall));
        } else {
            assertTrue(arena.isIceWall(wall.getPosition()));
        }
    }

    @Provide
    Arbitrary<Integer> levels() {
        return Arbitraries.of(1, 2, 3, 4, 5);
    }

    @Provide
    Arbitrary<GUI.ACTION> movements() {
        return Arbitraries.of(GUI.ACTION.UP, GUI.ACTION.DOWN, GUI.ACTION.LEFT, GUI.ACTION.RIGHT);
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
