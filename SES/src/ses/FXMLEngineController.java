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

        Engine engine = new Engine();

        double inputVolLiq = Double.parseDouble(volLiqText.getText());
        vapTimeLabel.setText(engine.calcVapTime() + " s");
        enginePowerLabel.setText(engine.calcPower() + " W");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setBackground(AssetManager.getTrainTrack());

        materialContList.getItems().addAll("copper", "aluminium", "beryllium",
                "boron", "cadmium", "cesium", "chromium", "cobalt", "gold", "hafnium",
                "iridium", "iron", "lead", "nickel", "platinum", "stainless-steel");

        typeLiqList.getItems().addAll("water", "alcohol", "ether", "hexane", "gasoline");

        materialCombList.getItems().addAll("stove", "natural gas", "methane",
                "hydrogen", "carbon monoxide", "wood", "charcoal");

        volContSlider.setMin(0);
        volContSlider.setMax(0);
        volContSlider.setValue(0);
        volContSlider.setShowTickLabels(true);
        volContSlider.setShowTickMarks(true);
        volContSlider.setMajorTickUnit(0);
        volContSlider.setMinorTickCount(0);
        volContSlider.setBlockIncrement(0);

        thicContSlider.setMin(0);
        thicContSlider.setMax(0);
        thicContSlider.setValue(0);
        thicContSlider.setShowTickLabels(true);
        thicContSlider.setShowTickMarks(true);
        thicContSlider.setMajorTickUnit(0);
        thicContSlider.setMinorTickCount(0);
        thicContSlider.setBlockIncrement(0);
    }

}