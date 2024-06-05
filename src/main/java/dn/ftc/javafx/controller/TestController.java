package dn.ftc.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TestController {

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    private ChoiceBox<String> materialChoice;

    @FXML
    private TextField strokesField;
    @FXML
    private TextField forceField;
    @FXML
    private TextField extensionField;

    @FXML
    private Label materialLabel;
    @FXML
    private Label strokesLabel;
    @FXML
    private Label forceLabel;
    @FXML
    private Label extensionLabel;
    @FXML
    private Label directionLabel;

    @FXML
    private CheckBox upCheckbox;
    @FXML
    private CheckBox downCheckbox;

    @FXML
    protected void onStartButtonClick() {
        System.out.println("START");
    }

}
