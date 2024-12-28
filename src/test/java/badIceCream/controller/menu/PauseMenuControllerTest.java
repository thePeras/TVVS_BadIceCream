package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.menu.PauseMenu;
import badIceCream.states.GameState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;

public class PauseMenuControllerTest {

    private PauseMenuController controller;
    private PauseMenu model;
    private Game game;
    private GameState parent;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        model = mock(PauseMenu.class);
        game = mock(Game.class);
        parent = mock(GameState.class);
        Arena arena = mock(Arena.class);
        when(arena.getWidth()).thenReturn(100);
        when(arena.getHeight()).thenReturn(50);
        when(parent.getModel()).thenReturn(arena);

        when(game.getState()).thenReturn(parent);
        controller = new PauseMenuController(model, parent);
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
    public void testStepWithSelectActionResumeSelected() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedResume()).thenReturn(true);
        controller.step(game, action, 0);
        verify(game).setState(parent, Type.game, parent.getModel().getWidth(), parent.getModel().getHeight());
    }

    @Test
    public void testStepWithSelectActionMenuSelected() throws IOException {
        action = GUI.ACTION.SELECT;
        when(model.isSelectedMenu()).thenReturn(true);
        controller.step(game, action, 0);
        verify(game).setState(any(MainMenuState.class), eq(Type.nulo), eq(0), eq(0));
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
        when(model.isSelectedResume()).thenReturn(false);
        when(model.isSelectedMenu()).thenReturn(false);
        controller.step(game, action, 0);
        verifyNoInteractions(game);
    }
}

