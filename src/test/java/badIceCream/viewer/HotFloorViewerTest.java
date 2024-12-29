package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.HotFloor;
import badIceCream.model.game.elements.IceCream;
import net.jqwik.api.*;

import static org.mockito.Mockito.*;

public class HotFloorViewerTest {

    @Property
    void testStepDrawMonster(@ForAll("types") int type) {
        HotFloorViewer hotFloorViewer = new HotFloorViewer();
        HotFloor hotFloor = mock(HotFloor.class);

        Graphics graphics = mock(Graphics.class);
        hotFloorViewer.draw(hotFloor, graphics, type);

        verify(graphics, times(1)).drawHotFloor(hotFloor.getPosition(), type);
    }

    @Provide
    public Arbitrary<Integer> types() {
        return Arbitraries.integers().between(1, 3);
    }
}
