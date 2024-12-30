package badIceCream.states;

import badIceCream.controller.menu.PauseMenuController;
import badIceCream.model.menu.PauseMenu;
import badIceCream.viewer.menu.PauseMenuViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PauseMenuStateTest {
    @Test
    void testSuperConstructorCalled() {
        PauseMenu mockPauseMenu = mock(PauseMenu.class);
        PauseMenuController mockController = mock(PauseMenuController.class);
        PauseMenuViewer mockViewer = mock(PauseMenuViewer.class);

        GameState gameState = mock(GameState.class);

        PauseMenuState PauseMenuState = new PauseMenuState(mockPauseMenu, gameState, mockController, mockViewer, 5);

        assertEquals(mockPauseMenu, PauseMenuState.getModel());
        assertEquals(5, PauseMenuState.getLevel());
    }
}
