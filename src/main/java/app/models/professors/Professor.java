package app.models.professors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Professor {

    private @NonNull String ID;
    private @NonNull String Name;
    private @NonNull String Surname;
}
