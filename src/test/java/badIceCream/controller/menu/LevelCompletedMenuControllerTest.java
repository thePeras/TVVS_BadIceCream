package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.states.GameState;
import badIceCream.states.LevelCompletedMenuState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;

public class LevelCompletedMenuControllerTest {

    private LevelCompletedMenuController controller;
    private LevelCompletedMenu model;
    private Game game;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        model = mock(LevelCompletedMenu.class);
        game = mock(Game.class);
        LevelCompletedMenuState state = mock(LevelCompletedMenuState.class);
        when(state.getLevel()).thenReturn(1);
        when(game.getState()).thenReturn(state);
        controller = new LevelCompletedMenuController(model);
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
    public void testStepWithSelectActionNextLevelSelected() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedNextLevel()).thenReturn(true);

        controller.step(game, action, 0);
        verify(game).setState(any(GameState.class), eq(Type.game), eq(14), eq(18));
    }

    @Test
    public void testStepWithSelectActionQuitToMainMenuSelected() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedQuitToMainMenu()).thenReturn(true);
        controller.step(game, action, 0);
        verify(game).setState(any(MainMenuState.class), eq(Type.menu), eq(140), eq(50));
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
        when(model.isSelectedNextLevel()).thenReturn(false);
        when(model.isSelectedQuitToMainMenu()).thenReturn(false);
        controller.step(game, action, 0);
        verifyNoInteractions(game);
    }
}

