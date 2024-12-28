package badIceCream.GUI;

import static org.mockito.Mockito.*;

import badIceCream.model.Position;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class GraphicsTest {

    @Provide
    Arbitrary<Position> validPositions() {
        return Arbitraries.integers().between(0, 100) // Assuming screen size limits
                .flatMap(x -> Arbitraries.integers().between(0, 100)
                        .map(y -> new Position(x, y)));
    }

    @Provide
    Arbitrary<Boolean> booleans() {
        return Arbitraries.of(true, false);
    }

    @Provide
    Arbitrary<GUI.ACTION> actions() {
        return Arbitraries.of(GUI.ACTION.values());
    }

    @Provide
    Arbitrary<Integer> iceWallTypes() {
        return Arbitraries.integers().between(1, 11).filter(type -> type != 2);
    }

    @Provide
    Arbitrary<Integer> hotFloorTypes() {
        return Arbitraries.integers().between(1, 30);
    }

    GUI gui;
    Terminal terminal;
    Screen screen;
    TextGraphics textGraphics;

    private Graphics createGraphics() throws IOException {
        gui = mock(GUI.class);
        terminal = mock(Terminal.class);
        screen = mock(Screen.class);
        textGraphics = mock(TextGraphics.class);

        when(gui.createTerminal()).thenReturn(terminal);
        when(gui.createScreen(terminal)).thenReturn(screen);
        when(screen.newTextGraphics()).thenReturn(textGraphics);

        Graphics graphics = new Graphics(gui);
        graphics.setGui(gui);
        return graphics;
    }

    void verifyDrawCharacter(Position position, String color) {
        verify(textGraphics, times(1)).setForegroundColor(eq(TextColor.Factory.fromString(color)));
        verify(textGraphics, times(1)).putString(eq(position.getX()), eq(position.getY()), anyString());
    }

    void verifyDrawCharacter(String color, int times) {
        verify(textGraphics, times(times)).setForegroundColor(eq(TextColor.Factory.fromString(color)));
        verify(textGraphics, times(times)).putString(anyInt(), anyInt(), anyString());
    }
    void verifyDrawCharacter(int times) {
        verify(textGraphics, times(times)).setForegroundColor(any());
        verify(textGraphics, times(times)).putString(anyInt(), anyInt(), anyString());
    }
    void verifyDrawCharacter(Position position, char c, String color) {
        verify(textGraphics, atLeast(1)).setForegroundColor(eq(TextColor.Factory.fromString(color)));
        verify(textGraphics, atLeast(1)).putString(eq(position.getX()), eq(position.getY()), eq("" + c));
    }

    @Property
    void testDrawIceCream(@ForAll("validPositions") Position position,
                          @ForAll("actions") GUI.ACTION action,
                          @ForAll boolean strawberry) throws IOException {
        Graphics graphics = createGraphics();
        graphics.drawIceCream(position, action, strawberry);

        verifyDrawCharacter(position, strawberry ? "#48DEFF" : "#FFFFFF");
    }

    @Property
    void testDrawIceWall(@ForAll("validPositions") Position position,
                         @ForAll("iceWallTypes") int type) throws IOException {
        Graphics graphics = createGraphics();
        graphics.drawIceWall(position, type);

        verifyDrawCharacter(position, "#87CEFA");
    }

    @Property
    void testDrawHotFloor(@ForAll("validPositions") Position position,
                          @ForAll("hotFloorTypes") int type) throws IOException {
        Graphics graphics = createGraphics();
        graphics.drawHotFloor(position, type);


        verifyDrawCharacter(position, "#e14750");
    }

    @Property
    void testDrawDefaultMonster(@ForAll("validPositions") Position position,
                                @ForAll("actions") GUI.ACTION action) throws IOException {
        Graphics graphics = createGraphics();
        graphics.drawDefaultMonster(position, action);

        verifyDrawCharacter(position, "#00FF00");
    }

    @Test
    void testGetNextAction() throws IOException {
        Graphics graphics = createGraphics();
        Screen screen = graphics.getGui().createScreen(graphics.getGui().createTerminal());

        KeyStroke[] keyStrokes = {
                new KeyStroke(KeyType.ArrowDown, false, false),
                new KeyStroke(KeyType.ArrowUp, false, false),
                new KeyStroke(KeyType.ArrowRight, false, false),
                new KeyStroke(KeyType.ArrowLeft, false, false),
                new KeyStroke(KeyType.Enter, false, false),
                new KeyStroke(KeyType.Escape, false, false),
                new KeyStroke(' ', false, false),
                null,
                new KeyStroke(KeyType.Backspace, false, false),
        };

        GUI.ACTION[] expectedActions = {
                GUI.ACTION.DOWN,
                GUI.ACTION.UP,
                GUI.ACTION.RIGHT,
                GUI.ACTION.LEFT,
                GUI.ACTION.SELECT,
                GUI.ACTION.PAUSE,
                GUI.ACTION.SPACE,
                GUI.ACTION.NONE,
                GUI.ACTION.NONE,
        };

        for (int i = 0; i < keyStrokes.length; i++) {
            when(screen.pollInput()).thenReturn(keyStrokes[i]);
            GUI.ACTION action = graphics.getNextAction();
            assertEquals(expectedActions[i], action);
        }
    }

    @Test
    void testDrawCharacters() throws IOException {
        Graphics graphics = createGraphics();
        graphics.drawCharacters();
        verifyDrawCharacter("#00FF00", 6);
    }

    @Test
    void testDrawFruits() throws IOException {
        Graphics graphics = createGraphics();
        Position position = new Position(33, 15);
        graphics.drawAppleFruit(position);
        graphics.drawBananaFruit(position);
        graphics.drawPineappleFruit(position);
        graphics.drawCherryFruit(position);
        graphics.drawStrawberryFruit(position);
        verifyDrawCharacter(5);
    }

    @Property
    void testDrawMonster(@ForAll("actions") GUI.ACTION action) throws IOException {
        Graphics graphics = createGraphics();
        Position position = new Position(33, 15);
        graphics.drawJumperMonster(position, action);
        graphics.drawWallBreakerMonster(position, action);
        verifyDrawCharacter(2);
    }

    @Property
    void testDrawRunnerMonster(@ForAll boolean isRunner) throws IOException {
        Graphics graphics = createGraphics();
        Position position = new Position(33, 15);
        graphics.drawRunnerMonster(position, GUI.ACTION.UP, isRunner);
        String color = isRunner ? "#FF0000" : "#FFFF66";
        char c = isRunner ? '3' : '1';
        verifyDrawCharacter(position, c, color);
        graphics.drawRunnerMonster(position, GUI.ACTION.LEFT, isRunner);
        c = isRunner ? 'X' : 'W';
        verifyDrawCharacter(position, c, color);
        graphics.drawRunnerMonster(position, GUI.ACTION.RIGHT, isRunner);
        c = isRunner ? '}' : '2';
        verifyDrawCharacter(position, c, color);
        graphics.drawRunnerMonster(position, GUI.ACTION.NONE, isRunner);
        c = isRunner ? '|' : 'V';
        verifyDrawCharacter(position, c, color);
    }

    @Test
    void testDawStoneWall() throws IOException {
        Graphics graphics = createGraphics();
        Position position = new Position(33, 15);
        graphics.drawStoneWall(position);
        verifyDrawCharacter(position, 'G', "#696969");
    }

    @Test
    void testClear() throws IOException {
        Graphics graphics = createGraphics();
        graphics.clear();
        verify(screen, times(1)).clear();
    }
    @Test
    void testRefresh() throws IOException {
        Graphics graphics = createGraphics();
        graphics.refresh();
        verify(screen, times(1)).refresh();
    }
    @Test
    void testClose() throws IOException {
        Graphics graphics = createGraphics();
        graphics.close();
        verify(screen, times(1)).close();
    }

    @Test
    void testDrawText() throws IOException {
        Graphics graphics = createGraphics();
        Position position = new Position(33, 15);
        graphics.drawText(position, "Hello", "#FF0000");
        verify(textGraphics, times(1)).setForegroundColor(eq(TextColor.Factory.fromString("#FF0000")));
        verify(textGraphics, times(1)).putString(eq(position.getX()), eq(position.getY()), eq("Hello"));
    }

}
