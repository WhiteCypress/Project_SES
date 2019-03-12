/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import java.io.IOException;
import java.net.URL;
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
        String inputMaterialCont = materialContList.getValue();
        double inputVolCont = volContSlider.getValue();
        double inuputThicCont = thicContSlider.getValue();
        String inputTypeLiq = typeLiqList.getValue();
        double inputVolLiq = Double.parseDouble(volLiqText.getText());
        String inputMaterialConb = materialCombList.getValue();

        engine = new Engine(inputMaterialCont, inputVolCont, inuputThicCont, inputTypeLiq, inputVolLiq, inputMaterialConb);

        vapTimeLabel.setText(engine.calcVapTime() + " s");
        enginePowerLabel.setText(engine.calcPower() + " W");
    }

    @FXML
    private void getVolContSliderValue() {
        volContLabel.setText(volContSlider.getValue() + " L");
    }

    @FXML
    private void getThicContSliderValue() {
        thicContLabel.setText(thicContSlider.getValue() + " m");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        pane.setBackground(AssetManager.getTrainTrack());

        materialContList.getItems().addAll("copper", "aluminium", "beryllium",
                "boron", "cadmium", "cesium", "chromium", "cobalt", "gold", "hafnium",
                "iridium", "iron", "lead", "nickel", "platinum", "stainless-steel");

        typeLiqList.getItems().addAll("water", "alcohol", "ether", "hexane", "gasoline");

        materialCombList.getItems().addAll("stove", "natural gas", "methane",
                "hydrogen", "carbon monoxide", "wood", "charcoal");

        volContSlider.setMin(5);
        volContSlider.setMax(500);
        volContSlider.setValue(250);
        volContSlider.setShowTickLabels(true);
        volContSlider.setShowTickMarks(true);
        volContSlider.setMajorTickUnit(50);
        volContSlider.setMinorTickCount(25);
        volContSlider.setBlockIncrement(10);

        volContSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                volContLabel.setText(
                        String.valueOf((int) volContSlider.getValue() + " L"));
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

        thicContSlider.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                thicContLabel.setText(
                        String.valueOf((double) thicContSlider.getValue() + " m"));
            }
        });
    }

}
