//package com.tap.schoolplatform.controllers.teacher.pages.exam;
//
//import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class TeacherExamContainerController {
//
//    public static String PATH = "/views/teacher-views/teacher-option-exam-container-view.fxml";
//
//    private Exam attachedExam;
//
//    @FXML private Button
//            editButton,
//            gradeButton;
//    public Label
//            titleLabel,
//            statusLabel,
//            deadLineLabel,
//            hourLabel,
//            durationLabel;
//
//    public void editExam() {
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherExamNewController.PATH));
//
//            Parent root = loader.load();
//
//            TeacherExamNewController controller = loader.getController();
//            controller.setExam(TeacherViewPage.subject.getUnit(attachedExam.getUnit().getNumber()).getExam());
//            controller.setController(this);
//
//            Stage stage = new Stage();
//            stage.setTitle("Edit Homework");
//            stage.setScene(new Scene(root));
//
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.showAndWait();
//
//            editContainer();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void openExamGrades() {
//
//    }
//
//    public void setAttachedExam(Exam exam) {
//        attachedExam = exam;
//    }
//
//    private void editContainer() {
//        titleLabel.setText(attachedExam.getTitle());
//        deadLineLabel.setText(attachedExam.getDeadline().toLocalDate().toString());
//        hourLabel.setText(attachedExam.getDeadline().toLocalTime().toString());
//        durationLabel.setText("-");
//    }
//}
