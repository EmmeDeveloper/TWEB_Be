package app.models.professors;

import javafx.util.Pair;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GetProfessorsByCourseIDsRequest {

    private List<String> courseIDs;

    public Pair<Boolean, String> IsValid() {
        if (courseIDs == null)
            return new Pair<>(false, "courseIDs cannot be null");
        return new Pair<>(true, "");
    }
}
