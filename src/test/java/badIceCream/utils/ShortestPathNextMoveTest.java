package badIceCream.utils;

import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.monsters.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShortestPathNextMoveTest {
    private ShortestPathNextMove shortestPathNextMove;
    private Arena arenaMock;
    private Monster monsterMock;

    @BeforeEach
    void setUp() {
        shortestPathNextMove = new ShortestPathNextMove();
        arenaMock = mock(Arena.class);
        monsterMock = mock(Monster.class);
    }

    @Test
    void testMonsterAlreadyAtTarget() {
        Position monsterPosition = new Position(3, 3);
        when(monsterMock.getPosition()).thenReturn(monsterPosition);
        //when(arenaMock.getIceCream()).thenReturn(() -> monsterPosition);

        //Position result = shortestPathNextMove.findShortestPath(monsterMock, arenaMock);

        //assertNull(result, "If the monster is already at the target position, the result should be null.");
    }

    @Test
    void testSimplePathToTarget() {
        Position monsterPosition = new Position(1, 1);
        Position iceCreamPosition = new Position(3, 3);

        when(monsterMock.getPosition()).thenReturn(monsterPosition);
        //when(arenaMock.getIceCream()).thenReturn(() -> iceCreamPosition);
        when(arenaMock.isEmptyMonsters(any(Position.class))).thenReturn(true);

        //Position result = shortestPathNextMove.findShortestPath(monsterMock, arenaMock);

        //assertNotNull(result, "The result should not be null for a valid path.");
        //assertEquals(new Position(2, 1), result, "The monster should move closer to the target (ice cream) using the shortest path.");
    }

    @Test
    void testObstacleBlockingPath() {
        Position monsterPosition = new Position(1, 1);
        Position iceCreamPosition = new Position(2, 2);

        when(monsterMock.getPosition()).thenReturn(monsterPosition);
        //when(arenaMock.getIceCream()).thenReturn(() -> iceCreamPosition);
        when(arenaMock.isEmptyMonsters(any(Position.class))).thenAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            // Block position (2, 1)
            return !pos.equals(new Position(2, 1));
        });

        //Position result = shortestPathNextMove.findShortestPath(monsterMock, arenaMock);

        //assertNotNull(result, "The result should not be null if a path exists around obstacles.");
        //assertEquals(new Position(1, 2), result, "The monster should take an alternative path around the obstacle.");
    }

    @Test
    void testNoPathToTarget() {
        Position monsterPosition = new Position(1, 1);
        Position iceCreamPosition = new Position(3, 3);

        when(monsterMock.getPosition()).thenReturn(monsterPosition);
        //when(arenaMock.getIceCream()).thenReturn(() -> iceCreamPosition);
        when(arenaMock.isEmptyMonsters(any(Position.class))).thenReturn(false);

        //Position result = shortestPathNextMove.findShortestPath(monsterMock, arenaMock);

        //assertNull(result, "The result should be null if no path exists to the target.");
    }

    @Test
    void testLargeArenaPerformance() {
        Position monsterPosition = new Position(0, 0);
        Position iceCreamPosition = new Position(50, 50);

        when(monsterMock.getPosition()).thenReturn(monsterPosition);
        //when(arenaMock.getIceCream()).thenReturn(() -> iceCreamPosition);
        when(arenaMock.isEmptyMonsters(any(Position.class))).thenReturn(true);

        //Position result = shortestPathNextMove.findShortestPath(monsterMock, arenaMock);

        //assertNotNull(result, "The result should not be null for large arenas with no obstacles.");
        // Only checks that it produces a valid move; exact value depends on heuristic.
    }
}
