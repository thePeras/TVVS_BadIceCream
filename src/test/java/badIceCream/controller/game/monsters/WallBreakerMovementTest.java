package badIceCream.controller.game.monsters;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.IceWall;
import badIceCream.model.game.elements.Wall;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class WallBreakerMovementTest {
    @Test
    void testStepTimeBoundary() throws IOException {
        Monster monster = mock(Monster.class);

        WallBreakerMovement movement = new WallBreakerMovement();
        Arena arena = new Arena(2, 2);
        movement.step(monster, arena, 150, 50);
        verify(monster, never()).setLastAction(any());
        verify(monster, never()).setPosition(any());
    }

    @Test
    void testArenaWithIceWall() throws IOException {
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));

        Arena arena = new Arena(3, 3);
        arena.setIceCream(new IceCream(0, 0));
        arena.setWalls(new ArrayList<Wall>(List.of(new IceWall(1, 0), new IceWall(1, 2), new IceWall(0, 1), new IceWall(2, 1))));
        arena.setMonsters(List.of(monster));

        WallBreakerMovement movement = new WallBreakerMovement();
        movement.step(monster, arena, 200, 0);

        assertEquals(3, arena.getWalls().size());
    }
}
