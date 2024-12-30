package badIceCream.controller.game.monsters;

import badIceCream.GUI.GUI;
import badIceCream.controller.game.StepMonsters;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.StoneWall;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class StepMonstersTest {
    @Property
    void testStepWithMultipleArenas(@ForAll("arenaScenarios") ArenaScenario scenario,
                                    @ForAll("movementStrategies") StepMonsters movementStrategy) throws IOException {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));
        scenario.arena.setMonsters(new ArrayList<>(List.of(monster)));

        movementStrategy.step(monster, scenario.arena, 225, 0);
        verify(monster).setLastAction(scenario.expectedAction);
        verify(monster).setPosition(scenario.expectedPosition);
    }

    @Property
    void testFullArena(@ForAll("movementStrategies") StepMonsters movementStrategy) throws IOException {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        Arena arena = new Arena(3, 3);
        arena.setWalls(List.of(new StoneWall(0, 1), new StoneWall(2, 1), new StoneWall(1, 0), new StoneWall(1, 2)));
        arena.setIceCream(new IceCream(0, 0));

        movementStrategy.step(monster, arena, 225, 0);
        verify(monster, never()).setLastAction(any());
        verify(monster, never()).setPosition(any());
    }

    @Provide
    Arbitrary<ArenaScenario> arenaScenarios() {
        return Arbitraries.of(
                new ArenaScenario(
                        setupArena(List.of(new StoneWall(0, 1), new StoneWall(2, 1), new StoneWall(1, 2))),
                        GUI.ACTION.UP,
                        new Position(1, 0)
                ),
                new ArenaScenario(
                        setupArena(List.of(new StoneWall(0, 1), new StoneWall(2, 1), new StoneWall(1, 0))),
                        GUI.ACTION.DOWN,
                        new Position(1, 2)
                ),
                new ArenaScenario(
                        setupArena(List.of(new StoneWall(1, 0), new StoneWall(2, 1), new StoneWall(1, 2))),
                        GUI.ACTION.LEFT,
                        new Position(0, 1)
                ),
                new ArenaScenario(
                        setupArena(List.of(new StoneWall(0, 1), new StoneWall(1, 0), new StoneWall(1, 2))),
                        GUI.ACTION.RIGHT,
                        new Position(2, 1)
                )
        );
    }

    static Arena setupArena(List<Wall> walls) {
        Arena arena = new Arena(3, 3);
        arena.setWalls(walls);
        arena.setIceCream(new IceCream(1, 1));
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

    @Provide
    public Arbitrary<StepMonsters> movementStrategies() {
        return Arbitraries.of(new DefaultMovement(), new RunnerMovementDisabled(), new JumperMovement(), new WallBreakerMovement(), new RunnerMovementEnabled());
    }
}
