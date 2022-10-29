package app.handlers;

import app.models.users.*;

import javax.servlet.http.HttpSession;

public interface IUserHandler {

    public User AddUser(AddUserRequest request) throws Exception;
    public User Login(LoginRequest request, HttpSession session);
    public boolean IsAuthorized(HttpSession session);
    public boolean IsAuthorized(User user);

}
