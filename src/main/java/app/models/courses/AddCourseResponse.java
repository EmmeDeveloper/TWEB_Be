package app.models.courses;


import lombok.Data;
import lombok.NonNull;

@Data
public class AddCourseResponse {
    @NonNull private Course Course;
}
