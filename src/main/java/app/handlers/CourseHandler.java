package app.handlers;

import app.dao.DAOCourse;
import app.exceptions.CourseAlreadyExistsException;
import app.exceptions.CourseNotFoundException;
import app.models.courses.AddCourseRequest;
import app.models.courses.Course;
import app.models.courses.EditCourseRequest;
import lombok.var;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CourseHandler implements ICourseHandler {

    private static final String HANDLER_KEY = "#courseHandler";
    private static DAOCourse _dao;
    private static ServletContext _context;


    // Costruttore priavato in quanto instanza singleton
    private CourseHandler() {}

    public static void Init(ServletContext context) {
        _dao = new DAOCourse();
        _context = context;
        _context.setAttribute(HANDLER_KEY, new CourseHandler());
    }

    public static CourseHandler getInstance() throws ServletException {
        if (_context == null)
            throw new ServletException("Context not provided, missing call to @Init method?");
        return (CourseHandler) _context.getAttribute(HANDLER_KEY);
    }

    public Course AddCourse(AddCourseRequest request) throws Exception {

        var course = _dao.GetCourseByTitle(request.getName());

        if (course != null) {
            throw new CourseAlreadyExistsException("Course with same title already exists");
        }

        Course c = new Course(
                UUID.randomUUID().toString(),
                request.getName()
        );

        if (_dao.InsertCourse(c.getId(), request.getName())) {
            return c;
        }
        throw new Exception("Cannot insert course: Unhandled error ");
    }

    public List<Course> GetAllCourses() throws Exception {
        var courses = _dao.GetCourses();
        return courses;
    }

    public void DeleteCourse(String ID) throws Exception {
        var course = _dao.GetCourseByTitle(ID);
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        var res = _dao.DeleteCourse(ID);
        if (!res) throw new Exception("Cannot delete course: Unhandled error ");
    }

    public void UpdateCourse(EditCourseRequest request) throws Exception {
        var course = _dao.GetCourseByID(request.getID());
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        var res = _dao.UpdateCourse(request.getID(), request.getName());
        if (!res) throw new Exception("Cannot update course: Unhandled error ");
    }


}
