package badIceCream.controller.game.monsters;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.Test;


class DefaultMovementTest {
    @Test
    void testStepTimeBoundary() {
        assertAll(() -> {
            Monster monster = mock(Monster.class);

            DefaultMovement movement = new DefaultMovement();
            Arena arena = new Arena(2, 2);
            movement.step(monster, arena, 200, 50);
            verify(monster, never()).setLastAction(any());
            verify(monster, never()).setPosition(any());
        });
    }
}
