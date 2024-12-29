package badIceCream.controller.game.monsters;

import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
}
