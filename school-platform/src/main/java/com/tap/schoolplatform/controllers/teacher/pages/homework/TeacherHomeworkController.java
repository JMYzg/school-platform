package com.tap.schoolplatform.controllers.teacher.pages.homework;


import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import javafx.event.ActionEvent;
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

public class TeacherHomeworkController extends TeacherViewPage {

    public static final String PATH = "/views/teacher-views/teacher-option-homework-view.fxml";
    public AnchorPane anchorPaneHomeworkContainer;

    @FXML private Button addHomeworkButton;

    @FXML private VBox homeworkViewsContainer;

    public Subject subject;

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    @FXML private void addHomework() throws IOException {
//        loadNewView(event, TeacherHomeworkNewController.PATH, "Create new assignment");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherHomeworkNewController.PATH));
            Parent root = loader.load();
            TeacherHomeworkNewController controller = loader.getController();
            controller.setHomeworkViewContainer(homeworkViewsContainer);

            Stage stage = new Stage();
            stage.setTitle("Add Homework");
            stage.setScene(new Scene(root));
            stage.show();
    }


//    public void updateHomeworkContainer(Assignment assignment) throws IOException {
//        for(Node node : homeworkViewsContainer.getChildren()){
//            if(node instanceof TeacherHomeworkContainerController){
//                TeacherHomeworkContainerController controller = (TeacherHomeworkContainerController) node;
//                if(controller.getAssignment().equals(assignment)){
//                    controller.updateAssignmentView(assignment);
//                    break;
//                }
//            }
//        }
//    }

    public VBox getHomeworkViewsContainer() {
        return homeworkViewsContainer;
    }
}
