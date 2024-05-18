package dn.ftc.javafx;

import dn.ftc.util.loging.Logger;
import dn.ftc.util.Ressources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private final Map<String, Scene> sceneMap = new HashMap<>();
    private final Stage rootStage;
    private final int width;
    private final int height;

    public SceneManager(Stage rootStage, int width, int height) {
       this.rootStage = rootStage;
       this.width = width;
       this.height = height;
    }

    public boolean changeScene(String sceneId) {
        Scene scene = sceneMap.get(sceneId);
        if(scene == null) {
            Logger.error("Scene with id: " + sceneId + " was not found!");
            return false;
        }
        rootStage.setScene(scene);
        Logger.debug("Scene set: " + sceneId);
        return true;
    }

    public void loadScenes(String path) throws IOException, URISyntaxException {
        path = path.replace("/", "\\");
        Ressources.stream(path)
                .filter(res -> Ressources.getName(res).endsWith(".fxml"))
                .forEach(res -> {
                    try {
                        String id = res.getFileName().toString();
                        id = id.substring(0, id.length() - 5);
                        FXMLLoader fxmlLoader = new FXMLLoader(res.toUri().toURL());
                        Scene scene = new Scene(fxmlLoader.load(), width, height);
                        sceneMap.put(id, scene);
                        Logger.debug("Loaded scene: " + id);
                    } catch (IOException e) {
                        Logger.error(e);
                    }
                });
    }



}
