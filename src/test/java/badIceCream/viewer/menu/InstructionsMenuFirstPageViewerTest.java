package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class InstructionsMenuFirstPageViewerTest {

    private InstructionsMenuFirstPageViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        InstructionsMenuFirstPage menu = Mockito.mock(InstructionsMenuFirstPage.class);
        graphics = Mockito.mock(Graphics.class);
        viewer = new InstructionsMenuFirstPageViewer(menu);
    }

    @Test
    public void testDrawTittle() {
        viewer.drawTitle(graphics);
        verify(graphics, times(6)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawSnowflake() {
        viewer.drawSnowflake(graphics);
        verify(graphics, times(24)).drawText(any(), anyString(), anyString());
    }

    @Test
    public void testDrawElements() {
        assertAll(() -> {
            viewer.draw(graphics);
            verify(graphics, times(54)).drawText(any(), anyString(), anyString());
        });
    }
}

