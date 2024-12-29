package badIceCream.controller.game.monsters;

import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.mockito.Mockito.*;

class RunnerMovementEnabledTest {
    /*
    @Property
    void testStepWithMultipleArenas(@ForAll("arenaScenarios") ArenaScenario scenario) {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        DefaultMovement movement = new DefaultMovement();
        scenario.arena.setMonsters(new ArrayList<>(List.of(monster)));

        try {
            movement.step(monster, scenario.arena, 225, 0);
            verify(monster).setLastAction(scenario.expectedAction);
            verify(monster).setPosition(scenario.expectedPosition);
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Provide
    Arbitrary<ArenaScenario> arenaScenarios() {
        return Arbitraries.of(
                new ArenaScenario(
                        setupArena(List.of(new IceWall(0, 1), new IceWall(2, 1), new IceWall(1, 2))),
                        GUI.ACTION.UP,
                        new Position(1, 0)
                ),
                new ArenaScenario(
                        setupArena(List.of(new IceWall(0, 1), new IceWall(2, 1), new IceWall(1, 0))),
                        GUI.ACTION.DOWN,
                        new Position(1, 2)
                ),
                new ArenaScenario(
                        setupArena(List.of(new IceWall(1, 0), new IceWall(2, 1), new IceWall(1, 2))),
                        GUI.ACTION.LEFT,
                        new Position(0, 1)
                ),
                new ArenaScenario(
                        setupArena(List.of(new IceWall(0, 1), new IceWall(1, 0), new IceWall(1, 2))),
                        GUI.ACTION.RIGHT,
                        new Position(2, 1)
                )
        );
    }

    static Arena setupArena(List<Wall> walls) {
        Arena arena = new Arena(3, 3);
        arena.setWalls(walls);
        arena.setIceCream(new IceCream(0, 0));
        return arena;
    }

     static class ArenaScenario {
        Arena arena;
        GUI.ACTION expectedAction;
        Position expectedPosition;

        ArenaScenario(Arena arena, GUI.ACTION expectedAction, Position expectedPosition) {
            this.arena = arena;
            this.expectedAction = expectedAction;
            this.expectedPosition = expectedPosition;
        }
    }

    @Test
    void testFullArena() {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        DefaultMovement movement = new DefaultMovement();
        Arena arena = new Arena(3, 3);
        arena.setWalls(List.of(new IceWall(0, 1), new IceWall(2, 1), new IceWall(1, 0), new IceWall(1, 2)));
        arena.setIceCream(new IceCream(0, 0));

        try {
            movement.step(monster, arena, 225, 0);
            verify(monster, never()).setLastAction(any());
            verify(monster, never()).setPosition(any());
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    void testStepTimeBoundary() {
        Monster monster = mock(Monster.class);

        DefaultMovement movement = new DefaultMovement();
        Arena arena = new Arena(2, 2);
        try {
            movement.step(monster, arena, 200, 50);
            verify(monster, never()).setLastAction(any());
            verify(monster, never()).setPosition(any());
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }
    */

    @Test
    void testStepTimeBoundary() throws IOException {
        Monster monster = mock(Monster.class);

        RunnerMovementEnabled movement = new RunnerMovementEnabled();
        Arena arena = new Arena(2, 2);
        movement.step(monster, arena, 3, 2);
        verify(monster, never()).setLastAction(any());
        verify(monster, never()).setPosition(any());
    }
}
