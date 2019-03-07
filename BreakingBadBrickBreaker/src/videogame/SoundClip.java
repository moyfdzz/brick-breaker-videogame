
package videogame;

/**
 *
 * @author moisesfernandez
 */
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.net.URL;

public class SoundClip {

    private AudioInputStream sample;
    private Clip clip;
    private boolean looping = false;
    private int repeat = 0;
    private String filename = "";

    /**
     * Constructor to initialize an object of the type SoundClip
     */
    public SoundClip() {
        try {
            //crea el Buffer de sonido
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) { 
        }
    }
    
    /**
     * Constructor to initialize an object of the type PowerUp with an attribute
     * @param filename
     */
    public SoundClip(String filename) {
        this();
        load(filename);
    }
    
    /**
     * To get the clip
     * @return clip
     */
    public Clip getClip() { 
        return clip; 
    }
    
    /**
     * To set the status of the looping
     * @param looping
     */
    public void setLooping(boolean looping) {
        this.looping = looping; 
    }

    /**
     * To get the status of the looping
     * @return looping
     */
    public boolean getLooping() {
        return looping;
    }

    /**
     * To set the status of the repeat
     * @param repeat
     */
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    /**
     * To get the status of the repeating
     * @return repeat
     */
    public int getRepeat() { 
        return repeat; 
    }

    /**
     * To set the name of the file
     * @param filename
     */
    public void setFilename(String filename) { 
        this.filename = filename; 
    }

    /**
     * To get the name of the file
     * @return filename
     */
    public String getFilename() { 
        return filename;
    }

    /**
     * To get the status of whether the soundclip is loaded or not
     * @return boolean
     */
    public boolean isLoaded() {
        return (boolean)(sample != null);
    }

    private URL getURL(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        }
        catch (Exception e) { 
            System.out.println("" + filename + "does not exist");
        }
        return url;
    }

    public boolean load(String audiofile) {
        try {
            setFilename(audiofile);
            sample = AudioSystem.getAudioInputStream(getURL(filename));
            clip.open(sample);
            return true;

        } catch (IOException e) {
            return false;
        } catch (UnsupportedAudioFileException e) {
            return false;
        } catch (LineUnavailableException e) {
            return false;
        }
    }

    public void play() {
        if (!isLoaded()) 
            return;
        clip.setFramePosition(0);

        if (looping)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            clip.loop(repeat);
    }

    public void stop() {
        clip.stop();
    }

}

