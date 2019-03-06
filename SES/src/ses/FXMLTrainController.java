/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.net.URL;
import java.util.ResourceBundle;
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
    private Slider runTimeSlider;
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
        Engine engine = new Engine();

        double massTrain = massTrainSlider.getValue();

        double runTime = runTimeSlider.getValue();
        double angle = Double.parseDouble(angleText.getText());
        double energy = engine.calcPower();
        Train train = new Train(massTrain, energy, angle);

        distanceFlatLabel.setText(train.calculateDistanceFlat() + " m");
        vMaxFlatLabel.setText(train.calculateMaxVeloctiyFlat(energy) + " m/s");
        accelerationFlatLabel.setText(train.calculateAccerlationFlat() + " m/s^2");
        distanceRampLabel.setText(train.calculateDistanceOnRamp() + " m");
        heightRampLabel.setText(train.calculateHeightOnRamp() + " m");
        speedRampLabel.setText(train.calculateVelocityAngle(runTime) + " m/s");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setBackground(AssetManager.getTrainTrack());

        massTrainSlider.setMin(0);
        massTrainSlider.setMax(0);
        massTrainSlider.setValue(0);
        massTrainSlider.setShowTickLabels(true);
        massTrainSlider.setShowTickMarks(true);
        massTrainSlider.setMajorTickUnit(0);
        massTrainSlider.setMinorTickCount(0);
        massTrainSlider.setBlockIncrement(0);

        runTimeSlider.setMin(0);
        runTimeSlider.setMax(0);
        runTimeSlider.setValue(0);
        runTimeSlider.setShowTickLabels(true);
        runTimeSlider.setShowTickMarks(true);
        runTimeSlider.setMajorTickUnit(0);
        runTimeSlider.setMinorTickCount(0);
        runTimeSlider.setBlockIncrement(0);
    }

}
