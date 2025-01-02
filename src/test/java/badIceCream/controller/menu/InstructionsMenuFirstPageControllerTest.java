package badIceCream.controller.menu;

import badIceCream.GUI.GUI;
import badIceCream.Game;
import badIceCream.model.menu.InstructionsMenuFirstPage;
import badIceCream.states.InstructionsMenuFirstPageState;
import badIceCream.states.InstructionsMenuSecondPageState;
import badIceCream.states.MainMenuState;
import badIceCream.utils.Type;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class InstructionsMenuFirstPageControllerTest {
    @Property
    public void testStepActions(@ForAll GUI.ACTION action) {
        assertAll(() -> {
            InstructionsMenuFirstPage model = mock(InstructionsMenuFirstPage.class);
            Game game = mock(Game.class);
            when(game.getState()).thenReturn(new InstructionsMenuFirstPageState(model, 1));
            InstructionsMenuFirstPageController controller = new InstructionsMenuFirstPageController(model);

            controller.step(game, action, 0);

            switch (action) {
                case PAUSE:
                    verify(game).setState(any(MainMenuState.class), eq(Type.nulo), eq(0), eq(0));
                    break;
                case RIGHT:
                    verify(game).setState(any(InstructionsMenuSecondPageState.class), eq(Type.nulo), eq(0), eq(0));
                    break;
                default:
                    verifyNoInteractions(game);
            }
        });
    }
}
