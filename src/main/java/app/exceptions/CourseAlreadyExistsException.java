package app.exceptions;

public class CourseAlreadyExistsException extends Exception{
    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}
