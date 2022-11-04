package app.controllers;

import app.dao.DAOBase;
import app.dao.DAOUser;
import app.handlers.CourseHandler;
import app.handlers.UserHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "MainController", value = "/", loadOnStartup = 1, asyncSupported = true)
public class MainController extends HttpServlet {

    public class InitCounter extends HttpServlet {

        @Override
        public void init(ServletConfig config) {
            CourseHandler.Init(getServletContext());
            UserHandler.Init(getServletContext());
        }
    }
}
