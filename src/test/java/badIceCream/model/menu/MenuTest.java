package badIceCream.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new Menu(Arrays.asList("Option 1", "Option 2", "Option 3"));
    }

    @Test
    public void testNextEntryWrapsAround() {
        menu.nextEntry();
        assertTrue(menu.isSelected(1));
        menu.nextEntry();
        assertTrue(menu.isSelected(2));
        menu.nextEntry();
        assertTrue(menu.isSelected(0));
    }

    @Test
    public void testPreviousEntryWrapsAround() {
        menu.previousEntry();
        assertTrue(menu.isSelected(2));
        menu.previousEntry();
        assertTrue(menu.isSelected(1));
        menu.previousEntry();
        assertTrue(menu.isSelected(0));
    }

    @Test
    public void testGetEntry() {
        assertEquals("Option 1", menu.getEntry(0));
        assertEquals("Option 2", menu.getEntry(1));
        assertEquals("Option 3", menu.getEntry(2));
    }

    @Test
    public void testIsSelected() {
        assertTrue(menu.isSelected(0));
        assertFalse(menu.isSelected(1));
        assertFalse(menu.isSelected(2));
    }

    @Test
    public void testGetNumberEntries() {
        assertEquals(3, menu.getNumberEntries());
    }
}
