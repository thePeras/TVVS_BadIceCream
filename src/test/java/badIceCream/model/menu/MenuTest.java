package badIceCream.model.menu;

import net.jqwik.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new Menu(Arrays.asList("Option 0", "Option 1", "Option 2"));
    }

    @Test
    public void testIsSelected() {
        assertTrue(menu.isSelected(0));
    }

    @Test
    public void testNextEntryWrapsAround() {
        menu.nextEntry();
        int expected = 1;
        assertEquals(expected, menu.currentEntry);
    }

    @Test
    public void testPreviousEntryWrapsAround() {
        menu.previousEntry();
        int expected = 2;
        assertEquals(expected, menu.currentEntry);
    }

    @Property
    public void testGetEntry(@ForAll("entries") int entryNumber) {
        menu = new Menu(Arrays.asList("Option 0", "Option 1", "Option 2"));
        String expected = "Option " + entryNumber;
        assertEquals(expected, menu.getEntry(entryNumber));
    }

    @Provide
    public Arbitrary<Integer> entries() {
        return Arbitraries.integers().between(0, 2);
    }

    @Test
    public void testGetNumberEntries() {
        Integer expectedNumberOfOptions = 3;
        assertEquals(expectedNumberOfOptions, menu.getNumberEntries());
    }
}
