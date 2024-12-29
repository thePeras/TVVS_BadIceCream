package badIceCream.controller.game.monsters;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

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
}
