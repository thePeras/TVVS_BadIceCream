package badIceCream.states;

import badIceCream.controller.menu.GameOverMenuController;
import badIceCream.model.menu.GameOverMenu;
import badIceCream.viewer.menu.GameOverMenuViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GameOverStateTest {
    @Test
    void testSuperConstructorCalled() {
        GameOverMenu mockGameOverMenu = mock(GameOverMenu.class);
        GameOverMenuController mockController = mock(GameOverMenuController.class);
        GameOverMenuViewer mockViewer = mock(GameOverMenuViewer.class);

        GameOverMenuState gameOverMenuState = new GameOverMenuState(mockGameOverMenu, mockController, mockViewer, 5);

        assertEquals(mockGameOverMenu, gameOverMenuState.getModel());
        assertEquals(5, gameOverMenuState.getLevel());
    }
}
