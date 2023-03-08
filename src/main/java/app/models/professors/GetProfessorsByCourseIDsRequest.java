package app.models.professors;

import javafx.util.Pair;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class GetProfessorsByCourseIDsRequest {
    private List<String> courseIDs;

    public Pair<Boolean, String> IsValid() {
        if (courseIDs == null)
            return new Pair<>(false, "courseIDs cannot be null");
        return new Pair<>(true, "");
    }

    public GetProfessorsByCourseIDsRequest(String[] ids) {
        if (ids == null) {
            courseIDs = new ArrayList<>();
            return;
        }
        courseIDs = Stream.of(ids).collect(Collectors.toList());
    }
}
