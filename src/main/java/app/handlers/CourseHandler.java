package app.handlers;

import app.dao.DAOBase;
import app.dao.DAOCourse;
import app.models.Course;

import javax.servlet.ServletContext;
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

    public static CourseHandler getInstance() throws Exception {
        if (_context == null)
            throw new Exception("Context not provided, missing call to @Init method?");
        return (CourseHandler) _context.getAttribute(HANDLER_KEY);
    }

    public Course AddCourse(String title) throws Exception {

        Course c = new Course(
                UUID.randomUUID().toString(),
                title
        );

        if (_dao.insertCourse(c)) {
            return c;
        }
        // TODO: Creare eccezione corretta
        throw new Exception("Cannot insert course: Unhandled error ");
    }

//    public static ArrayList<Corso> getCorsi() {
//        startConnection();
//        ArrayList<Corso> list = new ArrayList<>();
//        try {
//            Statement stx = conn.createStatement();
//            ResultSet result = stx.executeQuery("SELECT * FROM corso ORDER BY titolo ASC");
//            while(result.next()) {
//                list.add(new Corso(result.getInt("id_corso"), result.getString("titolo")));
//            }
//        } catch (SQLException exc) {
//            System.out.println(exc.getMessage());
//        }
//        endConnection();
//        return list;
//    }


}
