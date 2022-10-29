package app.handlers;

import app.dao.DAOCourse;
import app.models.Course;

import java.util.UUID;

public class CourseHandler implements ICourseHandler {

    private static DAOCourse _dao;
    private static CourseHandler instance;

    // Costruttore priavato in quanto instanza singleton
    private CourseHandler() {
        _dao = new DAOCourse();
    }

    public CourseHandler getInstance() {
        if (instance == null) {
            // synchronized rende threadsafe la creazione dell'instanza
            synchronized (CourseHandler.class) {
                if (instance == null){
                    instance = new CourseHandler();
                }
            }
        }
        return instance;
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
