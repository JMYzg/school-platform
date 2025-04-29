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
import java.util.HashMap;
import java.util.Map;

public class TeacherHomeworkController extends TeacherViewPage {

    public static final String PATH = "/views/teacher-views/teacher-option-homework-view.fxml";

    @FXML private Button addHomeworkButton;
    @FXML private VBox homeworkViewsContainer;
    public Subject subject;

    //
    private final Map<Assignment, TeacherHomeworkContainerController>
    conatinerMap = new HashMap<>();
    //

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    @FXML private void addHomework() throws IOException {
//      loadNewView(event, TeacherHomeworkNewController.PATH, "Create new assignment");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherHomeworkNewController.PATH));
        Parent root = loader.load();
        TeacherHomeworkNewController controller = loader.getController();
        controller.setHomeworkViewContainer(homeworkViewsContainer);
        //
        controller.setContainerMap(conatinerMap);

        homeworkViewsContainer.getProperties().put("Parent controller", this);
        //

        Stage stage = new Stage();
        stage.setTitle("Add Homework");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public Map<Assignment, TeacherHomeworkContainerController> getContainerMap(){
        return conatinerMap;
    }

    //Sin utilidad, pero espero que De aqui se pueda optener el Container original que guardara all.
    public VBox getHomeworkViewsContainer() {
        return homeworkViewsContainer;
    }
}
