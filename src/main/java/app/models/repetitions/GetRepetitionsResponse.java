package app.models.repetitions;

import app.models.courses.Course;
import app.models.professors.Professor;
import app.models.users.User;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class GetRepetitionsResponse {

  private List<FullRepetition> repetitions;

  @Data
  public static class FullRepetition {
    private String ID;
    private String IDCourse;
    private LocalDate date;
    private Integer time;
    private String status;
    private String note;
    private Professor professor;
    private User user;
  }
}
