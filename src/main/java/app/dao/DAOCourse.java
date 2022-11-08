package app.dao;

import app.models.courses.Course;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

public class DAOCourse extends DAOBase {

    private static final String INSERT_COURSE_QUERY = "INSERT INTO courses (ID, title) VALUES ('?','?')";
    private static final String GET_ALL_COURSES_QUERY = "SELECT * FROM courses ORDER BY title ASC";
    private static final String GET_COURSE_BY_TITLE_QUERY = "SELECT * FROM courses WHERE title = '?' LIMIT 1";
    private static final String GET_COURSE_BY_ID_QUERY = "SELECT * FROM courses WHERE ID = '?' LIMIT 1";
    private static final String UPDATE_COURSE_TITLE_BY_ID_QUERY = "UPDATE courses SET title = '?' WHERE ID = '?'";
    private static final String DELETE_COURSE_QUERY = "DELETE FROM courses WHERE ID = '?'";

    public DAOCourse() {
        super();
    }


    public Course GetCourseByTitle(String title) throws Exception {
        return GetCourse(GET_COURSE_BY_TITLE_QUERY, title);
    }

    public Course GetCourseByID(String ID) throws Exception {
        return GetCourse(GET_COURSE_BY_ID_QUERY, ID);
    }

    private Course GetCourse(String query, Object... args) throws Exception {
        Course currentCourse = null;
        var result = super.executeQuery(query, args);
        if (result != null) {
            while (result.next()) {
                currentCourse = new Course(
                        result.getString("ID"),
                        result.getString("title")
                );
            }
        }
        return currentCourse;
    }

    public List<Course> GetCourses() throws Exception {
        var list = new ArrayList<Course>();
        var result = super.executeQuery(GET_ALL_COURSES_QUERY);
        if (result != null) {
            while (result.next()) {
                list.add(new Course(
                        result.getString("ID"),
                        result.getString("title")
                ));
            }
        }
        return list;
    }

    ///
    /// Returns if course was insert correctly
    ///
    public boolean InsertCourse(String ID, String title) throws Exception {
        // MM-NOTA: Usiamo un guid al posto degli id e mettiamo sul db PK sul nome corso,
        // risparmiamo query e controlli facendo fare il tutto al db
        int addRows = super.executeUpdateQuery(
                INSERT_COURSE_QUERY,
                ID,
                title
        );

        return addRows > 0;
    }

    public boolean UpdateCourse(String ID, String title) throws Exception {
        int editedRows = super.executeUpdateQuery(
                UPDATE_COURSE_TITLE_BY_ID_QUERY,
                title,
                ID
        );

        return editedRows > 0;
    }

    public boolean DeleteCourse(String ID) throws Exception {
        int editedRows = super.executeUpdateQuery(
                DELETE_COURSE_QUERY,ID
        );
        return editedRows > 0;
    }
}
