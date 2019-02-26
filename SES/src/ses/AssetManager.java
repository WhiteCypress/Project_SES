package ses;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;

public class AssetManager {

    static private Background trainTrackIMG = null;

    static private Image engine = null;
    static private Image enginePop = null;
    static private Image train = null;

    static private AudioClip engineSound = null;
    static private AudioClip trainWhistleSound = null;

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets() {
        // Preload all images
        try {
            Image trainTrack = new Image(fileURL("./assets/images/trainTrack.png"));
            trainTrackIMG = new Background(
                    new BackgroundImage(trainTrack,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            BackgroundSize.DEFAULT));

            engine = new Image(fileURL("./assets/images/engine.png"));
            enginePop = new Image(fileURL("./assets/images/enginePop.png"));
            train = new Image(fileURL("./assets/images/train.png"));

        } catch (Exception e) {
            System.out.println("Error with graphics.");
        }
        try {
            // Preload all music tracks
            //backgroundMusic = new Media(fileURL("./assets/music/backgroundMusic.mp3"));

            // Preload all sound effects
            engineSound = new AudioClip(fileURL("./assets/soundfx/victorySound.wav"));
            trainWhistleSound = new AudioClip(fileURL("./assets/soundfx/gameOverSound.wav"));
            //shootingSound.setVolume(0.1);
    
        } catch (Exception e) {
            System.out.println("Error with sound effects.");
        }
    }

    static public Background getTrainTrack() {
        return trainTrackIMG;
    }

    public static Image getEngine() {
        return engine;
    }    
    
    public static Image getEnginePop() {
        return enginePop;
    }    
    
    public static Image getTrain() {
        return train;
    }

    public static AudioClip getEngineSound() {
        return engineSound;
    }

    public static AudioClip getTrainWhistleSound() {
        return trainWhistleSound;
    }

}