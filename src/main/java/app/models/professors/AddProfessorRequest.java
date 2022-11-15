package app.models.professors;

import app.commons.Constants;
import javafx.util.Pair;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class AddProfessorRequest {
	@Getter @Setter @NonNull
	private String Name;
	@Getter @Setter @NonNull
	private String Surname;

	public Pair<Boolean, String> IsValid() {
		if (Name == null || Name.isEmpty())
			return new Pair<>(false, "Name cannot be null or empty");

		if (Surname == null || Surname.isEmpty())
			return new Pair<>(false, "Surname cannot be null or empty");

		return new Pair<>(true, "");
	}
}
