package com.tap.schoolplatform;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.enums.Type;
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

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

    public static void dataInjection() throws IOException {
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
        user.setProfilePicture(defaultProfilePicture());
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
        user2.setProfilePicture(defaultProfilePicture());

        Group group = new Group("Advanced Programming Topics", "This group is for advanced programming topics. It is created to test the functionality of the application. It is not intended to be used for any other purpose.");
        group.setColor("#000000");
        Service.add(group);
        Service.add(admin);
        System.out.println("Usuario añadido: " + admin.getEmail());
        Service.add(user);
        System.out.println("Usuario añadido: " + user.getEmail());
        Service.add(user2);
        Membership membership = new Membership(user, group, Role.OWNER);
        Service.add(membership);
        Membership membership2 = new Membership(user2, group, Role.MEMBER);
        Service.add(membership2);
        Group group1 = new Group("Linear Algebra", "This groups was created to test the functionality of the JOIN function. It is not intended to be used for any other purpose.");
        group1.setColor("#00008B");
        Service.add(group1);
        Service.add(admin);
    }

    public static void main(String[] args) throws IOException {
        dataInjection();
        launch();
    }

    public static byte[] defaultProfilePicture() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Image image = new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("/images/DefaultProfilePicture.png")));
//        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
//        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}