package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.MainMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MainMenuViewerTest {

    private MainMenuViewer viewer;
    private MainMenu menu;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        menu = new MainMenu();
        graphics = Mockito.mock(Graphics.class);
        viewer = new MainMenuViewer(menu);
    }

    @Test
    public void testDrawTittle() {
        viewer.drawTitle(graphics);
        verify(graphics, times(14)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawSnowflake() {
        viewer.drawSnowflake(graphics);
        verify(graphics, times(40)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawElements() throws Exception {
        viewer.draw(graphics);

        verify(graphics, atLeast(54)).drawText(any(), anyString(), anyString());

        verify(graphics, times(1)).drawText(eq(new Position(63, 20)), anyString(), eq("#D1D100"));
        verify(graphics, times(1)).drawText(eq(new Position(63, 28)), anyString(), eq("#FFFFFF"));
    }
}

