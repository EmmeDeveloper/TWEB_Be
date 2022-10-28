package app.handlers;

import app.models.Course;

public interface ICourseHandler {
    public Course AddCourse(String title) throws Exception;
}
