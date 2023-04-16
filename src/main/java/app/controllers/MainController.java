package app.controllers;

import app.handlers.*;
import app.helpers.JsonHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MainController", value = "/main", loadOnStartup = 1, asyncSupported = true)
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

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/index.html").forward(req, resp);
  }

  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/json");
    resp.setStatus(200);
    PrintWriter out = resp.getWriter();
    out.println("{ \"message\": \"Hello World!\" }");
  }
}
