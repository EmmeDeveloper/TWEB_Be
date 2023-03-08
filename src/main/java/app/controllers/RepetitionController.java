package app.controllers;


import app.commons.Constants;
import app.exceptions.NotAuthorizedException;
import app.handlers.RepetitionHandler;
import app.handlers.UserHandler;
import app.helpers.JsonHelper;
import app.helpers.ResponseHelper;
import app.models.courses.AddCourseRequest;
import app.models.repetitions.AddRepetitionRequest;
import app.models.repetitions.EditRepetitionRequest;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static app.commons.Constants.Features.*;

@WebServlet(name = "RepetitionController", value = {"/repetitions", "/users/repetitions", "/repetitions/all"})
public class RepetitionController extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!processRequest(req, resp)) return;

    var repetitionHandler = RepetitionHandler.getInstance();
    var userHandler = UserHandler.getInstance();
    String path = req.getServletPath() != null ? req.getServletPath() : "";
    switch (path) {
      case "/repetitions":
        try {
          var currentUser = userHandler.GetCurrentUser(req.getSession());
          var repetitions = repetitionHandler.GetRepetitionsByCourseIds(
                  currentUser,
                  Stream.of(req.getParameterValues("courseIDs")).collect(Collectors.toList()),
                  new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startDate")),
                  new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("endDate"))
          );
          ResponseHelper.ReturnOk(resp, repetitions);
        } catch (Exception e) {
          e.printStackTrace();
          ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
        }
        break;
      case "/users/repetitions":
        try {
          var repetitions = repetitionHandler.GetAllRepetitionsByUser(
                  req.getParameter("userID"),
                  new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startDate")),
                  new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("endDate"))
          );
          ResponseHelper.ReturnOk(resp, repetitions);
        } catch (Exception e) {
          e.printStackTrace();
          ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
        }
        break;
      case "/repetitions/all":
        try {
          var repetitions = repetitionHandler.GetRepetitionsForAllUsers(
                  new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startDate")),
                  new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("endDate"))
          );
          ResponseHelper.ReturnOk(resp, repetitions);
        } catch (Exception e) {
          e.printStackTrace();
          ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
        }
        break;
    }

  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!processRequest(req, resp)) return;

    try {
      AddRepetitionRequest request = JsonHelper.FromJsonRequest(req, AddRepetitionRequest.class);
      var valid = request.IsValid();
      if (!valid.getKey()) {
        ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
        return;
      }
      var repetition = RepetitionHandler.getInstance().AddRepetition(request);
      ResponseHelper.ReturnOk(resp, repetition);
    } catch (Exception e) {
      e.printStackTrace();
      ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
    }
  }

  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!processRequest(req, resp)) return;

    try {
      EditRepetitionRequest request = JsonHelper.FromJsonRequest(req, EditRepetitionRequest.class);
      var valid = request.IsValid();
      if (!valid.getKey()) {
        ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
        return;
      }
      RepetitionHandler.getInstance().UpdateRepetition(
              request.getID(),
              req.getParameter("status"),
              request.getNote()
      );
      ResponseHelper.ReturnOk(resp);
    } catch (Exception e) {
      e.printStackTrace();
      ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
    }
  }

  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!processRequest(req, resp)) return;

    try {
      RepetitionHandler.getInstance().DeleteRepetition(req.getParameter("id"));
      ResponseHelper.ReturnOk(resp);
    } catch (Exception e) {
      e.printStackTrace();
      ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
    }
  }

  protected boolean processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      var userHandler = UserHandler.getInstance();
      String path = req.getServletPath() != null ? req.getServletPath() : "";
      String feature = "";
      switch (req.getMethod()) {
        case "GET":
          switch (path) {
            case "/repetitions":
              feature = REPETITIONS_GET_AVAILABLE;
              break;
            case "/users/repetitions":
              feature = REPETITIONS_GET_FOR_USER;
              break;
            case "/repetitions/all":
              feature = REPETITIONS_GET_FOR_ALL_USERS;
              break;
          }
        case "POST":
          if (path.equals("/repetitions"))
            feature = REPETITIONS_RESERVE;
          break;
        case "PUT":
          if (path.equals("/repetitions")) {
            String status = req.getParameter("status");
            if (status != null) {
              switch (status.toLowerCase()) {
                case Constants.RepetitionStatus.PENDING:
                  feature = REPETITIONS_UPDATE_NOTES;
                  break;
                case Constants.RepetitionStatus.DONE:
                  feature = REPETITIONS_SET_DONE;
                  break;
                case Constants.RepetitionStatus.DELETED:
                  feature = REPETITIONS_SET_DELETED;
                  break;
              }
            }
          }
          break;
        case "DELETE":
          if (path.equals("/repetitions"))
            feature = REPETITIONS_DELETE;
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

