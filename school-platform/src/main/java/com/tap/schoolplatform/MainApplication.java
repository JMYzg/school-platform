package com.tap.schoolplatform;

//import com.tap.schoolplatform.utils.SharedData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        SharedData.getInstance().initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/views/new-interface/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}