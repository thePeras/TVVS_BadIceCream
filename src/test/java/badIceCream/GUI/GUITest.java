package badIceCream.GUI;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GUITest {

    public class TestableGUI extends GUI {

        private final Terminal mockTerminal;

        public TestableGUI(Terminal mockTerminal) {
            this.mockTerminal = mockTerminal;
        }

        @Override
        protected Terminal createTerminal(){
            return mockTerminal;
        }
    }

    @Test
    void testCreateScreen(){
        assertAll(() -> {
            Terminal mockTerminal = mock(Terminal.class);
            TestableGUI testableGUI = new TestableGUI(mockTerminal);

            GUI gui = new MenuGraphics(10, 10);

            Screen screen = testableGUI.createScreen(gui.createTerminal());

            assertNotNull(screen);
            assertNull(screen.getCursorPosition());
        });
    }
}
