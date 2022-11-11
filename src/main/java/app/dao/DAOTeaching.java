package app.dao;

import app.models.professors.Professor;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOTeaching extends DAOBase {

    private static final String GET_PROFESSORS_FROM_COURSE_IDS_QUERY = "Select * from professors where ID in (Select IDProfessor from teachings where IDCourse in (?));";

    public DAOTeaching() {
        super();
    }

    public List<Professor> GetProfessorsByCourses(List<String> courseIDs) throws Exception {
        var profs = new ArrayList<Professor>();
        var ids = courseIDs
                .stream()
                .map(id -> "'" + id + "'")
                .collect(Collectors.toList());
        String IDs = "(" + String.join(",", ids) + ")";

        var result = super.executeQuery(GET_PROFESSORS_FROM_COURSE_IDS_QUERY, IDs);
        if (result != null) {
            while (result.next()) {
                profs.add(new Professor(
                        result.getString("ID"),
                        result.getString("name"),
                        result.getString("surname")
                ));
            }
        }
        return profs;
    }

}
