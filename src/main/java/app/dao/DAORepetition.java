package app.dao;

import app.models.repetitions.Repetition;
import lombok.var;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DAORepetition extends DAOBase {

  public static final String GET_REPETITIONS_BY_COURSE_IDS_QUERY = "SELECT repetitions.*\n" +
          "FROM repetitions\n" +
          "JOIN courses ON repetitions.IDCourse = courses.ID\n" +
          "JOIN professors ON repetitions.IDProfessor = professors.ID\n" +
          "JOIN users ON repetitions.IDUser = users.ID\n" +
          "WHERE courses.ID IN (?)\n" +
          "AND repetitions.date BETWEEN '?' AND '?'\n" +
          "ORDER BY repetitions.date ASC, repetitions.time ASC";

  public static final String GET_REPETITIONS_BY_USER_ID_QUERY = "SELECT repetitions.*\n" +
          "FROM repetitions\n" +
          "JOIN courses ON repetitions.IDCourse = courses.ID\n" +
          "JOIN professors ON repetitions.IDProfessor = professors.ID\n" +
          "JOIN users ON repetitions.IDUser = users.ID\n" +
          "WHERE users.ID = '?'\n" +
          "AND repetitions.date BETWEEN '?' AND '?'\n" +
          "ORDER BY repetitions.date ASC, repetitions.time ASC";

  public static final String SET_REPETITION_STATUS_AND_NOTE_BY_ID_QUERY = "UPDATE repetitions\n" +
          "SET status = '?', note = '?'\n" +
          "WHERE ID = '?';";

  public static final String SET_REPETITION_STATUS_AND_NOTE_BY_COURSE_ID_QUERY = "UPDATE repetitions\n" +
          "SET status = '?', note = '?'\n" +
          "WHERE IDCourse = '?' AND status = \"pending\";";

  public static final String SET_REPETITION_STATUS_AND_NOTE_BY_PROFESSOR_ID_QUERY = "UPDATE repetitions\n" +
          "SET status = '?', note = '?'\n" +
          "WHERE IDProfessor = '?' AND status = \"pending\";";

  public static final String SET_REPETITION_STATUS_AND_NOTE_BY_PROFESSOR_ID_AND_COURSE_ID_QUERY = "UPDATE repetitions\n" +
          "SET status = '?', note = '?'\n" +
          "WHERE IDProfessor = '?' AND IDCourse = '?'  AND status = \"pending\" ;";

  public static final String ADD_REPETITION_QUERY =
          "INSERT INTO repetitions (ID, IDUser, IDCourse, IDProfessor, date, time, status, note)\n" +
                  "VALUES ('?', '?', '?', '?', '?', '?', '?', '?');";
  public static final String DELETE_REPETITION_BY_ID_QUERY = "DELETE FROM repetitions WHERE ID = '?';";
  public static final String GET_REPETITION_BY_ID_QUERY = "SELECT * FROM repetitions WHERE ID = '?' LIMIT 1";

  public static final String EXISTS_REPETITION_BY_DATE_AND_TIME_AND_COURSE_ID_QUERY =
          "SELECT * FROM repetitions WHERE date = '?' AND time = '?' AND IDCourse = '?' AND status != \"deleted\" LIMIT 1;";

  public DAORepetition() {
    super();
  }

  public List<Repetition> GetRepetitionsByCourseIds(List<String> courseIds, Date from, Date to) throws Exception {
    Object[] params = Stream.concat(
                    Arrays.stream(courseIds.toArray()),
                    Stream.of(from, to)
            )
            .toArray(Object[]::new);
    return GetListRepetitions(GET_REPETITIONS_BY_COURSE_IDS_QUERY, params);
  }

  public List<Repetition> GetRepetitionsByUserID(String userID, Date from, Date to) throws Exception {
    return GetListRepetitions(GET_REPETITIONS_BY_USER_ID_QUERY, userID, from, to);
  }

  private List<Repetition> GetListRepetitions(String query, Object... params) throws Exception {
    var repetitions = new ArrayList<Repetition>();
    var result = super.executeQuery(query, params);
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

  public boolean SetRepetitionsStatusAndNoteByCourseID(String courseID, String status, String note) throws Exception {
    return super.executeUpdateQuery(
            SET_REPETITION_STATUS_AND_NOTE_BY_COURSE_ID_QUERY, status, note, courseID
    ) > 0;
  }

  public boolean SetRepetitionsStatusAndNoteByProfessorID(String professorID, String status, String note) throws Exception {
    return super.executeUpdateQuery(
            SET_REPETITION_STATUS_AND_NOTE_BY_PROFESSOR_ID_QUERY, status, note, professorID
    ) > 0;
  }

  public boolean SetRepetitionsStatusAndNoteByProfessorIDAndCourseID(String professorId, String courseId, String status, String note) {
    return super.executeUpdateQuery(
            SET_REPETITION_STATUS_AND_NOTE_BY_PROFESSOR_ID_AND_COURSE_ID_QUERY, status, note, professorId, courseId
    ) > 0;
  }

  public boolean AddRepetition(Repetition repetition) throws Exception {
    return super.executeUpdateQuery(
            ADD_REPETITION_QUERY,
            repetition.getID(),
            repetition.getIDUser(),
            repetition.getIDCourse(),
            repetition.getIDProfessor(),
            repetition.getDate(),
            repetition.getTime(),
            repetition.getStatus(),
            repetition.getNote()
    ) > 0;
  }

  public boolean SetRepetitionsStatusAndNoteByID(String repetitionID, String status, String note) throws Exception {
    return super.executeUpdateQuery(
            SET_REPETITION_STATUS_AND_NOTE_BY_ID_QUERY, status, note, repetitionID
    ) > 0;
  }

  public boolean DeleteRepetitionByID(String repetitionID) throws Exception {
    return super.executeUpdateQuery(
            DELETE_REPETITION_BY_ID_QUERY, repetitionID
    ) > 0;
  }

  public Repetition GetRepetitionByID(String repetitionID) throws Exception {
    var result = super.executeQuery(GET_REPETITION_BY_ID_QUERY, repetitionID);
    if (result != null && result.next()) {
      return new Repetition(
              result.getString("ID"),
              result.getString("IDUser"),
              result.getString("IDCourse"),
              result.getString("IDProfessor"),
              result.getDate("date").toLocalDate(),
              result.getInt("time"),
              result.getString("status"),
              result.getString("note")
      );
    }
    return null;
  }

  public boolean ExistsRepetitionByDateAndTimeAndCourseID(LocalDate date, int time, String courseID) throws Exception {
    var result = super.executeQuery(
            EXISTS_REPETITION_BY_DATE_AND_TIME_AND_COURSE_ID_QUERY, date, time, courseID
    );
    return result != null && result.next();
  }
}
