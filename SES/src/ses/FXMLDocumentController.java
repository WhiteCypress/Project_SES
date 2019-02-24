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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 *
 * @author zoewong
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ComboBox materialContList;
    private Slider volContSlider;
    private ComboBox typeLiqList;
    private TextField volLiqText;
    private ComboBox materialCombList;
    private Label vapTimeLabel;
    private Slider massTrain;
    private Slider runTime;
    private TextField angleText;
    private Label distanceNetLabel;
    private Label vMaxLabel;
    private Label accelerationLabel;
    private Label movTimeLabel;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
