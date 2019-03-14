/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.net.URL;
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

    @FXML
    private void startTrainButtonAction(ActionEvent event) {
        Engine engine = FXMLEngineController.engine;

        double massTrain = massTrainSlider.getValue();
        double runTime = runTimeSlider.getValue();
        double angle = Double.parseDouble(angleText.getText());
        double power = engine.calcPower();
        Train train = new Train(massTrain, power, angle, 60);
        train.setMovTime(runTime);

        //System.out.println(train.calculateDistanceFlat());
        distanceFlatLabel.setText(train.calculateDistanceFlat() + " m");
        vMaxFlatLabel.setText(train.calculateMaxVeloctiyFlat() + " m/s");
        accelerationFlatLabel.setText(train.calculateAccerlationFlat() + " m/s^2");
        distanceRampLabel.setText(train.calculateDistanceOnRamp() + " m");
        heightRampLabel.setText(train.calculateHeightOnRamp() + " m");
        speedRampLabel.setText(train.calculateVelocityAngle(runTime) + " m/s");
    }

    @FXML
    private void getMassTrainSliderValue() {
        massTrainLabel.setText(massTrainSlider.getValue() + " kg");
    }

    @FXML
    private void getRunTimeSliderValue() {
        runTimeLabel.setText(runTimeSlider.getValue() + " sec");
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

        massTrainSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                massTrainLabel.setText(
                        String.valueOf((int) massTrainSlider.getValue() + " kg"));
            }
        });

        runTimeSlider.setMin(0);
        runTimeSlider.setMax(90);
        runTimeSlider.setValue(0);
        runTimeSlider.setShowTickLabels(true);
        runTimeSlider.setShowTickMarks(true);
        runTimeSlider.setMajorTickUnit(10);

        runTimeSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                runTimeLabel.setText(
                        String.valueOf((int) runTimeSlider.getValue() + " sec"));
            }
        });
    }
}
