package badIceCream.viewer.menu;

import badIceCream.GUI.Graphics;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class InstructionsMenuSecondPageViewerTest {

    private InstructionsMenuSecondPageViewer viewer;
    private Graphics graphics;

    @BeforeEach
    public void setUp() {
        InstructionsMenuSecondPage menu = Mockito.mock(InstructionsMenuSecondPage.class);
        graphics = Mockito.mock(Graphics.class);
        viewer = new InstructionsMenuSecondPageViewer(menu);
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
            verify(graphics, times(1)).drawCharacters();
            verify(graphics, times(44)).drawText(any(), anyString(), anyString());
        });
    }
}

