/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
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
    private Button startEngine;
    @FXML
    private Button launchTrain;

    public static Engine engine;

    String inputMaterialCont;
    double inputVolCont = 250;
    double inuputThicCont;
    String inputTypeLiq;
    double inputVolLiq;
    String inputMaterialConb;

    DecimalFormat formater = new DecimalFormat("###.##");

    @FXML
    private void launchTrainButtonAction(ActionEvent event) throws IOException {                //creat a train window and replace this window with it
        Parent gameParent = FXMLLoader.load(getClass().getResource("FXMLTrain.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    @FXML
    private void startEngineButtonAction(ActionEvent event) {                   //does all calculations and display them. Once the deltaTime works, link these values with the time
        try {
            inputMaterialCont = materialContList.getValue();
            inputVolCont = volContSlider.getValue();
            inuputThicCont = thicContSlider.getValue();
            inputTypeLiq = typeLiqList.getValue();
            inputVolLiq = Double.parseDouble(volLiqText.getText());
            inputMaterialConb = materialCombList.getValue();
        } catch (Exception e) {
            vapTimeLabel.setText("Error! Please make sure your input is valid!");               //message label
        }

        engine = new Engine(inputMaterialCont, inputVolCont, inuputThicCont, inputTypeLiq, inputVolLiq, inputMaterialConb);

        try {
            vapTimeLabel.setText(formater.format(engine.calcVapTime()) + " s");
            enginePowerLabel.setText(formater.format(engine.calcPower()) + " W");
        } catch (Exception e) {
            vapTimeLabel.setText("Error! Calculation cannot precedd!");                     //message label
        }
    }

    @FXML
    private void getVolContSliderValue() {                                      //display the value of volCont
        volContLabel.setText(volContSlider.getValue() + " L");
    }

    @FXML
    private void getThicContSliderValue() {                                     //display the value of thicCont
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

        volContSlider.setMin(5);                                                //set default values for the slider
        volContSlider.setMax(500);
        volContSlider.setValue(250);
        volContSlider.setShowTickLabels(true);
        volContSlider.setShowTickMarks(true);
        volContSlider.setMajorTickUnit(50);
        volContSlider.setMinorTickCount(25);
        volContSlider.setBlockIncrement(10);

        getVolContSliderValue();

        volContSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                volContLabel.setText(
                        String.valueOf((int) volContSlider.getValue() + " L"));
                                
                inputVolCont = (int) volContSlider.getValue();
            }
        });

        thicContSlider.setMin(0.01);
        thicContSlider.setMax(0.1);
        thicContSlider.setValue(0.05);
        thicContSlider.setShowTickLabels(true);
        thicContSlider.setShowTickMarks(true);
        thicContSlider.setMajorTickUnit(0.015);
        thicContSlider.setMinorTickCount(1);
        thicContSlider.setBlockIncrement(0.01);

        getThicContSliderValue();

        thicContSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                thicContLabel.setText(
                        String.valueOf(formater.format((double) thicContSlider.getValue()) + " m"));
            }
        });

        // force the field to be numeric only
        volLiqText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\.") && !newValue.matches("\\d*")) {
                    volLiqText.setText(newValue.replaceAll("[^\\d\\.]", ""));
                }
                if(Double.parseDouble(newValue) > inputVolCont){
                    volLiqText.setText(Double.toString(inputVolCont));
                }
            }
        });
    }

}
