package app.controllers;

import app.handlers.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "MainController", value = "/", loadOnStartup = 1, asyncSupported = true)
public class MainController extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    CourseHandler.Init(getServletContext());
    UserHandler.Init(getServletContext());
    TeachingHandler.Init(getServletContext());
    ProfessorHandler.Init(getServletContext());
    RepetitionHandler.Init(getServletContext());
  }
}
