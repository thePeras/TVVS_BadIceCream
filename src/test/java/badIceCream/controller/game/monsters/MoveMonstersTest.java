package badIceCream.controller.game.monsters;

import badIceCream.controller.game.StepMonsters;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.*;
import static org.mockito.Mockito.*;

public class MoveMonstersTest {
    @Provide
    public Arbitrary<StepMonsters> movementStrategies() {
        return Arbitraries.of(new DefaultMovement(), new RunnerMovementDisabled(), new RunnerMovementEnabled(), new JumperMovement(), new WallBreakerMovement());
    }

    @Provide
    Arbitrary<Position> positions() {
        return Arbitraries.integers().between(0, 2).tuple2().map(t -> new Position(t.get1(), t.get2()));
    }

    @Property
    void testMoveMonsters(@ForAll("positions") Position position,
                          @ForAll("movementStrategies") StepMonsters movementStrategy,
                          @ForAll boolean isStrawberryActive) {
        Monster monster = mock(Monster.class);
        IceCream iceCream = mock(IceCream.class);
        when(iceCream.getPosition()).thenReturn(new Position(1, 1));
        when(iceCream.isStrawberryActive()).thenReturn(isStrawberryActive);

        Arena arena = new Arena(2, 2);
        arena.setIceCream(iceCream);

        movementStrategy.moveMonster(monster, position, arena);
        verify(monster).setPosition(position);

        if (position.equals(arena.getIceCream().getPosition()) && !isStrawberryActive) {
            verify(arena.getIceCream()).changeAlive();
        } else {
            verify(arena.getIceCream(), never()).changeAlive();
        }
    }
}
