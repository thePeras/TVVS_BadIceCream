package badIceCream.controller.game.monsters;

import badIceCream.controller.game.StepMonsters;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.StoneWall;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class RunnerMovementEnabledTest {

    @Test
    void testStepTimeBoundary() throws IOException {
        Monster monster = mock(Monster.class);

        RunnerMovementEnabled movement = new RunnerMovementEnabled();
        Arena arena = new Arena(2, 2);
        movement.step(monster, arena, 3, 3);
        verify(monster, never()).setLastAction(any());
        verify(monster, never()).setPosition(any());
    }

    @Test
    void testStepTimeBoundary2() throws IOException {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        RunnerMovementEnabled movement = new RunnerMovementEnabled();
        Arena arena = new Arena(2, 2);
        arena.setIceCream(new IceCream(1, 1));
        arena.setWalls(new ArrayList<>());
        arena.setMonsters(new ArrayList<>());
        movement.step(monster, arena, 5, 0);
        verify(monster, atLeast(1)).setLastAction(any());
    }

    @Property
    void testStep() throws IOException {
        RunnerMovementEnabled movement = new RunnerMovementEnabled();
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(0, 0));

        Arena arena = new Arena(3, 3);
        arena.setWalls(new ArrayList<>());
        arena.setIceCream(new IceCream(2, 0));
        arena.setMonsters(new ArrayList<>(List.of(monster)));

        movement.step(monster, arena, 225, 0);
        verify(monster, atLeast(1)).setPosition(new Position(1, 0));
        when(monster.getPosition()).thenReturn(new Position(1, 0));
        movement.step(monster, arena, 225, 0);
        verify(monster, atLeast(1)).setPosition(new Position(2, 0));

        reset(monster);
        arena.setIceCream(new IceCream(2, 0));
        when(monster.getPosition()).thenReturn(new Position(0, 1));
        movement.step(monster, arena, 225, 0);
        verify(monster, atLeast(1)).setPosition(new Position(0, 0));

        reset(monster);
        arena.setIceCream(new IceCream(0, 2));
        when(monster.getPosition()).thenReturn(new Position(2, 1));
        movement.step(monster, arena, 225, 0);
        verify(monster, atLeast(1)).setPosition(new Position(2, 2));
    }
}
