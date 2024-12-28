package badIceCream.model;

import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Property
    void testConstructorAndAccessors(@ForAll("validCoordinates") int[] coordinates) {
        Position position = new Position(coordinates[0], coordinates[1]);
        assertThat(position.getX()).isEqualTo(coordinates[0]);
        assertThat(position.getY()).isEqualTo(coordinates[1]);
    }

    @Property
    void testMutators(@ForAll("validCoordinates") int[] coordinates) {
        Position position = new Position(0, 0);
        position.setX(coordinates[0]);
        position.setY(coordinates[1]);
        assertThat(position.getX()).isEqualTo(coordinates[0]);
        assertThat(position.getY()).isEqualTo(coordinates[1]);
    }

    @Property
    void testDirectionalMethods(@ForAll("validCoordinates") int[] coordinates) {
        Position position = new Position(coordinates[0], coordinates[1]);
        assertThat(position.getLeft()).isEqualTo(new Position(coordinates[0] - 1, coordinates[1]));
        assertThat(position.getRight()).isEqualTo(new Position(coordinates[0] + 1, coordinates[1]));
        assertThat(position.getUp()).isEqualTo(new Position(coordinates[0], coordinates[1] - 1));
        assertThat(position.getDown()).isEqualTo(new Position(coordinates[0], coordinates[1] + 1));
    }

    @Property
    void testEqualityAndHashing(@ForAll("validCoordinates") int[] coordinates) {
        Position position1 = new Position(coordinates[0], coordinates[1]);
        Position position2 = new Position(coordinates[0], coordinates[1]);
        assertThat(position1).isEqualTo(position2);
        assertThat(position1.hashCode()).isEqualTo(position2.hashCode());
    }

    @Test
    void testEqualsSelf() {
        Position position = new Position(5, 10);
        Position otherPosition = new Position(5, 11);
        assertThat(position.equals(position)).isTrue();
        assertThat(position.equals(otherPosition)).isFalse();
    }

    @Test
    void testEqualsWithDifferentType() {
        Position position = new Position(5, 10);
        String otherObject = "NotAPosition";
        assertThat(position.equals(otherObject)).isFalse();
    }

    @Provide
    Arbitrary<int[]> validCoordinates() {
        return Arbitraries.integers().between(-1000, 1000).array(int[].class).ofSize(2);
    }
}

