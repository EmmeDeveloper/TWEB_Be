package app.controllers;

import app.exceptions.CourseAlreadyExistsException;
import app.exceptions.CourseNotFoundException;
import app.exceptions.NotAuthorizedException;
import app.handlers.CourseHandler;
import app.handlers.TeachingHandler;
import app.handlers.UserHandler;
import app.helpers.JsonHelper;
import app.helpers.ResponseHelper;
import app.models.courses.*;
import app.models.professors.GetProfessorsByCourseIDsRequest;
import app.models.professors.GetProfessorsByCourseIDsResponse;
import app.models.users.LoginRequest;
import app.models.users.LoginResponse;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.commons.Constants.Features.*;

@WebServlet(name = "CourseController", value = {"/courses", "/courses/professors"})
public class CourseController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!processRequest(req, resp)) return;

        var courseHandler = CourseHandler.getInstance();
        var teachingHandler = TeachingHandler.getInstance();
        String path = req.getServletPath() != null ? req.getServletPath() : "";
        switch (path) {
            case "/courses":
                try {
                    var courses = courseHandler.GetAllCourses();
                    ResponseHelper.ReturnOk(resp, new GetAllCoursesResponse(courses));
                } catch (Exception e) {
                    e.printStackTrace();
                    ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
                }
                break;

            case "/courses/professors":
                var courses = req.getParameterValues("ids");
                var request = new GetProfessorsByCourseIDsRequest(courses);
                var valid = request.IsValid();
                if (!valid.getKey()) {
                    ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
                    return;
                }

                try {
                    var professors = teachingHandler.GetProfessorsByCourseIDS(request.getCourseIDs());
                    ResponseHelper.ReturnOk(resp, new GetProfessorsByCourseIDsResponse(professors));
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
            var courseHandler = CourseHandler.getInstance();
            AddCourseRequest request = JsonHelper.FromJsonRequest(req, AddCourseRequest.class);
            var valid = request.IsValid();
            if (!valid.getKey()) {
                ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
                return;
            }
            var course = courseHandler.AddCourse(request);
            ResponseHelper.ReturnOk(resp, new AddCourseResponse(course));
        } catch (CourseAlreadyExistsException e) {
            ResponseHelper.ReturnErrorStatus(resp, 409, e.getMessage());
        } catch (Exception e) {
            ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
        }

    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!processRequest(req, resp)) return;

        try {
            var courseHandler = CourseHandler.getInstance();
            EditCourseRequest request = JsonHelper.FromJsonRequest(req, EditCourseRequest.class);
            var valid = request.IsValid();
            if (!valid.getKey()) {
                ResponseHelper.ReturnErrorStatus(resp, 401, valid.getValue());
                return;
            }
            courseHandler.UpdateCourse(request);
            resp.setStatus(200);
        } catch (CourseNotFoundException e) {
            ResponseHelper.ReturnErrorStatus(resp, 404, e.getMessage());
        } catch (Exception e) {
            ResponseHelper.ReturnErrorStatus(resp, 500, e.getMessage());
        }

    }

    protected boolean processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var userHandler = UserHandler.getInstance();
            String path = req.getServletPath() != null ? req.getServletPath() : "";
            switch (path) {
                case "/courses":
                    String feature = "";
                    switch (req.getMethod()) {
                        case "GET":
                            feature = COURSE_GET_ALL;
                            break;
                        case "POST":
                            feature = COURSE_ADD;
                            break;
                        case "PUT":
                            feature = COURSE_UPDATE;
                            break;
                    }
                    if (!userHandler.IsAuthorized(feature, req.getSession()))
                        throw new NotAuthorizedException("Not authorized");
                    break;

                case "/courses/professors":
                    if (!userHandler.IsAuthorized(COURSE_GET_PROFESSORS, req.getSession()))
                        throw new NotAuthorizedException("Not authorized");
                    break;
            }

        } catch (NotAuthorizedException e) {
            ResponseHelper.ReturnErrorStatus(resp, 401, e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
