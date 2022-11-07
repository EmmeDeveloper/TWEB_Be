package app.handlers;

import app.models.users.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface IUserHandler {

    public User AddUser(AddUserRequest request, HttpSession session) throws Exception;
    public User Login(LoginRequest request, HttpSession session) throws Exception;
    public void Logout(HttpSession session) throws Exception;
    public boolean IsAuthorized(String feature, HttpSession session);
    public boolean IsAuthorized(String feature, User user);

}
