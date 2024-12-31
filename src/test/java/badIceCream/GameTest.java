package badIceCream;

import badIceCream.audio.AudioController;
import badIceCream.states.State;
import badIceCream.utils.Type;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    @Property
    void testSetStates(@ForAll Type type) throws IOException, FontFormatException {
        try(MockedStatic<AudioController> audioController = mockStatic(AudioController.class)) {
            Game game = new Game();
            State mockState = mock(State.class);
            game.setState(mockState, type, 100, 100);
            assertEquals(mockState, game.getState());

            if (type != Type.nulo) {
                assertNotNull(game.getGui());
            }

            switch (type) {
                case menu:
                    audioController.verify(AudioController::playMenuMusic, times(2));
                    audioController.verify(AudioController::stopGameOverMusic, times(1));
                    audioController.verify(AudioController::stopLevelCompleteMusic, times(1));
                    audioController.verify(AudioController::stopLevelMusic, times(1));
                    break;
                case win:
                    audioController.verify(AudioController::playLevelCompleteMusic, times(1));
                    audioController.verify(AudioController::stopGameOverMusic, times(1));
                    audioController.verify(AudioController::stopMenuMusic, times(1));
                    audioController.verify(AudioController::stopLevelMusic, times(1));
                    break;
                case gameOver:
                    audioController.verify(AudioController::playGameOverMusic, times(1));
                    audioController.verify(AudioController::stopLevelMusic, times(1));
                    audioController.verify(AudioController::stopMenuMusic, times(1));
                    audioController.verify(AudioController::stopLevelCompleteMusic, times(1));
                    break;
                case game:
                    audioController.verify(AudioController::playLevelMusic, times(1));
                    audioController.verify(AudioController::stopMenuMusic, times(1));
                    audioController.verify(AudioController::stopLevelCompleteMusic, times(1));
                    audioController.verify(AudioController::stopGameOverMusic, times(1));
                default:

            }
        }
    }

    @Test
    void testGetNullGraphics() throws IOException, FontFormatException {
        try(MockedStatic<AudioController> audioController = mockStatic(AudioController.class)) {
            Game game = new Game();
            assertNull(game.getGraphicsForGame(Type.nulo, 100, 100));
        }
    }
}