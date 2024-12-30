package badIceCream.model.game.elements;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import net.jqwik.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IceCreamTest {

    @Test
    void testIceCreamInitialization() {
        IceCream iceCream = new IceCream(0, 0);
        assertTrue(iceCream.getAlive());
        assertFalse(iceCream.isStrawberryActive());
        assertEquals(GUI.ACTION.DOWN, iceCream.getLastMovement());
    }

    @Test
    void testIceCreamType() {
        IceCream iceCream = new IceCream(0, 0);
        assertEquals(1, iceCream.getType());
    }

    @Property
    void testAliveToggle(@ForAll boolean initialState) {
        IceCream iceCream = new IceCream(0, 0);
        if (!initialState) {
            iceCream.changeAlive();
        }
        assertEquals(initialState, iceCream.getAlive());
        iceCream.changeAlive();
        assertNotEquals(initialState, iceCream.getAlive());
    }

    @Property
    void testStrawberryToggle(@ForAll boolean strawberryState) {
        IceCream iceCream = new IceCream(0, 0);
        iceCream.setStrawberry(strawberryState);
        assertEquals(strawberryState, iceCream.isStrawberryActive());
    }

    @Property
    void testSetLastMovement(@ForAll GUI.ACTION action) {
        IceCream iceCream = new IceCream(0, 0);
        iceCream.setLastMovement(action);
        assertEquals(action, iceCream.getLastMovement());
    }

    @Test
    void testSetPosition() {
        IceCream iceCream = new IceCream(0, 0);
        iceCream.setPosition(new Position(1, 1));
        assertEquals(new Position(1, 1), iceCream.getPosition());
    }
}
