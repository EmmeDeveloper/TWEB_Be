package app.dao;

import app.models.repetitions.Repetition;
import lombok.var;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DAORepetition extends DAOBase {

  public static final String GetRepetitionsByCourseIds = "SELECT repetitions.*\n" +
          "FROM repetitions\n" +
          "JOIN courses ON repetitions.IDCourse = courses.ID\n" +
          "JOIN professors ON repetitions.IDProfessor = professors.ID\n" +
          "JOIN users ON repetitions.IDUser = users.ID\n" +
          "WHERE courses.ID IN ?\n" +
          "AND repetitions.date BETWEEN '?' AND '?'\n";

  public DAORepetition() {
    super();
  }

  public List<Repetition> GetRepetitionsByCourseIds(List<String> courseIds, Date from, Date to) throws Exception {

    var repetitions = new ArrayList<Repetition>();

    var ids = courseIds
            .stream()
            .map(id -> "'" + id + "'")
            .collect(Collectors.toList());
    String IDs = "(" + String.join(",", ids) + ")";

    var result = super.executeQuery(GetRepetitionsByCourseIds, IDs, from, to);
    if (result != null) {
      while (result.next()) {
        repetitions.add(
                new Repetition(
                        result.getString("ID"),
                        result.getString("IDUser"),
                        result.getString("IDCourse"),
                        result.getString("IDProfessor"),
                        result.getDate("date").toLocalDate(),
                        result.getInt("time"),
                        result.getString("status"),
                        result.getString("note")
                )
        );
      }
    }
    return repetitions;
  }

}
