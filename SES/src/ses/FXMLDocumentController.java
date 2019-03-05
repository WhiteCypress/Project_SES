/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

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
    private void launchTrainButtonAction(ActionEvent event) throws IOException {
        Parent gameParent = FXMLLoader.load(getClass().getResource("FXMLTrain.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    @FXML
    private void startEngineButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

        Engine engine = new Engine();       //testing values
        engine.transferConstant = 14.4;      //home pot are normally stainless steel #304, so this is the value for it
        engine.fireTemp = 230;              //the temperatur of home stove
        engine.tempLiquid = 20;
        engine.liquidInitialTemp = 20;
        engine.thicCont = 0.01;
        engine.liquidBoilPoint = 100;

        engine.liquidMass = 1; //15 L of water
        engine.liquidSpecificHeat = 4.186 * 1000;

        engine.volCont = 2;
        engine.typeLiq = "water";
        engine.liquidMass = 1; //1 L of water
        engine.deriveliquidInfo();
        engine.calcMoles();
        System.out.println("moles: " + engine.liquidMoles);

        System.out.println("the time it takes to boil is: " + engine.calcBoilTime() + " seconds");
        System.out.println(engine.calcHeatTransferRate() + " J/s , the temperature is: " + engine.calcTempInCont(110 * engine.calcHeatTransferRate()) + " after one second");     //test end
        System.out.println("the power of this engine is: " + engine.calcPower() + "Watt");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materialContList.getItems().addAll("copper", "aluminium", "beryllium",
                "boron", "cadmium", "cesium", "chromium", "cobalt", "gold", "hafnium",
                "iridium", "iron", "lead", "nickel", "platinum", "stainless-steel");
        
        typeLiqList.getItems().addAll("water","alcohol","ether","hexane","gasoline");

        materialCombList.getItems().addAll("stove", "natural gas", "methane",
                "hydrogen", "carbon monoxide", "wood", "charcoal");

    }

}
