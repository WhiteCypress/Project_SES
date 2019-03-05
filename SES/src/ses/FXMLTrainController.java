/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author zoewong
 */
public class FXMLTrainController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
