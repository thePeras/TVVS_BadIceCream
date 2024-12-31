package badIceCream.viewer;

import badIceCream.GUI.GUI;
import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ArenaViewerTest {

    Arena arena;
    FruitViewer fruitViewer;
    MonsterViewer monsterViewer;
    WallViewer wallViewer;
    HotFloorViewer hotFloorViewer;
    IceCreamViewer iceCreamViewer;

    Graphics graphics;

    @BeforeProperty
    void setup() {
        fruitViewer = mock(FruitViewer.class);
        monsterViewer = mock(MonsterViewer.class);
        wallViewer = mock(WallViewer.class);
        hotFloorViewer = mock(HotFloorViewer.class);
        iceCreamViewer = mock(IceCreamViewer.class);
        arena = new Arena(2, 2);
        arena.setFruits(new ArrayList<>());
        arena.setMonsters(new ArrayList<>());
        arena.setWalls(new ArrayList<>());
        arena.setHotFloors(new ArrayList<>());
        arena.setIceCream(new IceCream(0, 0));

        graphics = mock(Graphics.class);
    }

    @Provide
    public Arbitrary<Integer> fruits() {
        return Arbitraries.integers().between(1, 6);
    }

    @Property
    void testDrawFruits(@ForAll("fruits") int type) {
        Fruit fruit = mock(Fruit.class);
        when(fruit.getType()).thenReturn(type);

        arena.setFruits(new ArrayList<>(List.of(fruit)));

        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        arenaViewer.drawElements(graphics);

        verify(fruitViewer, times(1)).draw(any(), any(), eq(type));
        reset(fruitViewer);
    }

    @Provide
    public Arbitrary<Integer> monsters() {
        return Arbitraries.integers().between(1, 5);
    }

    @Property
    void testDrawMonsters(@ForAll("monsters") int type) {
        Monster monster = mock(Monster.class);
        when(monster.getType()).thenReturn(type);

        arena.setMonsters(new ArrayList<>(List.of(monster)));

        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        arenaViewer.drawElements(graphics);

        verify(monsterViewer, only()).draw(any(), any(), eq(type));
        reset(monsterViewer);
    }

    @Provide
    public Arbitrary<Integer> walls() {
        return Arbitraries.integers().between(1, 11);
    }

    @Property
    void testDrawWalls(@ForAll("walls") int wallType,
                       @ForAll("fruits") int fruitType,
                       @ForAll("monsters") int monsterType,
                       @ForAll GUI.ACTION action) {
        Wall wall = mock(Wall.class);
        when(wall.getType()).thenReturn(wallType);
        when(wall.getPosition()).thenReturn(new Position(1, 1));

        Fruit fruit = mock(Fruit.class);
        when(fruit.getType()).thenReturn(fruitType);
        when(fruit.getPosition()).thenReturn(new Position(1, 1));

        Monster monster = mock(Monster.class);
        when(monster.getType()).thenReturn(monsterType);
        when(monster.getPosition()).thenReturn(new Position(1, 1));
        when(monster.getLastAction()).thenReturn(action);

        arena.setWalls(new ArrayList<>(List.of(wall)));
        arena.setFruits(new ArrayList<>(List.of(fruit)));
        arena.setMonsters(new ArrayList<>(List.of(monster)));

        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        arenaViewer.drawElements(graphics);

        if(wallType == 2){
            verify(wallViewer, atLeast(1)).draw(any(), any(), eq(wallType));
        } else {
            if(fruitType > 5){
                verify(wallViewer, atLeast(1)).draw(any(), any(), eq(1));
            }else {
                verify(wallViewer, atLeast(1)).draw(any(), any(), eq(fruitType + 2));
            }

            if(monsterType == 2) {
                switch (action) {
                    case UP:
                        verify(wallViewer, atLeast(1)).draw(any(), any(), eq(9));
                        break;
                    case RIGHT:
                        verify(wallViewer, atLeast(1)).draw(any(), any(), eq(10));
                        break;
                    case LEFT:
                        verify(wallViewer, atLeast(1)).draw(any(), any(), eq(11));
                        break;
                    default:
                        verify(wallViewer, atLeast(1)).draw(any(), any(), eq(8));
                }
            }else{
                verify(wallViewer, atMost(1)).draw(any(), any(), eq(9));
            }
        }

        reset(wallViewer);
    }

    @Property
    void testDrawIceCream() {
        IceCream iceCream = mock(IceCream.class);
        arena.setIceCream(iceCream);

        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        arenaViewer.drawElements(graphics);

        verify(iceCreamViewer, only()).draw(any(), any(), eq(1));
        reset(iceCreamViewer);
    }

    @Property
    void testDrawHotFloorsWithMonsters() {
        HotFloor hotFloor = mock(HotFloor.class);
        when(hotFloor.getPosition()).thenReturn(new Position(1, 1));

        Monster monster = mock(Monster.class);
        when(monster.getType()).thenReturn(1);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        arena.setHotFloors(new ArrayList<>(List.of(hotFloor)));

        Object[][] monsterParams = {
                {1, GUI.ACTION.UP, 1, false}, {1, GUI.ACTION.RIGHT, 2, false}, {1, GUI.ACTION.LEFT, 3, false}, {1, null, 4, false},
                {2, GUI.ACTION.UP, 5, false}, {2, GUI.ACTION.RIGHT, 6, false}, {2, GUI.ACTION.LEFT, 7, false}, {2, null, 8, false},
                {3, GUI.ACTION.UP, 9, false}, {3, GUI.ACTION.RIGHT, 10, false}, {3, GUI.ACTION.LEFT, 11, false}, {3, null, 12, false},
                {3, GUI.ACTION.UP, 13, true}, {3, GUI.ACTION.RIGHT, 14, true}, {3, GUI.ACTION.LEFT, 15, true}, {3, null, 16, true},
                {4, GUI.ACTION.UP, 17, false}, {4, GUI.ACTION.RIGHT, 18, false}, {4, GUI.ACTION.LEFT, 19, false}, {4, null, 20, false},
        };

        for (Object[] params : monsterParams) {
            int type = (int) params[0];
            GUI.ACTION action = (GUI.ACTION) params[1];
            if(action == null) action = GUI.ACTION.DOWN;
            int expected = (int) params[2];
            boolean running = (boolean) params[3];

            when(monster.getType()).thenReturn(type);
            when(monster.getLastAction()).thenReturn(action);
            when(monster.isRunning()).thenReturn(running);

            arena.setMonsters(new ArrayList<>(List.of(monster)));
            ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
            arenaViewer.drawElements(graphics);

            arenaViewer.drawElements(graphics);
            verify(hotFloorViewer, atLeast(1)).draw(hotFloor, graphics, expected);
            reset(hotFloorViewer);
        }
    }

    @Property
    void testDrawHotFloorsWithFruit(@ForAll("fruits") int fruitType) {
        HotFloor hotFloor = mock(HotFloor.class);
        when(hotFloor.getPosition()).thenReturn(new Position(1, 1));

        Fruit fruit = mock(Fruit.class);
        when(fruit.getType()).thenReturn(fruitType);
        when(fruit.getPosition()).thenReturn(new Position(1, 1));

        arena.setHotFloors(new ArrayList<>(List.of(hotFloor)));

        arena.setFruits(new ArrayList<>(List.of(fruit)));

        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        arenaViewer.drawElements(graphics);

        if(fruitType < 6){
            int expected = fruitType + 20;
            verify(hotFloorViewer, times(1)).draw(any(), any(), eq(expected));
        }else{
            verify(hotFloorViewer, never()).draw(any(), any(), eq(21));
        }
        reset(hotFloorViewer);
    }

    @Property
    void testDrawHotFloorsWithIceCream() {
        HotFloor hotFloor = mock(HotFloor.class);
        when(hotFloor.getPosition()).thenReturn(new Position(1, 1));

        arena.setHotFloors(new ArrayList<>(List.of(hotFloor)));

        IceCream iceCream = mock(IceCream.class);
        when(iceCream.getPosition()).thenReturn(new Position(1, 1));

        Object[][] iceCreamParams = {
                {GUI.ACTION.UP, 26}, {GUI.ACTION.RIGHT, 27}, {GUI.ACTION.LEFT, 28}, {null, 29},
        };

        for (Object[] params : iceCreamParams) {
            GUI.ACTION action = (GUI.ACTION) params[0];
            if(action == null) action = GUI.ACTION.DOWN;
            int expected = (int) params[1];

            when(iceCream.getLastMovement()).thenReturn(action);
            arena.setIceCream(iceCream);

            ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
            arenaViewer.drawElements(graphics);

            arenaViewer.drawElements(graphics);
            verify(hotFloorViewer, atLeast(1)).draw(hotFloor, graphics, expected);
            reset(hotFloorViewer);
        }
    }

    @Property
    void testDrawHotFloorsEmptyArena() {
        HotFloor hotFloor = mock(HotFloor.class);
        when(hotFloor.getPosition()).thenReturn(new Position(1, 1));

        arena.setHotFloors(new ArrayList<>(List.of(hotFloor)));

        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        arenaViewer.drawElements(graphics);

        verify(hotFloorViewer, times(1)).draw(hotFloor, graphics, 0);
        reset(hotFloorViewer);
    }

    @Property
    void testGuiClearAndRefresh() throws IOException {
        ArenaViewer arenaViewer = new ArenaViewer(arena, fruitViewer, monsterViewer, wallViewer, hotFloorViewer, iceCreamViewer);
        Graphics graphics = mock(Graphics.class);
        arenaViewer.draw(graphics);

        verify(graphics, times(1)).clear();
        verify(graphics, times(1)).refresh();
    }
}
