package badIceCream.controller.game.monsters;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class JumperMovementTest {
    @Test
    void testStepTimeBoundary() throws IOException {
        Monster monster = mock(Monster.class);

        JumperMovement movement = new JumperMovement();
        Arena arena = new Arena(2, 2);
        movement.step(monster, arena, 150, 50);
        verify(monster, never()).setLastAction(any());
        verify(monster, never()).setPosition(any());
    }

    @Test
    void testStepTimeBoundary2() throws IOException {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        JumperMovement movement = new JumperMovement();
        Arena arena = new Arena(2, 2);
        arena.setIceCream(new IceCream(1, 1));
        arena.setWalls(new ArrayList<>());
        arena.setMonsters(new ArrayList<>());

        movement.step(monster, arena, 200, 0);
        verify(monster, atLeast(1)).setLastAction(any());
    }
}
