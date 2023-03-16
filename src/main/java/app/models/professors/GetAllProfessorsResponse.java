package app.models.professors;

import app.models.courses.Course;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GetAllProfessorsResponse {
  @NonNull
  private List<Professor> Professors;
}
