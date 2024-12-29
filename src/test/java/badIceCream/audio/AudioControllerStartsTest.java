package badIceCream.audio;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.AfterProperty;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import javax.sound.sampled.*;

import static org.mockito.Mockito.*;

public class AudioControllerStartsTest {
    private MockedStatic<Audio> audioMockedStatic;
    private Clip mockClip;

    @BeforeProperty
    public void setUp() {
        mockClip = mock(Clip.class);
        audioMockedStatic = Mockito.mockStatic(Audio.class);
        audioMockedStatic.when(() -> Audio.loadMusic(anyString())).thenReturn(mockClip);
    }

    @AfterProperty
    public void tearDown() {
        reset(mockClip);
        audioMockedStatic.close();
    }

    @Provide
    public Arbitrary<StartStopPair> startStopMethods() {
        return Arbitraries.of(
                new StartStopPair(AudioController::playLevelMusic, AudioController::stopLevelMusic),
                new StartStopPair(AudioController::playGameOverMusic, AudioController::stopGameOverMusic),
                new StartStopPair(AudioController::playMenuMusic, AudioController::stopMenuMusic),
                new StartStopPair(AudioController::playLevelCompleteMusic, AudioController::stopLevelCompleteMusic),
                new StartStopPair(AudioController::playBuildWallSound, null),
                new StartStopPair(AudioController::playBreakWallSound, null),
                new StartStopPair(AudioController::playRunnerMonsterSound, null)
        );
    }


    @Provide
    public Arbitrary<Runnable> methods() {
        return Arbitraries.of(
                AudioController::playLevelMusic,
                AudioController::playMenuMusic,
                AudioController::playLevelCompleteMusic,
                AudioController::playGameOverMusic,
                AudioController::playBuildWallSound,
                AudioController::playBreakWallSound,
                AudioController::playRunnerMonsterSound
        );
    }

    @Property
    public void testPlayMethods(@ForAll("startStopMethods") StartStopPair pair) {
        Runnable startMethod = pair.startMethod();
        Runnable stopMethod = pair.stopMethod();

        if(stopMethod != null) {
            stopMethod.run();
            verify(mockClip, never()).stop();
        }
        startMethod.run();
        verify(mockClip, times(1)).start();
        startMethod.run();
        verify(mockClip, times(2)).start();
        if(stopMethod != null) {
            stopMethod.run();
            verify(mockClip, times(1)).stop();
        }
        reset(mockClip);
    }

    public record StartStopPair(Runnable startMethod, Runnable stopMethod) {
    }
}
