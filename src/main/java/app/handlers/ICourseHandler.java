package app.handlers;

import app.models.courses.AddCourseRequest;
import app.models.courses.Course;
import app.models.courses.EditCourseRequest;

import java.util.List;

public interface ICourseHandler {
    public Course AddCourse(AddCourseRequest request) throws Exception;

    public List<Course> GetAllCourses() throws Exception;

    public void DeleteCourse(String ID) throws Exception;
    public void UpdateCourse(EditCourseRequest request) throws Exception;
}
