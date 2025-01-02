package badIceCream.controller.game;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import badIceCream.states.GameOverMenuState;
import badIceCream.states.GameState;
import badIceCream.states.LevelCompletedMenuState;
import badIceCream.states.PauseMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class ArenaControllerTest {
    private Arena arena;
    private IceCreamController iceCreamController;
    private ArenaController arenaController;
    private Game game;

    private IceCream iceCream;

    @BeforeEach
    public void setUp() {
        arena = mock(Arena.class);
        iceCreamController = mock(IceCreamController.class);
        game = mock(Game.class);
        GameState gameState = mock(GameState.class);
        when(gameState.getLevel()).thenReturn(1);
        when(game.getState()).thenReturn(gameState);

        iceCream = mock(IceCream.class);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(arena.getFruits()).thenReturn(List.of(mock(Fruit.class)));

        Monster monster1 = mock(Monster.class);
        when(monster1.getType()).thenReturn(1);
        when(monster1.getPosition()).thenReturn(new Position(1, 1));
        MonsterController monsterController1 = new MonsterController(arena, mock(StepMonsters.class), monster1);
        List<MonsterController> monsterControllers = new ArrayList<>();
        monsterControllers.add(monsterController1);

        Monster monster2 = mock(Monster.class);
        when(monster2.getType()).thenReturn(3);
        when(monster2.getPosition()).thenReturn(new Position(1, 2));
        MonsterController monsterController2 = new MonsterController(arena, mock(StepMonsters.class), monster2);
        monsterControllers.add(monsterController2);

        when(arena.getMonsters()).thenReturn(List.of(monster1, monster2));

        arenaController = new ArenaController(arena, iceCreamController, monsterControllers);
    }

    @Test
    public void testStepWithFruit() {
        assertAll(() -> {
            when(iceCreamController.eatFruit()).thenReturn(5);
            when(arena.getIceCream().isStrawberryActive()).thenReturn(false);

            arenaController.step(game, GUI.ACTION.NONE, 5000L);

            verify(arena.getIceCream()).setStrawberry(true);
        });
    }

    @Test
    public void testStepWithStrawberryTimeout() {
        assertAll(() -> {
            when(iceCreamController.eatFruit()).thenReturn(-1);
            when(arena.getIceCream().isStrawberryActive()).thenReturn(true);

            arenaController.step(game, GUI.ACTION.NONE, 15000L);

            verify(arena.getIceCream()).setStrawberry(false);
        });
    }

    @Test
    public void testStepWhenFruitsAreEmptyLevelCompleted() {
        assertAll(() -> {
            when(arena.getFruits()).thenReturn(new ArrayList<>());
            when(arena.getLevel()).thenReturn(2);
            when(game.getState().getLevel()).thenReturn(1);

            arenaController.step(game, GUI.ACTION.NONE, 5000L);
            verify(arena).generateNewFruits(2);
            arenaController.step(game, GUI.ACTION.NONE, 5000L);

            verify(game).setState(any(LevelCompletedMenuState.class), eq(Type.win), eq(140), eq(50));
        });
    }

    @Test
    public void testStepWhenIceCreamIsDead() {
        assertAll(() -> {
            when(iceCream.getAlive()).thenReturn(false);

            arenaController.step(game, GUI.ACTION.NONE, 5000L);
            verify(game).setState(any(GameOverMenuState.class), eq(Type.gameOver), eq(140), eq(50));
        });
    }

    @Test
    public void testStepWhenPaused() {
        assertAll(() -> {
            when(iceCream.getAlive()).thenReturn(true);

            arenaController.step(game, GUI.ACTION.PAUSE, 5000L);

            verify(game).setState(any(PauseMenuState.class), eq(Type.menu), eq(140), eq(50));
        });
    }

    @Test
    public void testStepNormal() {
        assertAll(() -> {
            when(arena.getFruits()).thenReturn(new ArrayList<>(List.of(mock(Fruit.class))));

            IceCream iceCream = mock(IceCream.class);
            when(iceCream.getAlive()).thenReturn(true);
            when(arena.getIceCream()).thenReturn(iceCream);

            arenaController.step(game, GUI.ACTION.NONE, 5000L);

            verify(iceCreamController).step(game, GUI.ACTION.NONE, 5000L);
        });
    }

    @Test
    public void testStepMonsters() {
        assertAll(() -> {
            arenaController.stepMonsters(5000L);

            for (Monster monster : arena.getMonsters()) {
                verify(monster, atLeast(3)).getType();
            }
        });
    }
}

