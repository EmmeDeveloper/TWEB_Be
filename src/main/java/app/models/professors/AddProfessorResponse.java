package app.models.professors;


import lombok.Data;
import lombok.NonNull;

@Data
public class AddProfessorResponse {
  @NonNull private Professor Professor;
}
