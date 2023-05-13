package app.models.repetitions;

import app.models.courses.Course;
import app.models.professors.Professor;
import app.models.users.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetRepetitionsResponse {

  private List<FullRepetition> repetitions;

  @Data
  public static class FullRepetition {
    private String ID;
    private LocalDate date;
    private Integer time;
    private String status;
    private String note;
    private Professor professor;
    private User user;
    private Course course;
  }
}
