package app.models.repetitions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Repetition {
  private String ID;
  private String IDUser;
  private String IDCourse;
  private String IDProfessor;
  private LocalDate date;
  private Integer time;
  private String status;
  private String note;
}

