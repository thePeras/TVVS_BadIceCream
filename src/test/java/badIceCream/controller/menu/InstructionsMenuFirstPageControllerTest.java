package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.InstructionsMenuSecondPageState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;

public class InstructionsMenuFirstPageControllerTest {

    private InstructionsMenuFirstPageController controller;
    private Game game;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        InstructionsMenuFirstPage model = mock(InstructionsMenuFirstPage.class);
        game = mock(Game.class);
        when(game.getState()).thenReturn(new InstructionsMenuFirstPageState(model, 1));
        controller = new InstructionsMenuFirstPageController(model);
        action = GUI.ACTION.PAUSE;
    }

    @Test
    public void testStepWithPauseAction() throws IOException {
        controller.step(game, action, 0);
        verify(game).setState(any(MainMenuState.class), eq(Type.nulo), eq(0), eq(0));
    }

    @Test
    public void testStepWithRightAction() throws IOException {
        action = GUI.ACTION.RIGHT;
        controller.step(game, action, 0);
        verify(game).setState(any(InstructionsMenuSecondPageState.class), eq(Type.nulo), eq(0), eq(0));
    }

    @Test
    public void testStepWithInvalidAction() throws IOException {
        action = GUI.ACTION.LEFT;
        controller.step(game, action, 0);
        verifyNoInteractions(game);
    }
}
