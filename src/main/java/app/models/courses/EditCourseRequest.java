package app.models.courses;

import javafx.util.Pair;
import lombok.Data;
import lombok.NonNull;

@Data
public class EditCourseRequest {

    @NonNull private String ID;
    @NonNull private String Name;

    public Pair<Boolean, String> IsValid() {
        if (ID == null || ID.isEmpty())
            return new Pair<>(false, "ID cannot be null or empty");

        if (Name == null || Name.isEmpty())
            return new Pair<>(false, "Name cannot be null or empty");
        return new Pair<>(true, "");
    }
}
