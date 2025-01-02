package badIceCream.controller.game;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.Position;
import badIceCream.model.game.arena.Arena;
import badIceCream.model.game.elements.IceCream;
import net.jqwik.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;

class IceCreamControllerTest {

    @Provide
    Arbitrary<GUI.ACTION> iceCreamActions(){
        return Arbitraries.of(List.of(GUI.ACTION.UP, GUI.ACTION.DOWN, GUI.ACTION.LEFT, GUI.ACTION.RIGHT, GUI.ACTION.SPACE));
    }
    @Property
    void testIceCreamMovements(@ForAll("iceCreamActions") GUI.ACTION action){
        Arena arena = mock(Arena.class);
        Game game = mock(Game.class);
        IceCream iceCream = mock(IceCream.class);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.getPosition()).thenReturn(new Position(1, 1));
        IceCreamController iceCreamController = new IceCreamController(arena);

        iceCreamController.step(game, action, 0);

        if(action == GUI.ACTION.SPACE) {
            verify(iceCream, never()).setLastMovement(any());
            verify(arena, times(1)).powerIceCream(any());
        }else{
            verify(iceCream).setLastMovement(action);
        }
    }



    @Test
    public void testEatFruit(){
        Arena arena = mock(Arena.class);
        IceCream iceCream = mock(IceCream.class);
        when(arena.getIceCream()).thenReturn(iceCream);
        when(iceCream.getPosition()).thenReturn(new Position(1, 1));
        when(arena.eatFruit(any())).thenReturn(1);
        IceCreamController iceCreamController = new IceCreamController(arena);

        int fruits = iceCreamController.eatFruit();

        verify(arena, times(1)).eatFruit(any());
        int expectedNumberOfFruits = 1;
        assertEquals(fruits, expectedNumberOfFruits);
    }
}

