package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import com.tap.schoolplatform.controllers.teacher.pages.homework.TeacherHomeworkNewController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static com.tap.schoolplatform.controllers.ViewController.loadNewView;

public class TeacherExamController extends TeacherViewPage {

    public static final String PATH = "/views/teacher-views/teacher-option-exam-view.fxml";

    @FXML Button createNewExamButton;

    @FXML VBox examViewsContainer;

    @FXML AnchorPane anchorPaneExamContainer;

    @FXML private void createNewExam(ActionEvent event) throws IOException {
//        loadNewView(event, TeacherExamNewController.PATH, "Create new exam");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherExamNewController.PATH));
        Parent root = loader.load();
        TeacherExamNewController controller = loader.getController();
        controller.setExamContainer(examViewsContainer);

        Stage stage = new Stage();
        stage.setTitle("Add/Edit Exam");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
