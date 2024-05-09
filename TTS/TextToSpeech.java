package TTS;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Class TextToSpeech.
 *
 * A TTS voice that reads input Strings.
 */
public class TextToSpeech {

    Voice voice;

    /**
     * TextToSpeech Constructor
     *
     * Initializes a new TextToSpeech object
     */
    public TextToSpeech() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        this.voice = voiceManager.getVoice("kevin16");
        voice.allocate();
    }

    /**
     * Stops the TTS voice if it is currently reading.
     */
    public void stop(){
        voice.getAudioPlayer().cancel();
    }

    /**
     * The TTS voice will read the given input string to the user.
     * A new thread is created for each message the TTS voice should read.
     *
     * @param message the String to be read by the TTS voice.
     */
    public void read(String message) {
        Runnable speaker = new Runnable() {
            @Override
            public void run() {
                stop();
                voice.speak(message);
            }
        };
        (new Thread(speaker)).start();
    }
}
