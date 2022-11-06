package app.controllers;

import app.handlers.UserHandler;
import app.helpers.JsonHelper;
import app.helpers.ResponseHelper;
import app.models.users.LoginRequest;
import app.models.users.LoginResponse;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserController", value = {"/login", "/signup", "/logout"})
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            var userHandler = UserHandler.getInstance();
            String path = req.getServletPath() != null ? req.getServletPath() : "";

            switch (path) {
                case "/login":
                    LoginRequest request = JsonHelper.FromJsonRequest(req, LoginRequest.class);
                    if (request.Account == null || request.Account.isEmpty()) {
                        ResponseHelper.ReturnErrorStatus(resp, 401, "Account cannot be null or empty");
                        return;
                    }
                    if (request.Password == null || request.Password.isEmpty()) {
                        ResponseHelper.ReturnErrorStatus(resp, 401, "Password cannot be null or empty");
                        return;
                    }
                    var user = userHandler.Login(request, req.getSession());
                    if (user != null) {
                        var logResp = new LoginResponse(user);
                        ResponseHelper.ReturnOk(resp, logResp);
                        return;
                    }



                    ResponseHelper.ReturnErrorStatus(resp, 404, "Non esiste il tuo cazzo di nome nel db");
                    break;

                case "/signup":
                    break;

                case "/logout":
                    break;

                default:
                    return; // TODO: Return 404
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
