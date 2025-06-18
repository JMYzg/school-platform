package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherExamController extends TeacherViewPage {

    public static final String PATH = "/views/teacher-views/teacher-option-exam-view.fxml";
    //
    public static ObservableList<Exam> exams;
    //

    @FXML Button createNewExamButton;

    @FXML VBox examViewsContainer;

    @FXML AnchorPane anchorPaneExamContainer;

    public void initialize() {
        //
        exams = subject.getAllExams();

        for (Exam exam : exams) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(TeacherExamContainerController.PATH));
                Node node = fxmlLoader.load();
                TeacherExamContainerController controller = fxmlLoader.getController();
                controller.titleLabel.setText(exam.getTitle());
                controller.deadLineLabel.setText(exam.getDeadline().toLocalDate().toString());
                controller.hourLabel.setText(exam.getDeadline().toLocalTime().toString());
                controller.durationLabel.setText("-");
                controller.setAttachedExam(exam);
                examViewsContainer.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //
    }

    @FXML private void createNewExam() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherExamNewController.PATH));
        Parent root = loader.load();
        TeacherExamNewController controller = loader.getController();
        controller.setVBox(this.examViewsContainer);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Exam");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
