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
    @FXML
    private Slider volContSlider;    
    @FXML
    private Slider thicContSlider;
    @FXML
    private ComboBox typeLiqList;
    @FXML
    private TextField volLiqText;
    @FXML
    private ComboBox materialCombList;
    @FXML
    private Label vapTimeLabel;    
    @FXML
    private Label enginePowerLabel;
    @FXML
    private Button startEngine;    
    @FXML
    private Button launchTrain;

    @FXML
    private void startEngineButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

        Engine engine = new Engine();       //testing values
        engine.transferConstant = 14.4;      //home pot are normally stainless steel #304, so this is the value for it
        engine.fireTemp = 230;              //the temperatur of home stove
        engine.liquidInitialTemp = 20;
        engine.thicCont = 0.01;
        engine.liquidBoilPoint = 100;

        engine.liquidMass = 1; //15 L of water
        engine.liquidSpecificHeat = 4.186 * 1000;

        System.out.println("the time it takes to boil is: " + engine.calcBoilTime() + " seconds");
        System.out.println(engine.calcHeatTransferRate() + " J/s , the change in temperature is: " + engine.calcTempInContChange(engine.calcHeatTransferRate()) + " kelvin");     //test end
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materialContList = new ComboBox();
        materialContList.getItems().addAll("elements");
    
    }

}
