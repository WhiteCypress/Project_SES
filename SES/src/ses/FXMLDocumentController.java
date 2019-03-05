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

        Engine engine = new Engine("stainless-steel", 20, 0.02, "water", 1, "stove");       //testing values

        for (double i = 0; i < 60; i+=0.1) {
            System.out.println("TIME IS : " + i);
            engine.calcMoles();
            System.out.println("moles: " + engine.liquidMoles);
            System.out.println("time to vaporize: " + engine.calcVapTime());
            System.out.println("the time it takes to boil is: " + engine.calcBoilTime() + " seconds");
            System.out.println("the heat transfer rate is: " + engine.calcHeatTransferRate() + " J/s , the temperature is: " + engine.calcTempInCont(0.1*engine.calcHeatTransferRate()) + " after " + i + " second");     //test end
            System.out.println("the power of this engine is: " + engine.calcPower() + "Watt");
        }
        
        //end of thes values
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
