package app.models.professors;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@Data
public class GetProfessorsByCourseIDsResponse {
    @NonNull private Map<String, List<Professor>> professors;
}
