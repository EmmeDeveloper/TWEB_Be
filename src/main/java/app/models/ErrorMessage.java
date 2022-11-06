package app.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorMessage {
    @NonNull private String Message;
}
