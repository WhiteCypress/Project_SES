/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    double massTrain;
    double runTime;
    double angle;
    double power;
    double TRAINA_SPEED = 5;

    DecimalFormat formater = new DecimalFormat("###.##");

    public void addToFlatPane(Node node) {
        flatPane.getChildren().add(node);
    }

    public void addToAnglePane(Node node) {
        anglePane.getChildren().add(node);
    }

    @FXML
    private void startTrainButtonAction(ActionEvent event) {                  //calculate all the values right now, but these should be later relie on the time change
        Engine engine = FXMLEngineController.engine;

        try {
            massTrain = massTrainSlider.getValue();
            runTime = runTimeSlider.getValue();
            angle = Double.parseDouble(angleText.getText());
            power = engine.calcPower();
        } catch (Exception e) {
            userMessageLabel.setText("Error! Please make sure your input is valid!");
        }
        Train train = new Train(massTrain, power, angle, runTime);
        //train.setMovTime(runTime);

        //System.out.println(train.calculateDistanceFlat());
        try {
            vMaxFlatLabel.setText(formater.format(train.calculateMaxVeloctiyFlat()) + " m/s");
            distanceFlatLabel.setText(formater.format(train.calculateDistanceFlat()) + " m");
            accelerationFlatLabel.setText(formater.format(train.calculateAccerlationFlat()) + " m/s^2");
            distanceRampLabel.setText(formater.format(train.calculateDistanceOnRamp()) + " m");
            heightRampLabel.setText(formater.format(train.calculateHeightOnRamp()) + " m");
            speedRampLabel.setText(formater.format(train.calculateVelocityAngle(runTime)) + " m/s");
        } catch (Exception e) {
            userMessageLabel.setText("Error! Calculation cannot proceed!");
        }

        angleTrack.endYProperty().setValue(anglePane.getPrefHeight() / 1.25 - getAngle());
        startTrainFlatAnimation();

    }

    @FXML
    private void getMassTrainSliderValue() {                                    //display the values for mass train
        massTrainLabel.setText(formater.format(massTrainSlider.getValue()) + " kg");
    }

    @FXML
    private void getRunTimeSliderValue() {                                      //display the values for mass train
        runTimeLabel.setText(formater.format(runTimeSlider.getValue()) + " sec");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AssetManager.preloadAllAssets();
        massTrainSlider.setMin(100000);
        massTrainSlider.setMax(10000000);
        massTrainSlider.setValue(100000);
        massTrainSlider.setShowTickLabels(true);
        massTrainSlider.setShowTickMarks(true);
        massTrainSlider.setMajorTickUnit(500000);
        massTrainSlider.setMinorTickCount(0);

        getMassTrainSliderValue();

        massTrainSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                massTrainLabel.setText(
                        String.valueOf(formater.format((int) massTrainSlider.getValue()) + " kg"));
            }
        });

        runTimeSlider.setMin(0);
        runTimeSlider.setMax(90);
        runTimeSlider.setValue(0);
        runTimeSlider.setShowTickLabels(true);
        runTimeSlider.setShowTickMarks(true);
        runTimeSlider.setMajorTickUnit(10);

        getRunTimeSliderValue();

        runTimeSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                runTimeLabel.setText(
                        String.valueOf(formater.format((int) runTimeSlider.getValue()) + " sec"));
            }
        });

        // force the field to be numeric only
        angleText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\.") && !newValue.matches("\\d*")) {
                    angleText.setText(newValue.replaceAll("[^\\d\\.]", ""));
                }
                if (Double.parseDouble(newValue) > 60) {
                    angleText.setText("60");
                }
            }
        });

        backgroundFlatA = new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight(), AssetManager.getLandscape());
        backgroundFlatA.setX(0);
        backgroundFlatA.setY(0);
        backgroundFlatA.setVisible(true);
        backgroundFlatA.setClip(new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight()));
        addToFlatPane(backgroundFlatA);
        flatPane.toBack();

        backgroundFlatB = new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight(), AssetManager.getLandscape());
        backgroundFlatB.setX(backgroundFlatA.getWidth());
        backgroundFlatB.setY(0);
        backgroundFlatB.setVisible(true);
        backgroundFlatB.setClip(new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight()));
        addToFlatPane(backgroundFlatB);

        backgroundAngleA = new Rectangle(anglePane.getPrefWidth(), anglePane.getPrefHeight(), Color.LIGHTGRAY);
        backgroundAngleA.setX(0);
        backgroundAngleA.setY(0);
        backgroundAngleA.setVisible(true);
        addToAnglePane(backgroundAngleA);

        trainA = new Rectangle(75, 30);
        trainA.setX(75);
        trainA.setY(flatPane.getPrefHeight() / 1.22 - trainA.getHeight());
        trainA.setFill(AssetManager.getTrain());
        trainA.setVisible(true);

        trainB = new Rectangle(75, 30);
        trainB.setX(0);
        trainB.setY(anglePane.getPrefHeight() / 1.05 - trainB.getHeight());
        trainB.setFill(AssetManager.getTrain());
        trainB.setVisible(true);

        flatTrack = new Line(0, flatPane.getPrefHeight() / 1.22, flatPane.getPrefWidth(), flatPane.getPrefHeight() / 1.22);
        flatTrack.setVisible(true);
        addToFlatPane(flatTrack);
        addToFlatPane(trainA);
        flatTrack.setClip(new Rectangle(flatPane.getPrefWidth(), flatPane.getPrefHeight()));

        angleTrack = new Line(0, anglePane.getPrefHeight() / 1.05, anglePane.getPrefWidth(), anglePane.getPrefHeight() / 1.05);
        angleTrack.setVisible(true);
        angleTrack.setClip(new Rectangle(anglePane.getPrefWidth(), anglePane.getPrefHeight()));

        addToAnglePane(angleTrack);
        addToAnglePane(trainB);
    }

    private double getAngle() {
        double angleValue = Double.parseDouble(angleText.getText());
        angleValue = Math.toRadians(angleValue);
        return anglePane.getPrefWidth() * Math.tan(angleValue);
//        return Double.parseDouble(angleText.getText());
    }

    private void startTrainFlatAnimation() {
        double lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        Engine engine = FXMLEngineController.engine;
//        massTrain = massTrainSlider.getValue();
//        runTime = runTimeSlider.getValue();
//        angle = Double.parseDouble(angleText.getText());
        power = engine.calcPower();

        Train tA = new Train(massTrain, power, 0, runTime);
        Train tB = new Train(massTrain, power, angle, runTime);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Time calculation                
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                double lastFrameTime = currentTime;
                double position = frameDeltaTime * Double.parseDouble(distanceFlatLabel.getText().split(" ")[0]) / runTimeSlider.getValue();
                TRAINA_SPEED = position;
                
                if (backgroundFlatA.getX() + backgroundFlatA.getWidth() > 0) {
                    backgroundFlatA.setX(backgroundFlatA.getX() - TRAINA_SPEED);
                } else {
                    backgroundFlatA.setX(backgroundFlatB.getWidth() - 10.5);
                }

                if (backgroundFlatB.getX() + backgroundFlatB.getWidth() > 0) {
                    backgroundFlatB.setX(backgroundFlatB.getX() - TRAINA_SPEED);
                } else {
                    backgroundFlatB.setX(backgroundFlatA.getWidth() - 10);
                }
            }
        }.start();
    }
}
