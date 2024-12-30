package badIceCream.states;

import badIceCream.controller.menu.InstructionsMenuFirstPageController;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.viewer.menu.InstructionsMenuFirstPageViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InstructionsMenuFirstPageStateTest {
    @Test
    void testSuperConstructorCalled() {
        InstructionsMenuFirstPage mockInstructionsMenuFirstPage = mock(InstructionsMenuFirstPage.class);
        InstructionsMenuFirstPageController mockController = mock(InstructionsMenuFirstPageController.class);
        InstructionsMenuFirstPageViewer mockViewer = mock(InstructionsMenuFirstPageViewer.class);

        InstructionsMenuFirstPageState InstructionsMenuFirstPageState = new InstructionsMenuFirstPageState(mockInstructionsMenuFirstPage, mockController, mockViewer, 5);

        assertEquals(mockInstructionsMenuFirstPage, InstructionsMenuFirstPageState.getModel());
        assertEquals(5, InstructionsMenuFirstPageState.getLevel());
    }
}
