package app.controllers;

import app.exceptions.UserAlreadyExistsException;
import app.exceptions.NotAuthorizedException;
import app.exceptions.UserNotFoundException;
import app.handlers.UserHandler;
import app.helpers.JsonHelper;
import app.helpers.ResponseHelper;
import app.models.users.AddUserRequest;
import app.models.users.LoginRequest;
import app.models.users.LoginResponse;
import app.models.users.User;
import javafx.util.Pair;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserController", value = {"/login", "/signup", "/logout"})
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            var userHandler = UserHandler.getInstance();
            String path = req.getServletPath() != null ? req.getServletPath() : "";

            Pair<Boolean, String> valid;
            User user;

            switch (path) {
                case "/login":
                    LoginRequest request = JsonHelper.FromJsonRequest(req, LoginRequest.class);
                    valid = request.IsValid();
                    if (!valid.getKey()) {
                        ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
                        return;
                    }

                    user = userHandler.Login(request, req.getSession());
                    ResponseHelper.ReturnOk(resp, new LoginResponse(user));
                    break;

                case "/signup":
                    AddUserRequest signReq = JsonHelper.FromJsonRequest(req, AddUserRequest.class);
                    valid = signReq.IsValid();
                    if (!valid.getKey()) {
                        ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
                        return;
                    }
                    user = userHandler.AddUser(signReq, req.getSession());
                    ResponseHelper.ReturnOk(resp, new LoginResponse(user));
                    break;

                case "/logout":
                    userHandler.Logout(req.getSession());
                    resp.setStatus(200);
                    break;
            }

        }
        catch (NotAuthorizedException e) {
            ResponseHelper.ReturnErrorStatus(resp, 401, e.getMessage());
        }
        catch (UserNotFoundException e) {
            ResponseHelper.ReturnErrorStatus(resp, 404, e.getMessage());
        }
        catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            ResponseHelper.ReturnErrorStatus(resp, 409, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
        }
    }
}
