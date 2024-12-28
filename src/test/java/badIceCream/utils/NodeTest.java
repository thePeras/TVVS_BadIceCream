package badIceCream.utils;

import badIceCream.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;
class NodeTest {

    @Test
    void testNodeInitialization() {
        Position position = new Position(5, 10);

        Node parent = new Node(new Position(3, 7), 3, 4, null);
        Node node = new Node(position, 5, 10, parent);

        assertEquals(position, node.position);
        assertEquals(5, node.cost);
        assertEquals(10, node.heuristic);

        assertEquals(parent, node.parent);
    }

    @Property
    void testCompareTo(@ForAll("nodes") Node node1, @ForAll("nodes") Node node2) {
        int sum1 = node1.cost + node1.heuristic;
        int sum2 = node2.cost + node2.heuristic;

        int result = node1.compareTo(node2);

        if (sum1 < sum2) {
            assertTrue(result < 0);
        } else if (sum1 > sum2) {
            assertTrue(result > 0);
        } else {
            assertEquals(0, result);
        }
    }

    @Provide
    Arbitrary<Node> nodes() {
        return Arbitraries.integers().between(0, 100).flatMap(cost ->
                Arbitraries.integers().between(0, 100).flatMap(heuristic ->
                        Arbitraries.integers().between(0, 50).flatMap(x ->
                                Arbitraries.integers().between(0, 50).flatMap(y ->
                                        Arbitraries.just(new Node(new Position(x, y), cost, heuristic, null))
                                )
                        )
                )
        );
    }
}