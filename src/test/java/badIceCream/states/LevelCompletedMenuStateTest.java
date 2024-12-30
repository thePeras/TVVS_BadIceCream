package badIceCream.states;

import badIceCream.controller.menu.LevelCompletedMenuController;
import badIceCream.model.menu.LevelCompletedMenu;
import badIceCream.viewer.menu.LevelCompletedMenuViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class LevelCompletedMenuStateTest {
    @Test
    void testSuperConstructorCalled() {
        LevelCompletedMenu mockLevelCompletedMenu = mock(LevelCompletedMenu.class);
        LevelCompletedMenuController mockController = mock(LevelCompletedMenuController.class);
        LevelCompletedMenuViewer mockViewer = mock(LevelCompletedMenuViewer.class);

        LevelCompletedMenuState LevelCompletedMenuState = new LevelCompletedMenuState(mockLevelCompletedMenu, mockController, mockViewer, 5);

        assertEquals(mockLevelCompletedMenu, LevelCompletedMenuState.getModel());
        assertEquals(5, LevelCompletedMenuState.getLevel());
    }
}
