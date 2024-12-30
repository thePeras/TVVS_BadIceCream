package badIceCream.states;

import badIceCream.controller.menu.MainMenuController;
import badIceCream.model.menu.MainMenu;
import badIceCream.viewer.menu.MainMenuViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class MainMenuStateTest {
    @Test
    void testSuperConstructorCalled() {
        MainMenu mockMainMenu = mock(MainMenu.class);
        MainMenuController mockController = mock(MainMenuController.class);
        MainMenuViewer mockViewer = mock(MainMenuViewer.class);

        MainMenuState MainMenuState = new MainMenuState(mockMainMenu, mockController, mockViewer, 5);

        assertEquals(mockMainMenu, MainMenuState.getModel());
        assertEquals(5, MainMenuState.getLevel());
    }
}
