/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import static java.lang.Math.cos;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author zoewong
 */
public class FXMLTrainController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    AnchorPane flatPane;
    @FXML
    AnchorPane anglePane;
    @FXML
    AnchorPane trainAndTrackPane;
    @FXML
    private Slider massTrainSlider;
    @FXML
    private Label massTrainLabel;
    @FXML
    private Slider runTimeSlider;
    @FXML
    private Label runTimeLabel;
    @FXML
    private TextField angleText;
    @FXML
    private Label distanceFlatLabel;
    @FXML
    private Label vMaxFlatLabel;
    @FXML
    private Label accelerationFlatLabel;
    @FXML
    private Label distanceRampLabel;
    @FXML
    private Label heightRampLabel;
    @FXML
    private Label speedRampLabel;
    @FXML
    private Button startTrainButton;
    @FXML
    private Line angleTrack;
    @FXML
    private Line flatTrack;
    @FXML
    private Rectangle trainA;
    @FXML
    private Rectangle trainB;
    @FXML
    private Rectangle backgroundFlatA;
    @FXML
    private Rectangle backgroundFlatB;
    @FXML
    private Rectangle backgroundAngleA;
    @FXML
    private Label userMessageLabel;
    @FXML
    private Label currentSpeedFlatLabel;

    private double lastFrameTime = 0.0;
    private double lastFrameTimeB = 0.0;
    double massTrain;
    double runTime;
    double angle;
    double power;
    double TRAINA_SPEED = 5;
    double TRAINB_SPEED = 5;

    double trainPosition;

    Train train;

    DecimalFormat formater = new DecimalFormat("###.##");

    public void addToFlatPane(Node node) {
        flatPane.getChildren().add(node);
    }

    public void addToAnglePane(Node node) {
        anglePane.getChildren().add(node);
    }

    @FXML
    private void startTrainButtonAction(ActionEvent event) {                  //calculate all the values relying on the time change
        Engine engine = FXMLEngineController.engine;
        train = new Train();

        try {
            massTrain = massTrainSlider.getValue();
            runTime = runTimeSlider.getValue();
            angle = Double.parseDouble(angleText.getText());
            power = engine.calcPower();
            train = new Train(massTrain, power, angle, runTime);
            startTrainButton.setDisable(true);
        } catch (Exception e) {
            userMessageLabel.setText("Error! Please make sure your input is valid!");
        }

        try {
            vMaxFlatLabel.setText(formater.format(train.calculateMaxVeloctiyFlat()) + " m/s");
            distanceFlatLabel.setText(formater.format(train.calculateDistanceFlat()) + " m");
            accelerationFlatLabel.setText(formater.format(train.calculateAccerlationFlat()) + " m/s^2");
            distanceRampLabel.setText(formater.format(train.calculateDistanceOnRamp()) + " m");
            heightRampLabel.setText(formater.format(train.calculateHeightOnRamp()) + " m");
//            speedRampLabel.setText(formater.format(train.calculateVelocityAngle(runTime)) + " m/s");
        } catch (Exception e) {
            userMessageLabel.setText("Error! Calculation cannot proceed!");
        }

        double angle = -Double.parseDouble(angleText.getText());
        trainAndTrackPane.setRotate(angle);             //rotates angle track and train according to the angle
        startTrainFlatAnimation();
        //startTrainAngleAnimation();

    }

    @FXML
    private void getMassTrainSliderValue() {                                    //displays the values for mass train
        massTrainLabel.setText(formater.format(massTrainSlider.getValue()) + " kg");
    }

    @FXML
    private void getRunTimeSliderValue() {                                      //displays the values for run time
        runTimeLabel.setText(formater.format(runTimeSlider.getValue()) + " sec");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AssetManager.preloadAllAssets();

        flatPane.setBackground(AssetManager.getLandscapeBackground());

        massTrainSlider.setMin(100000);             //set default values for mass of train slider
        massTrainSlider.setMax(10000000);
        massTrainSlider.setValue(100000);
        massTrainSlider.setShowTickLabels(true);
        massTrainSlider.setShowTickMarks(true);
        massTrainSlider.setMajorTickUnit(500000);
        massTrainSlider.setMinorTickCount(0);

        getMassTrainSliderValue();

        massTrainSlider.valueProperty().addListener(new ChangeListener() {          //displays the values of the slider as they change 
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                massTrainLabel.setText(
                        String.valueOf(formater.format((int) massTrainSlider.getValue()) + " kg"));
            }
        });

        runTimeSlider.setMin(1);             //set default values for run time slider
        runTimeSlider.setMax(90);
        runTimeSlider.setValue(0);
        runTimeSlider.setShowTickLabels(true);
        runTimeSlider.setShowTickMarks(true);
        runTimeSlider.setMajorTickUnit(10);

        getRunTimeSliderValue();

        runTimeSlider.valueProperty().addListener(new ChangeListener() {              //displays the values of the slider as they change 
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                runTimeLabel.setText(
                        String.valueOf(formater.format((int) runTimeSlider.getValue()) + " sec"));
            }
        });

        //forces the field to be numeric only
        startTrainButton.setDisable(true);
        angleText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                startTrainButton.setDisable(false);
                if (!newValue.matches("\\.") && !newValue.matches("\\d*")) {
                    angleText.setText(newValue.replaceAll("[^\\d\\.]", ""));
                }
                try {
                    userMessageLabel.setText("Project SES");
                    if (Double.parseDouble(newValue) > 52) {        //sets the maximum angle to 52
                        angleText.setText("52");
                    }
                } catch (Exception e) {
                    startTrainButton.setDisable(true);
                    userMessageLabel.setText("Please do not enter empty value");
                }
            }
        });

        backgroundFlatA = new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight(), AssetManager.getLandscape());     //creates and sets a background for the flat animation
        backgroundFlatA.setX(0);
        backgroundFlatA.setY(0);
        backgroundFlatA.setVisible(true);
        backgroundFlatA.setClip(new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight()));         //ensures that the image stays within the confines of the box
        addToFlatPane(backgroundFlatA);
        flatPane.toBack();

        backgroundFlatB = new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight(), AssetManager.getLandscape());
        backgroundFlatB.setX(backgroundFlatA.getWidth());
        backgroundFlatB.setY(0);
        backgroundFlatB.setVisible(true);
        backgroundFlatB.setClip(new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight()));
        addToFlatPane(backgroundFlatB);

        backgroundAngleA = new Rectangle(anglePane.getPrefWidth(), anglePane.getPrefHeight(), AssetManager.getLandscape());
        backgroundAngleA.setX(0);
        backgroundAngleA.setY(0);
        backgroundAngleA.setVisible(true);
        addToAnglePane(backgroundAngleA);
        anglePane.toBack();

        trainA = new Rectangle(75, 30);         //creates and sets a train for the animation on flat
        trainA.setX(75);
        trainA.setY(flatPane.getPrefHeight() / 1.22 - trainA.getHeight());
        trainA.setFill(AssetManager.getTrain());
        trainA.setVisible(true);

        trainB = new Rectangle(75, 30);         //creates and sets a train for the animation on angle
        trainB.setX(anglePane.getPrefWidth() / 2 - 65);
        trainB.setY(anglePane.getPrefHeight() / 2 - trainB.getHeight());
        trainB.setFill(AssetManager.getTrain());
        trainB.setVisible(true);

        flatTrack = new Line(0, flatPane.getPrefHeight() / 1.22, flatPane.getPrefWidth(), flatPane.getPrefHeight() / 1.22);              //creates and sets a traintrack for the animation on flat
        flatTrack.setVisible(true);
        addToFlatPane(flatTrack);
        addToFlatPane(trainA);
        flatTrack.setClip(new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight()));

        angleTrack = new Line(0, anglePane.getPrefHeight()/2, anglePane.getPrefWidth(), anglePane.getPrefHeight()/2);              //creates and sets a traintrack for the animation on flat
        angleTrack.setVisible(true);
        trainAndTrackPane.getChildren().addAll(angleTrack, trainB);         //allows the train and the tracktrain to become angled together
        trainAndTrackPane.toFront();
        
        trainAndTrackPane.setClip(new Rectangle(anglePane.getPrefWidth(), anglePane.getPrefHeight()));
        anglePane.setClip(new Rectangle(anglePane.getPrefWidth(), anglePane.getPrefHeight()));
        angleTrack.setClip(new Rectangle(anglePane.getPrefWidth(), anglePane.getPrefHeight()));

    }

    private double getAngle() {
        double angleValue = Double.parseDouble(angleText.getText());
        angleValue = Math.toRadians(angleValue);            //converts the angle from degrees to radian
        return anglePane.getPrefWidth() * Math.tan(angleValue);
//        return Double.parseDouble(angleText.getText());
    }

    private void startTrainFlatAnimation() {

        Engine engine = FXMLEngineController.engine;
        power = engine.calcPower();

        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Time calculation                
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                //double maxSpeed = Double.parseDouble(vMaxFlatLabel.getText().split(" ")[0]);
                double maxSpeed = train.calculateMaxVeloctiyFlat();
                double computedSpeed = Double.parseDouble(accelerationFlatLabel.getText().split(" ")[0]) * currentTime;

                TRAINA_SPEED = computedSpeed > maxSpeed ? maxSpeed : computedSpeed;             //determines the speed of the train

                currentSpeedFlatLabel.setText(formater.format(TRAINA_SPEED) + " m/s");          //displays dynamic speed value

                if (currentTime <= runTime) {               //moves the background according to velocity to create the animation on flat surce
                    if (backgroundFlatA.getX() + backgroundFlatA.getWidth() >= 0) {
                        backgroundFlatA.setX(backgroundFlatA.getX() - TRAINA_SPEED);
                    } else {
                        backgroundFlatA.setX(backgroundFlatB.getWidth() - TRAINA_SPEED);
                        backgroundFlatA.setX(backgroundFlatA.getX() - TRAINA_SPEED);
                    }

                    if (backgroundFlatB.getX() + backgroundFlatB.getWidth() >= 0) {
                        backgroundFlatB.setX(backgroundFlatB.getX() - TRAINA_SPEED);
                    } else {
                        backgroundFlatB.setX(backgroundFlatA.getWidth() - TRAINA_SPEED);
                        backgroundFlatB.setX(backgroundFlatB.getX() - TRAINA_SPEED);
                    }
                } else {
                    System.out.println("Strat Train angle");
                    startTrainAngleAnimation();
                    this.stop();
                }
            }
        }.start();
    }

    private void startTrainAngleAnimation() {           //same as the privious method rn
        lastFrameTimeB = 0.0f;
        long initialTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Time calculation                
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTimeB;
                lastFrameTime = currentTime;

                train.setAngle(Double.parseDouble(angleText.getText()));
                speedRampLabel.setText(formater.format(train.calculateVelocityAngle(currentTime)) + " m/s");
                trainPosition = train.calculateCurrentPositionOnRamp(currentTime);

                trainB.setX((anglePane.getPrefWidth() / 2 - 65) + 3 * frameDeltaTime * train.calculateVelocityAngle(currentTime));       //require change or line 330 and 331
                //trainB.setY(anglePane.getPrefHeight() / 1.05 - trainB.getHeight() - 3 * frameDeltaTime *train.calculateVelocityAngle(currentTime));

                if(trainPosition <= 0){
                    this.stop();
                }
            }

        }.start();
    }

}
