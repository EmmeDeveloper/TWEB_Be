package app.handlers;

import app.dao.DAOCourse;
import app.exceptions.CourseAlreadyExistsException;
import app.exceptions.CourseNotFoundException;
import app.models.courses.AddCourseRequest;
import app.models.courses.Course;
import lombok.var;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.UUID;

interface ICourseHandler {
    Course AddCourse(AddCourseRequest request) throws Exception;

    List<Course> GetAllCourses() throws Exception;

    void DeleteCourse(String ID) throws Exception;

    Course GetCourseByID(String ID) throws Exception;
}

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

    public static CourseHandler getInstance() {
        if (_context == null)
            throw new RuntimeException("Context not provided, missing call to @Init method?");
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
        var course = _dao.GetCourseByID(ID);
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        var res = _dao.DeleteSoftCourse(ID);
        if (!res) throw new Exception("Cannot delete course: Unhandled error ");

        var teachHandler = TeachingHandler.getInstance();
        teachHandler.DeleteTeachingsOfCourse(ID);

        var repetitionHandler = RepetitionHandler.getInstance();
        repetitionHandler.CancelRepetitionsOfCourse(ID);
    }

    @Override
    public Course GetCourseByID(String ID) throws Exception {
        var course = _dao.GetCourseByID(ID);
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        return course;
    }


}
