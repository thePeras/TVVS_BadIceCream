package badIceCream.controller.game;

import badIceCream.audio.AudioController;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonsterControllerTest {
    private Monster monster;
    private StepMonsters step;
    private Arena arena;
    private MonsterController controller;

    @BeforeEach
    public void setUp() {
        monster = mock(Monster.class);
        step = mock(StepMonsters.class);
        arena = mock(Arena.class);
        when(arena.getIceCream()).thenReturn(new IceCream(0, 0));
        controller = new MonsterController(arena, step, monster);
    }

    @Test
    public void testStepRunnerToggle() throws IOException {
        try(MockedStatic<AudioController> dummyStatic = mockStatic(AudioController.class)) {
            when(monster.getType()).thenReturn(3);
            when(monster.getPosition()).thenReturn(new Position(1, 1));

            controller.step(15000L);
            verify(monster).startRunning();
            dummyStatic.verify(AudioController::playRunnerMonsterSound);
            controller.step(30000L);
            verify(monster).stopRunning();
            controller.step(0L);
            verify(monster, atMost(1)).startRunning();
            verify(monster, atMost(1)).stopRunning();
        }
    }

    @Test
    public void testStepRegularBehavior() throws IOException {
        long time = 1000L;
        when(monster.getType()).thenReturn(1);

        controller.step(time);
        assertFalse(controller.runnerOn);

        verify(step).step(eq(monster), eq(arena), eq(time), anyLong());
        verify(monster, never()).startRunning();
        verify(monster, never()).stopRunning();
    }
}

