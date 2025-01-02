package badIceCream.audio;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.sound.sampled.*;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class AudioTest {

    @Test
    public void testLoadMusic_success(){
        assertAll(() -> {
            String soundFile = "LevelMusic.wav";
            Clip result = Audio.loadMusic(soundFile);
            assertNotNull(result);

            float defaultVolume = -20.0f;
            FloatControl volumeController = (FloatControl) result.getControl(FloatControl.Type.MASTER_GAIN);
            assertEquals(defaultVolume, volumeController.getValue());
        });
    }

    @Property
    public void testPlay_once(@ForAll boolean soundNotNull) {
        Clip mockClip = Mockito.mock(Clip.class);
        Audio audio = soundNotNull ? new Audio(mockClip) : new Audio(null);
        audio.playOnce();
        int numberOfTimes = soundNotNull ? 1 : 0;
        verify(mockClip, times(numberOfTimes)).setMicrosecondPosition(0);
        verify(mockClip, times(numberOfTimes)).start();
    }

    @Property
    public void testPlay_continuously(@ForAll boolean soundNotNull) {
        Clip mockClip = Mockito.mock(Clip.class);
        Audio audio = soundNotNull ? new Audio(mockClip) : new Audio(null);
        audio.play();
        final int numberOfTimes = soundNotNull ? 1 : 0;
        verify(mockClip, times(numberOfTimes)).setMicrosecondPosition(0);
        verify(mockClip, times(numberOfTimes)).start();
        verify(mockClip, times(numberOfTimes)).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Property
    public void testStop(@ForAll boolean soundNotNull) {
        Clip mockClip = Mockito.mock(Clip.class);
        Audio audio = soundNotNull ? new Audio(mockClip) : new Audio(null);
        audio.stop();
        int numberOfTimes = soundNotNull ? 1 : 0;
        verify(mockClip, times(numberOfTimes)).stop();
    }
}

