package app.handlers;

import app.dao.DAOCourse;
import app.models.users.AddUserRequest;
import app.models.users.LoginRequest;
import app.models.users.User;

import javax.servlet.http.HttpSession;

public class UserHandler implements IUserHandler {

//    private static DAOUsers _dao;
    private static UserHandler instance;

    // Costruttore privato in quanto instanza singleton
//    private UserHandler() {
//        _dao = new DAOUsers();
//    }

    public UserHandler getInstance() {
        if (instance == null) {
            // synchronized rende threadsafe la creazione dell'instanza
            synchronized (UserHandler.class) {
                if (instance == null){
                    instance = new UserHandler();
                }
            }
        }
        return instance;
    }

    public User AddUser(AddUserRequest request) throws Exception {
        return null;
    }

    public User Login(LoginRequest request, HttpSession session) {
        return null;
    }

    public boolean IsAuthorized(HttpSession session) {
        return false;
    }

    public boolean IsAuthorized(User user) {
        return false;
    }
}
