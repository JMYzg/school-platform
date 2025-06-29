//package com.tap.schoolplatform.controllers.teacher.pages.homework;
//
//
//import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
//import com.tap.schoolplatform.models.academic.Group;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TeacherHomeworkController extends TeacherViewPage {
//
//    public static final String PATH = "/views/teacher-views/teacher-option-homework-view.fxml";
//
//    @FXML private Button addHomeworkButton;
//    @FXML private VBox homeworkViewsContainer;
//
//
//    private final Map<Assignment, TeacherHomeworkContainerController>
//    conatinerMap = new HashMap<>();
//
//
//    //
//    @FXML
//    private void initialize() {
//        homeworkViewsContainer.getProperties().put("parentController", this);
//        loadExistingAssignments();
//    }
//
//    private void loadExistingAssignments() {
//        if (subject != null) {
//            for(Unit unit : subject.getMemberships()){
//                for(Assignment assignment : unit.getAssignments()){
//                    //FXML CONTAINERS re
//                    addAssignmentView(assignment);
//                }
//            }
//        }
//    }
//
//    private void addAssignmentView(Assignment assignment) {
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherHomeworkContainerController.CONTAINER_PATH));
//            Node taskView = loader.load();
//            TeacherHomeworkContainerController controller = loader.getController();
//            controller.setAssignment(assignment);
//            controller.setTitle(assignment.getTitle());
//            controller.setDueDate(assignment.getDeadline());
//            controller.setCreationDate(assignment.getCreationDate());
//
//            homeworkViewsContainer.getChildren().add(taskView);
//            conatinerMap.put(assignment, controller);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//     //
//
//    public void setSubject(Group subject){
//        this.subject = subject;
//    }
//
//    @FXML private void addHomework() throws IOException {
////      loadNewView(event, TeacherHomeworkNewController.PATH, "Create new assignment");
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherHomeworkNewController.PATH));
//        Parent root = loader.load();
//        TeacherHomeworkNewController controller = loader.getController();
//        controller.setHomeworkViewContainer(homeworkViewsContainer);
//        //
//        controller.setContainerMap(conatinerMap);
//
////        homeworkViewsContainer.getProperties().put("parentController", this);
//        //
//
//        Stage stage = new Stage();
//        stage.setTitle("Add Homework");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//    public Map<Assignment, TeacherHomeworkContainerController> getContainerMap(){
//        return conatinerMap;
//    }
//
////    //Sin utilidad, pero espero que De aqui se pueda optener el Container original que guardara all.
////    public VBox getHomeworkViewsContainer() {
////        return homeworkViewsContainer;
////    }
//}
