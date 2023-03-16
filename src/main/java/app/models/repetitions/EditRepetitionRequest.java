package app.models.repetitions;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.util.Pair;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class EditRepetitionRequest {
  @Getter @Setter
  private String ID;

  @Getter @Setter
  private String note;

  public Pair<Boolean, String> IsValid() {
    if (ID == null || ID.isEmpty())
      return new Pair<>(false, "ID cannot be null or empty");

    return new Pair<>(true, "");
  }


}
