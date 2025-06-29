package com.tap.schoolplatform;

import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Type;
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.services.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

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
    public static void dataInjection() {
        User admin = new User(
                "Brisa",
                "Bautista",
                "brs_bc@hotmail.com",
                "789",
                "624-160-8038",
                new Address(
                        "Pango St.",
                        "23477",
                        "Cabo Valley",
                        "C.S.L.",
                        "B.C.S.",
                        "Mexico"
                ),
                LocalDate.of(2004, 2, 14),
                Gender.FEMALE,
                Type.ADMIN
        );

        User user = new User(
                "Jeremy",
                "Zarate",
                "jmy_zg@icloud.com",
                "123",
                "624-227-5260",
                new Address(
                        "Sago Palm",
                        "23477",
                        "The Palms",
                        "C.S.L.",
                        "B.C.S.",
                        "Mexico"
                ),
                LocalDate.of(2004, 2, 23),
                Gender.MALE,
                Type.USER
        );
        User user2 = new User(
                "Gary",
                "Juarez",
                "gry_jp@gmail.com",
                "456",
                "624-243-8558",
                new Address(
                        "Dolomite",
                        "23477",
                        "Saint Bernard",
                        "S.J.D.",
                        "B.C.S.",
                        "Mexico"
                ),
                LocalDate.of(2005, 9, 5),
                Gender.MALE,
                Type.USER
        );
        Service.add(admin);
        System.out.println("Usuario añadido: " + admin.getEmail());
        Service.add(user);
        System.out.println("Usuario añadido: " + user.getEmail());
        Service.add(user2);

    }

    public static void main(String[] args) {
        dataInjection();
        launch();

    }
}