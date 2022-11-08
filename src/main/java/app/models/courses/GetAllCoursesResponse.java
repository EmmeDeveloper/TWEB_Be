package app.models.courses;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GetAllCoursesResponse {
    @NonNull private List<Course> Courses;
}
