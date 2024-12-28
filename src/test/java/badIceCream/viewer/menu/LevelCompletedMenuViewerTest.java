package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.LevelCompletedMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class LevelCompletedMenuViewerTest {

    private LevelCompletedMenuViewer viewer;
    private LevelCompletedMenu menu;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        menu = new LevelCompletedMenu();
        graphics = Mockito.mock(Graphics.class);
        viewer = new LevelCompletedMenuViewer(menu);
    }

    @Test
    public void testDrawTittle() {
        viewer.drawTitle(graphics);
        verify(graphics, times(8)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawSnowflake() {
        viewer.drawSnowflake(graphics);
        verify(graphics, times(40)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawElements() throws Exception {
        viewer.draw(graphics);

        verify(graphics, atLeast(48)).drawText(any(), anyString(), anyString());

        verify(graphics, times(1)).drawText(eq(new Position(65, 17)), anyString(), eq("#D1D100"));
        verify(graphics, times(1)).drawText(eq(new Position(65, 21)), anyString(), eq("#FFFFFF"));
    }
}

