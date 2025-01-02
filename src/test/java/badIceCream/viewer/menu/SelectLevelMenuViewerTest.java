package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.menu.SelectLevelMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class SelectLevelMenuViewerTest {

    private SelectLevelMenuViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        SelectLevelMenu menu = new SelectLevelMenu();
        graphics = Mockito.mock(Graphics.class);
        viewer = new SelectLevelMenuViewer(menu);
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
    public void testDrawElements() {
        assertAll(() -> {
            viewer.draw(graphics);

            verify(graphics, atLeast(66)).drawText(any(), anyString(), anyString());

            verify(graphics, times(1)).drawText(eq(new Position(45, 18)), anyString(), eq("#D1D100"));
            verify(graphics, times(1)).drawText(eq(new Position(57, 18)), anyString(), eq("#FFFFFF"));
        });
    }
}

