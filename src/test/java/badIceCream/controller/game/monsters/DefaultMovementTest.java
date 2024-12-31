package badIceCream.controller.game.monsters;

import static org.mockito.Mockito.*;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DefaultMovementTest {
    @Test
    void testStepTimeBoundary() throws IOException {
        Monster monster = mock(Monster.class);

        DefaultMovement movement = new DefaultMovement();
        Arena arena = new Arena(2, 2);
        movement.step(monster, arena, 200, 50);
        verify(monster, never()).setLastAction(any());
        verify(monster, never()).setPosition(any());
    }
}
