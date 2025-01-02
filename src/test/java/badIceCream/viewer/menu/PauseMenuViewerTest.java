package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.PauseMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class PauseMenuViewerTest {

    private PauseMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        PauseMenu menu = new PauseMenu();
        graphics = Mockito.mock(Graphics.class);
        viewer = new PauseMenuViewer(menu);
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
    public void testDrawPauseSymbol() {
        viewer.drawPauseSymbol(graphics);
        verify(graphics, times(7)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawElements() {
        assertAll(() -> {
            viewer.draw(graphics);

            verify(graphics, atLeast(53)).drawText(any(), anyString(), anyString());

            verify(graphics, times(1)).drawText(eq(new Position(68, 21)), anyString(), eq("#D1D100"));
            verify(graphics, times(1)).drawText(eq(new Position(68, 24)), anyString(), eq("#FFFFFF"));
        });
    }
}

