package app.dao;

import app.models.professors.Professor;
import javafx.util.Pair;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOTeaching extends DAOBase {

    private static final String GET_PROFESSORS_FROM_COURSE_IDS_QUERY =
            "SELECT " +
                    "courses.ID as 'IDC', " +
                    "professors.ID as 'IDP'\n" +
                    "professors.name as 'PName'\n" +
                    "professors.surname as 'PSurn'\n" +
            "FROM teachings\n" +
            "INNER JOIN courses ON teachings.IDCourse = courses.ID\n" +
            "INNER JOIN professors ON teachings.IDProfessor = professors.ID\n" +
            "WHERE courses.ID IN ?";
    private static final String DELETE_TEACHING_BY_COURSE_ID_QUERY = "DELETE FROM teachings WHERE IDCourse = '?'";

    private static final String DELETE_TEACHING_BY_PROFESSOR_ID_QUERY = "DELETE FROM teachings WHERE IDProfessor = '?'";

    public DAOTeaching() {
        super();
    }

    public List<Pair<String, Professor>> GetProfessorsByCourses(List<String> courseIDs) throws Exception {
        var pairs = new ArrayList<Pair<String, Professor>>();
        var ids = courseIDs
                .stream()
                .map(id -> "'" + id + "'")
                .collect(Collectors.toList());
        String IDs = "(" + String.join(",", ids) + ")";

        var result = super.executeQuery(GET_PROFESSORS_FROM_COURSE_IDS_QUERY, IDs);
        if (result != null) {
            while (result.next()) {
                pairs.add(
                        new Pair<>(result.getString("IDC"),
                                new Professor(
                                        result.getString("IDP"),
                                        result.getString("PName"),
                                        result.getString("PSurn")
                                )
                        )
                );
            }
        }
        return pairs;
    }
    public boolean DeleteTeachingByCourseID(String courseID) throws Exception {
        return super.executeUpdateQuery(DELETE_TEACHING_BY_COURSE_ID_QUERY, courseID) > 0;
    }

    public boolean DeleteTeachingByProfessorID(String professorID) throws Exception {
        return super.executeUpdateQuery(DELETE_TEACHING_BY_PROFESSOR_ID_QUERY, professorID) > 0;
    }
}
