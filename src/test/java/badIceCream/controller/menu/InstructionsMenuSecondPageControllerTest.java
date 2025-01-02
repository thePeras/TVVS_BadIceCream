package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuSecondPage;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.InstructionsMenuSecondPageState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class InstructionsMenuSecondPageControllerTest {
    @Property
    public void testStepActions(@ForAll GUI.ACTION action) {
        assertAll(() -> {
            InstructionsMenuSecondPage model = mock(InstructionsMenuSecondPage.class);
            Game game = mock(Game.class);
            when(game.getState()).thenReturn(new InstructionsMenuSecondPageState(model, 1));
            InstructionsMenuSecondPageController controller = new InstructionsMenuSecondPageController(model);

            controller.step(game, action, 0);

            switch (action) {
                case PAUSE:
                    verify(game).setState(any(MainMenuState.class), eq(Type.nulo), eq(0), eq(0));
                    break;
                case LEFT:
                    verify(game).setState(any(InstructionsMenuFirstPageState.class), eq(Type.nulo), eq(0), eq(0));
                    break;
                default:
                    verifyNoInteractions(game);
            }
        });
    }
}
