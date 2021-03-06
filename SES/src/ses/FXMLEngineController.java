/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author zoewong
 */
public class FXMLEngineController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    private ComboBox<String> materialContList;
    @FXML
    private Slider volContSlider;
    @FXML
    private Label volContLabel;
    @FXML
    private Slider thicContSlider;
    @FXML
    private ComboBox<String> typeLiqList;
    @FXML
    private Label thicContLabel;
    @FXML
    private TextField volLiqText;
    @FXML
    private ComboBox<String> materialCombList;
    @FXML
    private Label vapTimeLabel;
    @FXML
    private Label enginePowerLabel;
    @FXML
    private Label currentPressureLabel;
    @FXML
    private Label currentTempLabel;
    @FXML
    private Button startEngine;
    @FXML
    private Button launchTrain;
    @FXML
    private Label userMessageLabel;

    public static Engine engine;

    String inputMaterialCont;
    double inputVolCont = 250;
    double inuputThicCont;
    String inputTypeLiq;
    double inputVolLiq;
    String inputMaterialConb;

    DecimalFormat formater = new DecimalFormat("###.##");

    private double lastFrameTime = 0.0;
    private ArrayList<Circle> circleList = new ArrayList<>();
    private ArrayList<Vector2D> circleVelocityList = new ArrayList<>();
    private int BALLS_NUMBER = 100;
    private int WALL_HEIGHT = 400;
    private double BAR_SPEED = 0.5;
    private double BAR_ACC;
    private int MOLECULE_RADIUS = 10;
    private Line l;
    private Rectangle r;
    private Rectangle border;

    private double PARTICLE_SPEED = 10;
    
    private boolean running = true;             //if this is false, the animation timer will handle nothing, it doesn't stop it though

    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    @FXML
    private void launchTrainButtonAction(ActionEvent event) throws IOException {                //creat a train window and replace engine window with it
        Parent gameParent = FXMLLoader.load(getClass().getResource("FXMLTrain.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
        running = false;
    }

    private void startEngineAnimation() {
        if (!materialContList.getValue().equals("Material of Container") && !typeLiqList.getValue().equals("Type of Liquid") && !volLiqText.getText().equals("") && !materialCombList.getValue().equals("Material of Combustion")) {
            startEngine.setDisable(true);       //disables engine button once it is clicked
            launchTrain.setDisable(false);      //enables train button once engine button is clicked

            BALLS_NUMBER = (int) (inputVolLiq * 2);

            l = new Line(0, WALL_HEIGHT, pane.getPrefWidth() + 20, WALL_HEIGHT);        //creates the line indicating the top of the liquid/gas
            r = new Rectangle(60, 40);
            r.setX(150);
            l.setVisible(true);
            r.setVisible(true);
            addToPane(l);
            addToPane(r);

            // Adds random circles
            for (int i = 0; i < BALLS_NUMBER; ++i) {
                Random rng = new Random();
                int width = (int) pane.getPrefWidth();
                int height = (int) pane.getPrefHeight();
                int x = rng.nextInt(width);
                int y = rng.nextInt(height - WALL_HEIGHT + 1) + WALL_HEIGHT;        //sets the height of the bar
                int radius = MOLECULE_RADIUS;

                Circle c = new Circle(0, 0, radius);
                c.setCenterX(x);
                c.setCenterY(y);
                c.setFill(Color.RED);
                c.setStroke(Color.BLACK);
                circleList.add(c);
                circleVelocityList.add(new Vector2D((rng.nextDouble() - 0.5) * 400, (rng.nextDouble() - 0.5) * 400));
                addToPane(c);
            }

            lastFrameTime = 0.0f;
            long initialTime = System.nanoTime();

            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (running) {
                        // Time calculation                
                        double currentTime = (now - initialTime) / 1000000000.0;                //get time in seconds
                        double frameDeltaTime = currentTime - lastFrameTime;
                        lastFrameTime = currentTime;
                        double oldLineYPosition = l.getEndY();

                        engine.calcTempInCont(frameDeltaTime * engine.heatTransferRate);
                        BAR_SPEED = frameDeltaTime * ((WALL_HEIGHT - 20) / Double.parseDouble(vapTimeLabel.getText().split(" ")[0]));       //sets the speed of the molecules
                        if (oldLineYPosition > 40) {            //sets the position of the bar
                            l.setStartY(oldLineYPosition - BAR_SPEED);
                            l.setEndY(oldLineYPosition - BAR_SPEED);

                            r.setY(oldLineYPosition - BAR_SPEED - 20);
                        } else {
                            r.setY(oldLineYPosition - BAR_SPEED - 40);
                        }

                        double newLineYPosition = l.getEndY();
                        double multiplier = frameDeltaTime / (PARTICLE_SPEED / 2 * newLineYPosition) * 1000;
                        double topCap = 0.22;       //sets the maximum height of the bar
                        if (multiplier > topCap) {  //sets when the engine cap pops
                            multiplier = topCap;
                        }

                        // Move circles every frame
                        for (int i = 0; i < circleList.size(); i++) {
                            Circle c = circleList.get(i);
                            Vector2D position = new Vector2D(c.getCenterX(), c.getCenterY());
                            Vector2D v = circleVelocityList.get(i);
                            position = position.add(v.mult(multiplier));
                            c.setCenterX(position.getX());
                            c.setCenterY(position.getY());

                            // collision with edges
                            if (c.getCenterX() - c.getRadius() < 5) {
                                v.setX(Math.abs(v.getX()));
                            }

                            if (c.getCenterX() + c.getRadius() > pane.getWidth() - 10) {
                                v.setX(-Math.abs(v.getX()));
                            }

                            if (c.getCenterY() - c.getRadius() < 0 + newLineYPosition) {
                                v.setY(Math.abs(v.getY()));
                            }

                            if (c.getCenterY() + c.getRadius() > pane.getHeight() - 10) {
                                v.setY(-Math.abs(v.getY()));
                            }
                        }

                        //Changes the values dynamically
                        engine.calcTempInCont(frameDeltaTime * engine.heatTransferRate);
                        currentPressureLabel.setText(formater.format(101.2 + engine.calcCurrentPressure(engine.vapTime, currentTime)) + " Pa");
                        currentTempLabel.setText(formater.format(engine.calcTempInCont(currentTime)) + " Degree");
                    }else{
                        this.stop();
                    }
                }
            }.start();
        }
    }

    @FXML
    private void startEngineButtonAction(ActionEvent event) {                   //Performs all calculations and display them. Once the deltaTime works, links these values with the time
        int error = 0;
        try {
            inputMaterialCont = materialContList.getValue();
            inputVolCont = volContSlider.getValue();
            inuputThicCont = thicContSlider.getValue();
            inputTypeLiq = typeLiqList.getValue();
            inputVolLiq = Double.parseDouble(volLiqText.getText());
            inputMaterialConb = materialCombList.getValue();

            engine = new Engine(inputMaterialCont, inputVolCont, inuputThicCont, inputTypeLiq, inputVolLiq, inputMaterialConb);

        } catch (Exception e) {
            error = 1;
            userMessageLabel.setText("Error! Please make sure your input is valid!");             
        }

        try {
            if (error == 0) {
                vapTimeLabel.setText(formater.format(engine.calcVapTime()) + " s");
                enginePowerLabel.setText(formater.format(engine.calcPower()) + " W");
                userMessageLabel.setText("Project SES");
                startEngineAnimation();
            }
        } catch (Exception e) {
            userMessageLabel.setText("Error! Calculation cannot proceed!");               
        }
        
    }

    @FXML
    private void getVolContSliderValue() {                                      //displays the value of volume of container
        volContLabel.setText(formater.format((int)volContSlider.getValue()) + " L");
    }

    @FXML
    private void getThicContSliderValue() {                                     //displays the value of thickness of container
        thicContLabel.setText(formater.format(thicContSlider.getValue()) + " m");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        pane.setBackground(AssetManager.getTrainTrack());

        materialContList.getItems().addAll("copper", "aluminium", "beryllium", //add values for the comboBox so we can refer to them later
                "boron", "cadmium", "cesium", "chromium", "cobalt", "gold", "hafnium",
                "iridium", "iron", "lead", "nickel", "platinum", "stainless-steel");

        typeLiqList.getItems().addAll("water", "alcohol", "ether", "hexane", "gasoline");

        materialCombList.getItems().addAll("stove", "natural gas", "methane",
                "hydrogen", "carbon monoxide", "wood", "charcoal");

        volContSlider.setMin(5);                    //set default values for the volume of container slider
        volContSlider.setMax(500);
        volContSlider.setValue(250);
        volContSlider.setShowTickLabels(true);
        volContSlider.setShowTickMarks(true);
        volContSlider.setMajorTickUnit(50);
        volContSlider.setMinorTickCount(25);
        volContSlider.setBlockIncrement(10);

        getVolContSliderValue();

        volContSlider.valueProperty().addListener(new ChangeListener() {           //displays the values of the slider as they change 
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                volContLabel.setText(
                        String.valueOf(formater.format((int) volContSlider.getValue()) + " L"));

                inputVolCont = (int) volContSlider.getValue();
            }
        });

        thicContSlider.setMin(0.01);               //set default values for the thickness of container slider
        thicContSlider.setMax(0.1);
        thicContSlider.setValue(0.05);
        thicContSlider.setShowTickLabels(true);
        thicContSlider.setShowTickMarks(true);
        thicContSlider.setMajorTickUnit(0.015);
        thicContSlider.setMinorTickCount(1);
        thicContSlider.setBlockIncrement(0.01);

        getThicContSliderValue();

        thicContSlider.valueProperty().addListener(new ChangeListener() {           //displays the values of the slider as they change 
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                thicContLabel.setText(
                        String.valueOf(formater.format((double) thicContSlider.getValue()) + " m"));
            }
        });

        //Ensures the field to be numeric only
        volLiqText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\.") && !newValue.matches("\\d*")) {
                    volLiqText.setText(newValue.replaceAll("[^\\d\\.]", ""));
                }
                try {
                    userMessageLabel.setText("Project SES");
                    if (Double.parseDouble(newValue) > inputVolCont) {
                        volLiqText.setText(Double.toString(inputVolCont));
                    }
                } catch (Exception e) {
                    userMessageLabel.setText("Please do not enter empty value");
                }
            }
        });

        launchTrain.setDisable(true);

        border = new Rectangle(pane.getPrefWidth() + 20, pane.getPrefHeight() + 20, Color.LIGHTGRAY);       //sets the border for the engine animation
        border.setStroke(Color.BLACK);
        border.setX(0);
        border.setY(0);
        border.setVisible(true);
        addToPane(border);
    }
}