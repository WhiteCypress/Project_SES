package ses;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;

public class AssetManager {

    static private ImagePattern landscape = null;
    static private Background landscapeBackground = null;

    static private Image engine = null;
    static private Image enginePop = null;
    static private ImagePattern train = null;

    static private AudioClip engineSound = null;
    static private AudioClip trainWhistleSound = null;

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets() {
        // Preload all images
        try {
            landscape = new ImagePattern(new Image(fileURL("./assets/images/landscape.png")));

            Image landscapeIMG = new Image(fileURL("./assets/images/landscape.png"));
            landscapeBackground = new Background(
                    new BackgroundImage(landscapeIMG,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(450, 170, false, false, false, false)));

            /*engine = new Image(fileURL("./assets/images/engine.png"));
            enginePop = new Image(fileURL("./assets/images/enginePop.png"));*/
            train = new ImagePattern(new Image(fileURL("./assets/images/train.png")));

        } catch (Exception e) {
            System.out.println("Error with graphics.");
            System.out.println(e);
        }
        /*try {
            // Preload all music tracks
            //backgroundMusic = new Media(fileURL("./assets/music/backgroundMusic.mp3"));

            // Preload all sound effects
            engineSound = new AudioClip(fileURL("./assets/soundfx/victorySound.wav"));
            trainWhistleSound = new AudioClip(fileURL("./assets/soundfx/gameOverSound.wav"));
            //shootingSound.setVolume(0.1);
    
        } catch (Exception e) {
            System.out.println("Error with sound effects.");
        }*/
    }
    static public Background getLandscapeBackground() {
        return landscapeBackground;
    }
    
    static public ImagePattern getLandscape() {     //load images of the landscape
        return landscape;
    }

    public static Image getEngine() {           //load an image for the engine
        return engine;
    }

    public static Image getEnginePop() {        //get the image for the engine when it pops out
        return enginePop;
    }

    public static ImagePattern getTrain() {     //get an image for the train
        return train;
    }

    public static AudioClip getEngineSound() {      //load the sound effect for the train
        return engineSound;
    }

    public static AudioClip getTrainWhistleSound() {            //get the train whistle sound
        return trainWhistleSound;
    }

}
