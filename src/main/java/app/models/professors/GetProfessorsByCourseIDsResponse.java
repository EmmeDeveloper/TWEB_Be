package app.models.professors;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GetProfessorsByCourseIDsResponse {

    @NonNull private List<Professor> professors;

}
