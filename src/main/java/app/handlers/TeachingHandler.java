package app.handlers;

import app.dao.DAOCourse;
import app.dao.DAOTeaching;
import app.models.professors.Professor;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class TeachingHandler implements ITeachingHandler{

    private static final String HANDLER_KEY = "#teachingHandler";
    private static DAOTeaching _dao;
    private static ServletContext _context;

    private TeachingHandler() {}

    public static void Init(ServletContext context) {
        _dao = new DAOTeaching();
        _context = context;
        _context.setAttribute(HANDLER_KEY, new TeachingHandler());
    }

    public static TeachingHandler getInstance() throws ServletException {
        if (_context == null)
            throw new ServletException("Context not provided, missing call to @Init method?");
        return (TeachingHandler) _context.getAttribute(HANDLER_KEY);
    }



    @Override
    public List<Professor> GetProfessorsByCourseIDS(List<String> IDs) throws Exception {
        if (IDs == null || IDs.isEmpty())
            return new ArrayList<>();

        return _dao.GetProfessorsByCourses(IDs);
    }
}
