package badIceCream.states;

import badIceCream.controller.menu.SelectLevelMenuController;
import badIceCream.model.menu.SelectLevelMenu;
import badIceCream.viewer.menu.SelectLevelMenuViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class SelectLevelMenuStateTest {
    @Test
    void testSuperConstructorCalled() {
        SelectLevelMenu mockSelectLevelMenu = mock(SelectLevelMenu.class);
        SelectLevelMenuController mockController = mock(SelectLevelMenuController.class);
        SelectLevelMenuViewer mockViewer = mock(SelectLevelMenuViewer.class);

        SelectLevelMenuState selectLevelMenuState = new SelectLevelMenuState(mockSelectLevelMenu, mockController, mockViewer, 5);

        assertEquals(mockSelectLevelMenu, selectLevelMenuState.getModel());
        assertEquals(5, selectLevelMenuState.getLevel());
    }
}
