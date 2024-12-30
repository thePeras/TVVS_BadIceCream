package badIceCream.states;

import badIceCream.controller.menu.InstructionsMenuSecondPageController;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.viewer.menu.InstructionsMenuSecondPageViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InstructionsMenuSecondPageStateTest {
    @Test
    void testSuperConstructorCalled() {
        InstructionsMenuSecondPage mockInstructionsMenuSecondPage = mock(InstructionsMenuSecondPage.class);
        InstructionsMenuSecondPageController mockController = mock(InstructionsMenuSecondPageController.class);
        InstructionsMenuSecondPageViewer mockViewer = mock(InstructionsMenuSecondPageViewer.class);

        InstructionsMenuSecondPageState InstructionsMenuSecondPageState = new InstructionsMenuSecondPageState(mockInstructionsMenuSecondPage, mockController, mockViewer, 5);

        assertEquals(mockInstructionsMenuSecondPage, InstructionsMenuSecondPageState.getModel());
        assertEquals(5, InstructionsMenuSecondPageState.getLevel());
    }
}
