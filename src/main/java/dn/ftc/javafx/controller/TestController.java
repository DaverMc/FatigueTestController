package dn.ftc.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class TestController {



    @FXML
    private TextField maxExtensionField;

    @FXML
    private Label maxExtensionLabel;

    @FXML
    private CheckBox maxExtensionCheckbox;

    @FXML
    private TextField maxForceField;

    @FXML
    private Label maxForceLabel;

    @FXML
    private CheckBox maxForceCheckbox;

    @FXML
    private TextField strokesField;

    @FXML
    private Label strokesLabel;

    @FXML
    private CheckBox strokesCheckbox;

    @FXML
    private ChoiceBox<String> materialChoice;

    @FXML
    private Label materialLabel;

    @FXML
    private CheckBox upCheckbox;

    @FXML
    private CheckBox downCheckbox;

    @FXML
    private Label directionLabel;

    @FXML
    private TextField minExtensionField;

    @FXML
    private Label minExtensionLabel;

    @FXML
    private CheckBox minExtensionCheckbox;

    @FXML
    private TextField minForceField;

    @FXML
    private Label minForceLabel;

    @FXML
    private CheckBox minForceCheckbox;

    @FXML
    private Button startButton;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @FXML
    private Button centerButton;

    @FXML
    private Button calibrateButton;

    @FXML
    private Button selectDriveButton;

    @FXML
    private Button changeMaterialButton;

    @FXML
    private LineChart<String, Number> forceGraph;
    private XYChart.Series<String, Number> forceSeries = new XYChart.Series<>();

    @FXML
    private LineChart<Integer, Number> lengthGraph;

    @FXML
    private void onLengthKeyTyped() {
        // Ihre Logik hier
    }

    @FXML
    private void onForceKeyTyped() {
        // Ihre Logik hier
    }

    private int forceIndex = 0;
    @FXML
    private void onStartButtonClick() {
        if(forceIndex == 0) {
            forceSeries.setName("Force");
            forceGraph.getData().add(forceSeries);
        }
        Double f = Math.random();
        forceSeries.setName("Force");
        forceSeries.getData().add(new XYChart.Data<>(String.valueOf(forceIndex), Math.random()));
        forceIndex++;
    }

    @FXML
    private void onUpButtonClick() {
        // Hoch-Button-Logik hier
    }

    @FXML
    private void onDownButtonClick() {
        // Runter-Button-Logik hier
    }

    @FXML
    private void onCenterButtonClick() {
        // Zum Mittelpunkt-Button-Logik hier
    }

    @FXML
    private void onCalibrateButtonClick() {
        // Kalibrierungs-Button-Logik hier
    }

    @FXML
    private void onSelectDriveButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Drive");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            System.out.println("Drive selected: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void onChangeMaterialButtonClick() {
        // Materialien ändern-Button-Logik hier
    }
}
