package app.models.courses;

import javafx.util.Pair;
import lombok.Data;
import lombok.NonNull;

@Data
public class AddCourseRequest {
    @NonNull private String Name;

    public Pair<Boolean, String> IsValid() {
        if (Name == null || Name.isEmpty())
            return new Pair<>(false, "Name cannot be null or empty");
        return new Pair<>(true, "");
    }
}
