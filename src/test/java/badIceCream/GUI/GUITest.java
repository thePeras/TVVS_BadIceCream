package badIceCream.GUI;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class GUITest {

    public class TestableGUI extends GUI {

        private final Terminal mockTerminal;

        public TestableGUI(Terminal mockTerminal) {
            this.mockTerminal = mockTerminal;
        }

        @Override
        protected Terminal createTerminal() throws IOException {
            return mockTerminal;
        }
    }

    @Test
    void testCreateScreen() throws IOException {
        Terminal mockTerminal = mock(Terminal.class);
        TestableGUI testableGUI = new TestableGUI(mockTerminal);

        GUI gui = new MenuGraphics(10, 10);

        Screen screen = testableGUI.createScreen(gui.createTerminal());

        assertNotNull(screen);
        assertNull(screen.getCursorPosition());
    }

}
