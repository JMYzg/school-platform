package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.teacher.pages.homework.TeacherHomeworkNewController;
import com.tap.schoolplatform.models.academic.tasks.Exam;
import com.tap.schoolplatform.models.academic.tasks.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherExamContainerController {

    public static String PATH = "/views/teacher-views/teacher-option-exam-container-view.fxml";

    public Exam asociatedExam;

    @FXML Button
            editButton,
            gradeButton;
    public Label
            examTitleLabel,
            statusLabel,
            dayOfAplicationLabel,
            timeLabel,
            durationLabel;

    public void editExam(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherExamNewController.PATH));
//            TeacherExamNewController.exam = asociatedExam;
            Parent root = loader.load();
//            TeacherExamNewController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Edit Homework");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            examTitleLabel.setText(asociatedExam.getTitle());
            dayOfAplicationLabel.setText(asociatedExam.getDeadline().toLocalDate().toString());
            timeLabel.setText(asociatedExam.getDeadline().toLocalTime().toString());
            durationLabel.setText("-");
            if (TeacherExamController.exams.contains(asociatedExam)) {
                TeacherExamController.exams.remove(asociatedExam);
                TeacherExamController.exams.add(asociatedExam);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openExamGrades(ActionEvent actionEvent) {

    }


}
