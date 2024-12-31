package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.GameOverMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GameOverMenuViewerTest {

    private GameOverMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        GameOverMenu menu = new GameOverMenu();
        graphics = Mockito.mock(Graphics.class);
        viewer = new GameOverMenuViewer(menu);
    }

    @Test
    public void testDrawTittle() {
        viewer.drawTitle(graphics);
        verify(graphics, times(6)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawSnowflake() {
        viewer.drawSnowflake(graphics);
        verify(graphics, times(40)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawElements() throws Exception {
        viewer.draw(graphics);

        verify(graphics, atLeast(46)).drawText(any(), anyString(), anyString());

        verify(graphics, times(1)).drawText(eq(new Position(65, 17)), anyString(), eq("#D1D100"));
        verify(graphics, times(1)).drawText(eq(new Position(65, 21)), anyString(), eq("#FFFFFF"));
    }
}

