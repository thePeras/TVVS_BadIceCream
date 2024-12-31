package badIceCream.model.game.arena;

import badIceCream.model.Position;
import badIceCream.model.game.elements.IceCream;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class LoaderArenaBuilderTest {

    @Test
    void testLoadArena() throws IOException {
        LoaderArenaBuilder loaderArenaBuilder = new LoaderArenaBuilder(1);
        Arena arena = loaderArenaBuilder.createArena();

        assertEquals(14, arena.getWidth());
        assertEquals(18, arena.getHeight());
        assertEquals(1, arena.getLevel());
        assertEquals(new Position(6, 16), arena.getIceCream().getPosition());
        assertEquals(4, arena.getMonsters().size());
        assertEquals(76, arena.getWalls().size());
        assertEquals(24, arena.getFruits().size());
        assertEquals(60, arena.getHotFloors().size());
    }

    @Test
    void testEmptyArena() throws IOException {
        BufferedReader mockBufferedReader = new BufferedReader(new StringReader(""));

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.newBufferedReader(any(), any()))
                    .thenReturn(mockBufferedReader);

            LoaderArenaBuilder builder = new LoaderArenaBuilder(1);
            Arena arena = builder.createArena();
            assertNull(arena.getIceCream());
        }

    }
}
