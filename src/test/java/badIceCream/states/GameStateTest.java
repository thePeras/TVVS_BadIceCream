package badIceCream.states;

import badIceCream.GUI.GUI;
import badIceCream.GUI.Graphics;
import badIceCream.Game;
import badIceCream.controller.game.ArenaController;
import badIceCream.model.game.arena.Arena;
import badIceCream.viewer.ArenaViewer;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameStateTest {
    @Test
    void testSuperConstructorCalled() {
        Arena mockArena = mock(Arena.class);
        ArenaController mockController = mock(ArenaController.class);
        ArenaViewer mockViewer = mock(ArenaViewer.class);

        GameState gameState = new GameState(mockArena, mockController, mockViewer, 5);

        assertEquals(mockArena, gameState.getModel());
        assertEquals(5, gameState.getLevel());
    }

    @Provide
    Arbitrary<Integer> levels(){
        return Arbitraries.integers().between(0, 6);
    }

    @Property
    void testIncreaseLevel(@ForAll("levels") int level){
        Arena mockArena = mock(Arena.class);
        ArenaController mockController = mock(ArenaController.class);
        ArenaViewer mockViewer = mock(ArenaViewer.class);

        GameState gameState = new GameState(mockArena, mockController, mockViewer, level);
        gameState.increaseLevel();

        if(level < 5) {
            assertEquals(level + 1, gameState.getLevel());
        } else {
            assertEquals(level, gameState.getLevel());
        }
    }

    @Property
    void testStep(@ForAll GUI.ACTION action) throws IOException {
        Arena mockArena = mock(Arena.class);
        ArenaController mockController = mock(ArenaController.class);
        ArenaViewer mockViewer = mock(ArenaViewer.class);

        GameState gameState = new GameState(mockArena, mockController, mockViewer, 0);

        Game game = mock(Game.class);
        Graphics graphics = mock(Graphics.class);
        when(graphics.getNextAction()).thenReturn(action);
        gameState.step(game, graphics, 0);

        verify(mockController).step(game, action, 0);
        verify(mockViewer).draw(graphics);
    }

    @Test
    void testStepMonsters() throws IOException {
        Arena mockArena = mock(Arena.class);
        ArenaController mockController = mock(ArenaController.class);
        ArenaViewer mockViewer = mock(ArenaViewer.class);

        GameState gameState = new GameState(mockArena, mockController, mockViewer, 0);
        gameState.stepMonsters(0);

        verify(mockController).stepMonsters(0);
    }
}
