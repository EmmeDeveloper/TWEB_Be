package app.models.repetitions;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddRepetitionResponse {
  @NonNull
  private Repetition repetition;
}
