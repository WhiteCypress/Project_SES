package ses;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.ImagePattern;

public class AssetManager {

    static private ImagePattern landscape = null;
    static private Background landscapeBackground = null;

    static private ImagePattern train = null;

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets() {
        // Preload all images (train backgrounds and train)
        try {
            landscape = new ImagePattern(new Image(fileURL("./assets/images/landscape.png")));

            Image landscapeIMG = new Image(fileURL("./assets/images/landscape.png"));
            landscapeBackground = new Background(
                    new BackgroundImage(landscapeIMG,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(450, 170, false, false, false, false)));

            train = new ImagePattern(new Image(fileURL("./assets/images/train.png")));

        } catch (Exception e) {
            System.out.println("Error with graphics.");
            System.out.println(e);
        }

    }
    static public Background getLandscapeBackground() {        //gets background for the landscape
        return landscapeBackground;
    }
    
    static public ImagePattern getLandscape() {     //gets image of the landscape
        return landscape;
    }

    public static ImagePattern getTrain() {     //gets an image for the train
        return train;
    }
}