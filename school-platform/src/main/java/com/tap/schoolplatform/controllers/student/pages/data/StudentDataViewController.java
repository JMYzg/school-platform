package com.tap.schoolplatform.controllers.student.pages.data;

import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class StudentDataViewController {
    @FXML private ImageView studentPhoto;
    @FXML private Label
            lastName,
            name,
            id,
            degree,
            semester,
            group,
            shift,
            gender,
            telephone,
            email,
            street,
            colony,
            pc,
            city,
            state,
            country;

    Student currentStudent = (Student) LoginService.getCurrentUser();

    @FXML private void initialize() {
        studentPhoto.setImage(currentStudent.getProfilePicture());

        lastName.setText(currentStudent.getLastName());
        name.setText(currentStudent.getName());

        id.setText(currentStudent.getID());

        degree.setText(currentStudent.getDegree().toString());
        semester.setText(currentStudent.getGroup().getSemester().toString());
        group.setText(currentStudent.getGroup().toString());
        shift.setText(currentStudent.getGroup().getShift().toString());

        gender.setText(currentStudent.getGender().toString());

        telephone.setText(currentStudent.getPhone());
        email.setText(currentStudent.getEmail());

        street.setText(currentStudent.getAddress().getStreet());
        colony.setText(currentStudent.getAddress().getColony());
        pc.setText(Integer.toString(Integer.parseInt(currentStudent.getAddress().getPostalCode())));
        city.setText(currentStudent.getAddress().getCity());
        state.setText(currentStudent.getAddress().getState());
        country.setText(currentStudent.getAddress().getCountry());
    }
}
