package dn.ftc.javafx;

import javafx.application.Application;
import javafx.stage.Stage;

public class FatigueTestController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager manager = new SceneManager(primaryStage, 1270, 920);
        manager.loadScenes("/scene");
        primaryStage.setTitle("Fatigue Test Controller");
        manager.changeScene("00_Test");
        primaryStage.show();
    }
}
