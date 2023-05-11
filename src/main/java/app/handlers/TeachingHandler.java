package app.handlers;

import app.dao.DAOTeaching;
import app.exceptions.CourseNotFoundException;
import app.exceptions.ProfessorNotFoundException;
import app.models.professors.Professor;
import lombok.var;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.*;

interface ITeachingHandler {
    Map<String,List<Professor>> GetProfessorsByCourseIDS(List<String> IDs) throws Exception;
    void DeleteTeachingsOfProfessor(String professorID) throws Exception;
    void DeleteTeachingsOfCourse(String courseID) throws Exception;
    void AddTeaching(String professorID, String courseID) throws Exception;
    boolean ExistsTeaching(String courseID, String professorID) throws Exception;
}

public class TeachingHandler implements ITeachingHandler {

    private static final String HANDLER_KEY = "#teachingHandler";
    private static DAOTeaching _dao;
    private static ServletContext _context;

    private TeachingHandler() {}

    public static void Init(ServletContext context) {
        _dao = new DAOTeaching();
        _context = context;
        _context.setAttribute(HANDLER_KEY, new TeachingHandler());
    }

    public static TeachingHandler getInstance() {
        if (_context == null)
            throw new RuntimeException("Context not provided, missing call to @Init method?");
        return (TeachingHandler) _context.getAttribute(HANDLER_KEY);
    }



    @Override
    public Map<String,List<Professor>> GetProfessorsByCourseIDS(List<String> IDs) throws Exception {
        if (IDs == null || IDs.isEmpty())
            return null;

        var pairs = _dao.GetProfessorsByCourses(IDs);
        var map = new HashMap<String, List<Professor>>();

        for (var pair : pairs) {
            if (!map.containsKey(pair.getKey()))
                map.put(pair.getKey(), new ArrayList<>());
            map.get(pair.getKey()).add(pair.getValue());
        }
        return map;
    }

    @Override
    public void DeleteTeachingsOfProfessor(String professorID) throws Exception {
        if (professorID == null || professorID.isEmpty())
            return;
        _dao.DeleteTeachingByProfessorID(professorID);
    }

    @Override
    public void DeleteTeachingsOfCourse(String courseID) throws Exception {
        if (courseID == null || courseID.isEmpty())
            return;
        _dao.DeleteTeachingByCourseID(courseID);
    }

    @Override
    public void AddTeaching(String professorID, String courseID) throws Exception {
        if (professorID == null || professorID.isEmpty())
            return;
        if (courseID == null || courseID.isEmpty())
            return;

        var ids = new ArrayList<>(Collections.singletonList(professorID));
        var professor = ProfessorHandler.getInstance().GetProfessorsByIDs(ids);
        if (professor == null || professor.isEmpty())
            throw new ProfessorNotFoundException("Professor not found");
        var course = CourseHandler.getInstance().GetCourseByID(courseID);
        if (course == null)
            throw new CourseNotFoundException("Course not found");

        if (_dao.ExistsTeaching(courseID, professorID))
            return;

        _dao.AddTeaching(UUID.randomUUID().toString(),courseID, professorID);
    }

    @Override
    public boolean ExistsTeaching(String courseID, String professorID) throws Exception {
        if (professorID == null || professorID.isEmpty())
            return false;
        if (courseID == null || courseID.isEmpty())
            return false;
        return _dao.ExistsTeaching(courseID, professorID);
    }


}
