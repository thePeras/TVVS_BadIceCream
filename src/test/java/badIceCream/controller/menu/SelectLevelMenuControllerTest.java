package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.states.GameState;
import badIceCream.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class SelectLevelMenuControllerTest {

    private SelectLevelMenuController controller;
    private SelectLevelMenu model;
    private Game game;
    private GUI.ACTION action;

    @BeforeEach
    public void setUp() {
        model = mock(SelectLevelMenu.class);
        game = mock(Game.class);
        when(game.getState()).thenReturn(mock(GameState.class));
        controller = new SelectLevelMenuController(model);
        action = GUI.ACTION.LEFT;
    }

    @Test
    public void testStepWithLeftAction() {
        assertAll(() -> {
            controller.step(game, action, 0);
            verify(model).previousEntry();
            verifyNoMoreInteractions(game);
        });
    }

    @Test
    public void testStepWithRightAction() {
        assertAll(() -> {
            action = GUI.ACTION.RIGHT;
            controller.step(game, action, 0);
            verify(model).nextEntry();
            verifyNoMoreInteractions(game);
        });
    }

    @Test
    public void testStepWithSelectActionLevel1Selected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedLevel1()).thenReturn(true);
            when(game.getState().getLevel()).thenReturn(1);
            controller.step(game, action, 0);
            verify(game).setState(any(GameState.class), eq(Type.game), eq(14), eq(18));
        });
    }

    @Test
    public void testStepWithSelectActionLevel2Selected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedLevel2()).thenReturn(true);
            when(game.getState().getLevel()).thenReturn(2);
            controller.step(game, action, 0);
            verify(game).setState(any(GameState.class), eq(Type.game), eq(19), eq(22));
        });
    }

    @Test
    public void testStepWithSelectActionLevel3Selected() {
        assertAll(()-> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedLevel3()).thenReturn(true);
            when(game.getState().getLevel()).thenReturn(3);
            controller.step(game, action, 0);
            verify(game).setState(any(GameState.class), eq(Type.game), eq(17), eq(17));
        });
    }

    @Test
    public void testStepWithSelectActionLevel4Selected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedLevel4()).thenReturn(true);
            when(game.getState().getLevel()).thenReturn(4);
            controller.step(game, action, 0);
            verify(game).setState(any(GameState.class), eq(Type.game), eq(21), eq(19));
        });
    }

    @Test
    public void testStepWithSelectActionLevel5Selected() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedLevel5()).thenReturn(true);
            when(game.getState().getLevel()).thenReturn(5);
            controller.step(game, action, 0);
            verify(game).setState(any(GameState.class), eq(Type.game), eq(20), eq(20));
        });
    }

    @Test
    public void testStepWithInvalidAction() {
        assertAll(() -> {
            action = GUI.ACTION.UP;
            controller.step(game, action, 0);
            verifyNoInteractions(game);
        });
    }

    @Test
    public void testStepWithSelectActionUnhandled() {
        assertAll(() -> {
            action = GUI.ACTION.SELECT;
            when(model.isSelectedLevel1()).thenReturn(false);
            when(model.isSelectedLevel2()).thenReturn(false);
            when(model.isSelectedLevel3()).thenReturn(false);
            when(model.isSelectedLevel4()).thenReturn(false);
            when(model.isSelectedLevel5()).thenReturn(false);
            controller.step(game, action, 0);
            verifyNoInteractions(game);
        });
    }
}
