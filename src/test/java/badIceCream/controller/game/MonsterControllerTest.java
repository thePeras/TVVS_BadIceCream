package badIceCream.controller.game;

import badIceCream.audio.AudioController;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.*;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonsterControllerTest {
    @Property
    public void testStepRunnerToggle(@ForAll("types") int monsterType){
        assertAll(() -> {
            try(MockedStatic<AudioController> dummyStatic = mockStatic(AudioController.class)) {

                Monster monster = mock(Monster.class);
                StepMonsters step = mock(StepMonsters.class);
                Arena arena = mock(Arena.class);
                when(arena.getIceCream()).thenReturn(new IceCream(0, 0));
                MonsterController controller = new MonsterController(arena, step, monster);

                when(monster.getType()).thenReturn(monsterType);
                when(monster.getPosition()).thenReturn(new Position(1, 1));

                controller.step(15000L);

                if(monsterType == 3) {
                    verify(monster).startRunning();
                    dummyStatic.verify(AudioController::playRunnerMonsterSound);
                    controller.step(30000L);
                    verify(monster).stopRunning();
                    controller.step(0L);
                    verify(monster, atMost(1)).startRunning();
                    verify(monster, atMost(1)).stopRunning();
                } else {
                    assertFalse(controller.runnerOn);
                    verify(step).step(eq(monster), eq(arena), eq(15000L), anyLong());
                    verify(monster, never()).startRunning();
                    verify(monster, never()).stopRunning();
                }
            }
        });
    }

    @Provide
    public Arbitrary<Integer> types() {
        return Arbitraries.integers().between(1, 3);
    }
}

