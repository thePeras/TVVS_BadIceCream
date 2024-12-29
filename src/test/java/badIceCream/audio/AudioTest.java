package badIceCream.audio;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.sound.sampled.*;
import java.io.FileNotFoundException;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class AudioTest {

    @Test
    public void testLoadMusic_success() throws Exception {
        String soundFile = "LevelMusic.wav";
        Clip result = Audio.loadMusic(soundFile);
        assertNotNull(result);

        FloatControl volumeController = (FloatControl) result.getControl(FloatControl.Type.MASTER_GAIN);
        assertEquals(-20.0f, volumeController.getValue());
    }

    @Test
    public void testLoadMusic_fileNotFound(){
        String soundFile = "non_existent_sound.wav";
        assertThrows(FileNotFoundException.class, () -> {
            Audio.loadMusic(soundFile);
        });
    }

    @Property
    public void testPlay_once(@ForAll boolean soundNotNull) {
        Clip mockClip = Mockito.mock(Clip.class);
        Audio audio = soundNotNull ? new Audio(mockClip) : new Audio(null);
        audio.playOnce();

        if(soundNotNull){
            verify(mockClip, times(1)).setMicrosecondPosition(0);
            verify(mockClip, times(1)).start();
        }else{
            verify(mockClip, never()).setMicrosecondPosition(0);
            verify(mockClip, never()).start();
        }
    }

    @Property
    public void testPlay_continuously(@ForAll boolean soundNotNull) {
        Clip mockClip = Mockito.mock(Clip.class);
        Audio audio = soundNotNull ? new Audio(mockClip) : new Audio(null);
        audio.play();

        if(soundNotNull){
            verify(mockClip, times(1)).setMicrosecondPosition(0);
            verify(mockClip, times(1)).start();
            verify(mockClip, times(1)).loop(Clip.LOOP_CONTINUOUSLY);
        }else{
            verify(mockClip, never()).setMicrosecondPosition(0);
            verify(mockClip, never()).start();
            verify(mockClip, never()).loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Property
    public void testStop(@ForAll boolean soundNotNull) {
        Clip mockClip = Mockito.mock(Clip.class);
        Audio audio = soundNotNull ? new Audio(mockClip) : new Audio(null);
        audio.stop();
        if(soundNotNull){
            verify(mockClip, times(1)).stop();
        }else{
            verify(mockClip, never()).stop();
        }
    }
}

