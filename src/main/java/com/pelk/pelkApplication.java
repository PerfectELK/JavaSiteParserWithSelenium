package com.pelk;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class pelkApplication extends Application {

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.setTitle("Парсер сайтов (v 0.2)");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root,536,526));
        primaryStage.show();

    }

    public static void l(String args []){
        Application.launch(args);
    }

}
