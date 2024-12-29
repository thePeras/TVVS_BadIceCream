package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.fruits.Fruit;
import net.jqwik.api.*;

import static org.mockito.Mockito.*;

public class IceCreamViewerTest {

    @Property
    void testStepDrawMonster(@ForAll("types") int type) {
        IceCreamViewer iceCreamViewer = new IceCreamViewer();
        IceCream iceCream = mock(IceCream.class);

        Graphics graphics = mock(Graphics.class);
        iceCreamViewer.draw(iceCream, graphics, type);

        verify(graphics, times(1)).drawIceCream(iceCream.getPosition(), iceCream.getLastMovement(), iceCream.isStrawberryActive());
    }

    @Provide
    public Arbitrary<Integer> types() {
        return Arbitraries.integers().between(1, 3);
    }
}
