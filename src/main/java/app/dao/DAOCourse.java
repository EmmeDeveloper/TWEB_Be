package app.dao;

import app.models.Course;
public class DAOCourse extends DAOBase {

    private static final String INSERT_COURSE_QUERY = "INSERT INTO corso (id_corso, titolo) VALUES ('?','?')";

    public DAOCourse() {
        super();
    }

    ///
    /// Returns if course was insert correctly
    ///
    public boolean insertCourse(Course course) throws Exception {
        // MM-NOTA: Usiamo un guid al posto degli id e mettiamo sul db PK sul nome corso,
        // risparmiamo query e controlli facendo fare il tutto al db
        int addRows = super.executeUpdateQuery(
                INSERT_COURSE_QUERY,
                course.getId(),
                course.getTitle()
        );

        return addRows > 0;
    }

//    public static void deleteCorso(Corso corso) {
//        startConnection();
//        try {
//            Statement stx = conn.createStatement();
//            stx.executeUpdate("DELETE FROM corso WHERE id_corso = '"+ corso.getId() + "'");
//        } catch (SQLException exc) {
//            System.out.println(exc.getMessage());
//        }
//        endConnection();
//    }

}
