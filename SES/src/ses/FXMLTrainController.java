/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author zoewong
 */
public class FXMLTrainController implements Initializable {

    @FXML
    AnchorPane pane;
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

    double massTrain;
    double runTime;
    double angle;
    double power;

    DecimalFormat formater = new DecimalFormat("###.##");

    @FXML
    private void startTrainButtonAction(ActionEvent event) {                  //calculate all the values right now, but these should be later relie on the time change
        Engine engine = FXMLEngineController.engine;

        try {
            massTrain = massTrainSlider.getValue();
            runTime = runTimeSlider.getValue();
            angle = Double.parseDouble(angleText.getText());
            power = engine.calcPower();
        } catch (Exception e) {
            vMaxFlatLabel.setText("Error! Please make sure your input is valid!");
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
            vMaxFlatLabel.setText("Error! Calculation cannot precedd!");
        }
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
        pane.setBackground(AssetManager.getTrainTrack());

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
                if(Double.parseDouble(newValue) > 60){
                    angleText.setText("60");
                }
            }
        });
    }
}
