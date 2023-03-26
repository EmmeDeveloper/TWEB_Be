package app.models.repetitions;

import javafx.util.Pair;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddRepetitionRequest {
  @Getter @Setter
  private String IDCourse;
  @Getter @Setter
  private String IDProfessor;
  @Getter @Setter
  private LocalDate date;

  @Getter @Setter
  private String note;
  @Getter @Setter
  private int time;

    public Pair<Boolean, String> IsValid() {
      if (IDCourse == null || IDCourse.isEmpty())
        return new Pair<>(false, "IDCourse cannot be null or empty");

      if (IDProfessor == null || IDProfessor.isEmpty())
        return new Pair<>(false, "IDProfessor cannot be null or empty");

      if (date == null)
        return new Pair<>(false, "Date cannot be null");

      // Check if current date is after repetition date
      if (date.isBefore(LocalDate.now()))
        return new Pair<>(false, "Cannot add repetition in the past");

      // Check if time is before current hour
      if (date.isEqual(LocalDate.now())) {
        if (time < LocalDateTime.now().getHour())
          return new Pair<>(false, "Cannot add repetition in the past");

        if (time == LocalDateTime.now().getHour())
          return new Pair<>(false, "Cannot add repetition during the same hour");
      }

      return new Pair<>(true, "");
    }
}
