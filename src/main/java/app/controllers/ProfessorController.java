package app.controllers;

import app.exceptions.NotAuthorizedException;
import app.handlers.ProfessorHandler;
import app.handlers.RepetitionHandler;
import app.handlers.TeachingHandler;
import app.handlers.UserHandler;
import app.helpers.JsonHelper;
import app.helpers.ResponseHelper;
import app.models.professors.AddProfessorRequest;
import app.models.professors.GetAllProfessorsResponse;
import lombok.var;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.commons.Constants.Features.*;


@WebServlet(name = "ProfessorController", value = {"/professors", "/professors/course"})
public class ProfessorController extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (!processRequest(req, resp)) return;
    try {
      var professorHandler = ProfessorHandler.getInstance();
      var professors = professorHandler.GetAllProfessors();
      ResponseHelper.ReturnOk(resp, new GetAllProfessorsResponse(professors));
    } catch (Exception e) {
      e.printStackTrace();
      ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (!processRequest(req, resp)) return;
    try {
      switch (req.getServletPath()) {
        case "/professors":
          AddProfessorRequest request = JsonHelper.FromJsonRequest(req, AddProfessorRequest.class);
          var professor = ProfessorHandler.getInstance().AddProfessor(request);
          ResponseHelper.ReturnOk(resp, professor);
          break;
        case "/professors/course":
          var profId = req.getParameter("professorId");
          var courseId = req.getParameter("courseId");
          if (profId == null || courseId == null) {
            ResponseHelper.ReturnErrorStatus(resp, 400, "Invalid parameters");
            return;
          }
          TeachingHandler.getInstance().AddTeaching(profId, courseId);
          ResponseHelper.ReturnOk(resp);
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (!processRequest(req, resp)) return;
    try {
      switch (req.getServletPath()) {
        case "/professors":
          var profId = req.getParameter("id");
          if (profId == null) {
            ResponseHelper.ReturnErrorStatus(resp, 400, "Invalid parameters");
            return;
          }
          ProfessorHandler.getInstance().DeleteProfessor(profId);
          ResponseHelper.ReturnOk(resp);
          break;
        case "/professors/course":
          var proffId = req.getParameter("professorId");
          var courseId = req.getParameter("courseId");
          if (proffId == null || courseId == null) {
            ResponseHelper.ReturnErrorStatus(resp, 400, "Invalid parameters");
            return;
          }
          TeachingHandler.getInstance().DeleteTeachingsOfProfessor(proffId);
          RepetitionHandler.getInstance().CancelRepetitionsOfProfessorForCourse(proffId, courseId);
          ResponseHelper.ReturnOk(resp);
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
    }
  }

  protected boolean processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      var userHandler = UserHandler.getInstance();
      String feature = "";
      switch (req.getMethod()) {
        case "GET":
          if (req.getServletPath().equals("/professors"))
            feature = PROFESSOR_GET_ALL;
          break;
        case "POST":
          switch (req.getServletPath()) {
            case "/professors":
              feature = PROFESSOR_ADD;
              break;
            case "/professors/course":
              feature = TEACHING_ADD;
              break;
          }
          break;

        case "DELETE":
          switch (req.getServletPath()) {
            case "/professors":
              feature = PROFESSOR_DELETE;
              break;
            case "/professors/course":
              feature = TEACHING_DELETE;
              break;
          }
          break;
      }
      if (!userHandler.IsAuthorized(feature, req.getSession()))
        throw new NotAuthorizedException("Not authorized");

    } catch (NotAuthorizedException e) {
      ResponseHelper.ReturnErrorStatus(resp, 401, e.getMessage());
      return false;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return true;
  }
}
