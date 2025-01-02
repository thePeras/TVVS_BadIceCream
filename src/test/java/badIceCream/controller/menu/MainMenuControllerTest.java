package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.MainMenu;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.MainMenuState;
import badIceCream.states.SelectLevelMenuState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class MainMenuControllerTest {

    private MainMenuController controller;
    private MainMenu model;
    private Game game;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        model = mock(MainMenu.class);
        game = mock(Game.class);
        when(game.getState()).thenReturn(new MainMenuState(model, 1));
        controller = new MainMenuController(model);
        action = GUI.ACTION.UP;
    }

    @Test
    public void testStepWithUpAction() {
        assertAll(() -> {
            controller.step(game, action, 0);
            verify(model).previousEntry();
            verifyNoMoreInteractions(game);
        });
    }

    @Test
    public void testStepWithDownAction() {
        assertAll(() -> {
            action = GUI.ACTION.DOWN;
            controller.step(game, action, 0);
            verify(model).nextEntry();
            verifyNoMoreInteractions(game);
        });
    }

    @Test
    public void testStepWithSelectActionExitSelected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedExit()).thenReturn(true);
            controller.step(game, action, 0);
            verify(game).setState(null, Type.nulo, 0, 0);
        });
    }

    @Test
    public void testStepWithSelectActionInstructionsSelected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedInstructions()).thenReturn(true);
            controller.step(game, action, 0);
            verify(game).setState(any(InstructionsMenuFirstPageState.class), eq(Type.nulo), eq(0), eq(0));
        });
    }

    @Test
    public void testStepWithSelectActionStartSelected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedStart()).thenReturn(true);
            controller.step(game, action, 0);
            verify(game).setState(any(SelectLevelMenuState.class), eq(Type.nulo), eq(0), eq(0));
        });
    }

    @Test
    public void testStepWithInvalidAction() {
        assertAll(() -> {
            action = GUI.ACTION.LEFT;
            controller.step(game, action, 0);
            verifyNoInteractions(game);
        });
    }
}
