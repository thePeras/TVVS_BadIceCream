package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.states.GameState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;

public class GameOverMenuControllerTest {

    private GameOverMenuController controller;
    private GameOverMenu model;
    private Game game;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        model = mock(GameOverMenu.class);
        game = mock(Game.class);
        GameState gameState = mock(GameState.class);
        when(gameState.getLevel()).thenReturn(1);
        when(game.getState()).thenReturn(gameState);
        controller = new GameOverMenuController(model);
        action = GUI.ACTION.UP;
    }

    @Test
    public void testStepWithUpAction() throws IOException {
        controller.step(game, action, 0);
        verify(model).previousEntry();
        verifyNoMoreInteractions(game);
    }

    @Test
    public void testStepWithDownAction() throws IOException {
        action = GUI.ACTION.DOWN;
        controller.step(game, action, 0);
        verify(model).nextEntry();
        verifyNoMoreInteractions(game);
    }

    @Test
    public void testStepWithSelectActionQuitToMainMenuSelected() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedQuitToMainMenu()).thenReturn(true);
        controller.step(game, action, 0);
        verify(game).setState(any(MainMenuState.class), eq(Type.menu), eq(140), eq(50));
    }

    @Test
    public void testStepWithSelectActionPlayAgainSelected() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedPlayAgain()).thenReturn(true);
        controller.step(game, action, 0);
        verify(game).setState(any(GameState.class), eq(Type.game), eq(14), eq(18));
    }

    @Test
    public void testStepWithInvalidAction() throws IOException {
        action = GUI.ACTION.LEFT;
        controller.step(game, action, 0);
        verifyNoInteractions(game);
    }

    @Test
    public void testStepWithSelectActionUnhandled() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedQuitToMainMenu()).thenReturn(false);
        when(model.isSelectedPlayAgain()).thenReturn(false);
        controller.step(game, action, 0);
        verifyNoInteractions(game);
    }
}

