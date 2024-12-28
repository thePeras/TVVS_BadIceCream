package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.InstructionsMenuSecondPageState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;

public class InstructionsMenuSecondPageControllerTest {

    private InstructionsMenuSecondPageController controller;
    private InstructionsMenuSecondPage model;
    private Game game;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        model = mock(InstructionsMenuSecondPage.class);
        game = mock(Game.class);
        when(game.getState()).thenReturn(new InstructionsMenuSecondPageState(model, 1));
        controller = new InstructionsMenuSecondPageController(model);
        action = GUI.ACTION.PAUSE;
    }

    @Test
    public void testStepWithPauseAction() throws IOException {
        controller.step(game, action, 0);
        verify(game).setState(any(MainMenuState.class), eq(Type.nulo), eq(0), eq(0));
    }

    @Test
    public void testStepWithLeftAction() throws IOException {
        action = GUI.ACTION.LEFT;
        controller.step(game, action, 0);
        verify(game).setState(any(InstructionsMenuFirstPageState.class), eq(Type.nulo), eq(0), eq(0));
    }

    @Test
    public void testStepWithInvalidAction() throws IOException {
        action = GUI.ACTION.RIGHT;
        controller.step(game, action, 0);
        verifyNoInteractions(game);
    }
}
