package app.handlers;

import app.commons.Constants;
import app.dao.DAORepetition;
import app.dao.DAOUser;
import app.exceptions.CourseNotFoundException;
import app.exceptions.ProfessorNotFoundException;
import app.exceptions.UserNotFoundException;
import app.models.courses.Course;
import app.models.professors.Professor;
import app.models.repetitions.AddRepetitionRequest;
import app.models.repetitions.GetRepetitionsResponse;
import app.models.repetitions.Repetition;
import app.models.users.User;
import javafx.util.Pair;
import lombok.var;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.rmi.server.UID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

interface IRepetitionHandler {
  GetRepetitionsResponse GetRepetitionsByCourseIds(User currentUser, List<String> courseIds, Date from, Date to) throws Exception;
  GetRepetitionsResponse GetAllRepetitionsByUser(String userId, Date from, Date to) throws Exception;
  GetRepetitionsResponse GetRepetitionsForAllUsers(Date from, Date to) throws Exception;
  void CancelRepetitionsOfCourse(String courseId) throws Exception;
  void CancelRepetitionsOfProfessor(String professorId) throws Exception;
  Repetition AddRepetition(AddRepetitionRequest request) throws Exception;
  void UpdateRepetition(String id, String status, String note) throws Exception;

  void DeleteRepetition(String id) throws Exception;
}

public class RepetitionHandler implements IRepetitionHandler {

  private static final String HANDLER_KEY = "#repetitionsHandler";
  private static ServletContext _context;
  private static DAORepetition _dao;

  public static void Init(ServletContext context) {
    _dao = new DAORepetition();
    _context = context;
    _context.setAttribute(HANDLER_KEY, new RepetitionHandler());
  }

  public static RepetitionHandler getInstance() {
    if (_context == null)
      throw new RuntimeException("Context not provided, missing call to @Init method?");
    return (RepetitionHandler) _context.getAttribute(HANDLER_KEY);
  }

  @Override
  public GetRepetitionsResponse GetRepetitionsByCourseIds(User currentUser, List<String> courseIds, Date from, Date to) throws Exception {

    if (currentUser == null)
      return null;

    if (courseIds == null || courseIds.isEmpty())
      return null;

    if (from == null || to == null)
      return null;

    if (from.after(to))
      return null;

    var response = new GetRepetitionsResponse();

    // Get all repetitions filtered by course ids
    var list = _dao.GetRepetitionsByCourseIds(courseIds, from, to);

    // Get all professors
    var professorIds = list.stream().map(Repetition::getIDProfessor).collect(Collectors.toList());
    var professors = ProfessorHandler.getInstance().GetProfessorsByIDs(professorIds);
    var professorMap = professors.stream().collect(Collectors.toMap(Professor::getID, p -> p));

    if (currentUser.IsAdmin()) {
      // If user is admin, all repetitions should have the user field set
      var userIds = list.stream().map(Repetition::getIDUser).collect(Collectors.toList());
      var users = UserHandler.getInstance().GetUsersByIDs(userIds);
      var userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));

      response.setRepetitions(
              list
                      .stream()
                      .map(r -> GetFullRepetition(
                              r,
                              userMap.get(r.getIDUser()),
                              professorMap.get(r.getIDProfessor()))
                      )
                      .collect(Collectors.toList())
      );
    } else {
      // If user is not admin, only repetitions of the current user should have the user field set
      response.setRepetitions(
              list
                      .stream()
                      .map(r -> GetFullRepetition(
                              r,
                              r.getIDUser().equals(currentUser.getId()) ? currentUser : null,
                              professorMap.get(r.getIDProfessor()))
                      )
                      .collect(Collectors.toList())
      );
    }

    return response;
  }

  @Override
  public GetRepetitionsResponse GetAllRepetitionsByUser(String userId, Date from, Date to) throws Exception {

    if (userId == null || userId.isEmpty())
      return null;

    if (from.after(to))
      return null;

    if (from == null)
      from = new Date(0);

    if (to == null) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, 1); // to get previous year add -1
      to = cal.getTime();
    }

    var response = new GetRepetitionsResponse();
    var list = _dao.GetRepetitionsByUserID(userId, from, to);

    var user = UserHandler
            .getInstance()
            .GetUsersByIDs(new ArrayList<String>() {{ add(userId); }})
            .get(0);

    if (user == null)
      return null;

    var professorIds = list.stream().map(Repetition::getIDProfessor).collect(Collectors.toList());
    var professors = ProfessorHandler.getInstance().GetProfessorsByIDs(professorIds);
    var professorMap = professors.stream().collect(Collectors.toMap(Professor::getID, p -> p));

    response.setRepetitions(
            list
                    .stream()
                    .map(r -> GetFullRepetition(
                            r,
                            user,
                            professorMap.get(r.getIDProfessor()))
                    )
                    .collect(Collectors.toList())
    );

    return response;
  }

  @Override
  public GetRepetitionsResponse GetRepetitionsForAllUsers(Date from, Date to) throws Exception {
    var courses = CourseHandler.getInstance().GetAllCourses();
    var courseIds = courses.stream().map(Course::getId).collect(Collectors.toList());

    var response = new GetRepetitionsResponse();
    var list = _dao.GetRepetitionsByCourseIds(courseIds, from, to);

    var professorIds = list.stream().map(Repetition::getIDProfessor).collect(Collectors.toList());
    var professors = ProfessorHandler.getInstance().GetProfessorsByIDs(professorIds);
    var professorMap = professors.stream().collect(Collectors.toMap(Professor::getID, p -> p));

    var userIds = list.stream().map(Repetition::getIDUser).collect(Collectors.toList());
    var users = UserHandler.getInstance().GetUsersByIDs(userIds);
    var userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));

    response.setRepetitions(
            list
                    .stream()
                    .map(r -> GetFullRepetition(
                            r,
                            userMap.get(r.getIDUser()),
                            professorMap.get(r.getIDProfessor()))
                    )
                    .collect(Collectors.toList())
    );

    return response;
  }

  @Override
  public void CancelRepetitionsOfCourse(String courseId) throws Exception {
    if (courseId == null || courseId.isEmpty())
      return;
    _dao.SetRepetitionsStatusAndNoteByCourseID(courseId, Constants.RepetitionStatus.DELETED, "Ripetizione annullata per cancellazione corso");
  }

  @Override
  public void CancelRepetitionsOfProfessor(String professorId) throws Exception {
    if (professorId == null || professorId.isEmpty())
      return;
    _dao.SetRepetitionsStatusAndNoteByProfessorID(professorId, Constants.RepetitionStatus.DELETED, "Ripetizione annullata per cancellazione professore");
  }

  @Override
  public Repetition AddRepetition(AddRepetitionRequest request) throws Exception {
    var user = UserHandler.getInstance().GetUsersByIDs(new ArrayList<String>() {{ add(request.getIDUser()); }});
    if (user == null || user.isEmpty())
      throw new UserNotFoundException("User not found");
    var professor = ProfessorHandler.getInstance().GetProfessorsByIDs(new ArrayList<String>() {{ add(request.getIDProfessor()); }});
    if (professor == null || professor.isEmpty())
      throw new ProfessorNotFoundException("Professor not found");

    var course = CourseHandler.getInstance().GetCourseByID(request.getIDCourse());
    if (course == null)
      throw new CourseNotFoundException("Course not found");

    var repetition = new Repetition(
            UUID.randomUUID().toString(),
            request.getIDUser(),
            request.getIDCourse(),
            request.getIDProfessor(),
            request.getDate(),
            request.getTime(),
            Constants.RepetitionStatus.PENDING,
            null
    );

    var res = _dao.AddRepetition(repetition);
    if (!res)
      throw new Exception("Cannot add repetition: Unhandled error ");

    return repetition;
  }

  @Override
  public void UpdateRepetition(String id, String status, String note) throws Exception {
    if (id == null || id.isEmpty())
      throw new Exception("Cannot update repetition: ID is null or empty");

    var repetition = _dao.GetRepetitionByID(id);
    if (repetition == null)
      throw new Exception("Cannot update repetition: Repetition not found");

    if (status == null || status.isEmpty())
      status = Constants.RepetitionStatus.PENDING; // Default value

    // Se si prova a confermare l'avvenimento di una ripetizione, questa deve essere passata
    if (!status.equals(Constants.RepetitionStatus.PENDING)) {
      var date = repetition.getDate();

      var isInFutureDay = date.isAfter(LocalDate.now());
      var isToday = date.isEqual(LocalDate.now());
      var isInCurrentOrFutureHour = repetition.getTime() >= LocalDateTime.now().getHour();

      if (isInFutureDay || (isToday && isInCurrentOrFutureHour))
        throw new Exception("Cannot update repetition: Cannot change status of repetition before it will finish");
    }

    if (repetition.getStatus().equals(Constants.RepetitionStatus.DONE) ||
            repetition.getStatus().equals(Constants.RepetitionStatus.DELETED))
      throw new Exception("Cannot update repetition: Cannot change status of repetition already confirmed");

    var res = _dao.SetRepetitionsStatusAndNoteByID(id, status, note);
    if (!res)
      throw new Exception("Cannot update repetition: Unhandled error ");
  }

  @Override
  public void DeleteRepetition(String id) throws Exception {
    if (id == null || id.isEmpty())
      throw new Exception("Cannot delete repetition: ID is null or empty");

    _dao.DeleteRepetitionByID(id);
  }

  private GetRepetitionsResponse.FullRepetition GetFullRepetition(Repetition repetition, User user, Professor professor) {
    var fullRepetition = new GetRepetitionsResponse.FullRepetition();
    fullRepetition.setID(repetition.getID());
    fullRepetition.setIDCourse(repetition.getIDCourse());
    fullRepetition.setDate(repetition.getDate());
    fullRepetition.setTime(repetition.getTime());

    if (user != null) {
      fullRepetition.setStatus(repetition.getStatus());
      fullRepetition.setNote(repetition.getNote());
      fullRepetition.setUser(user);
    }

    if (professor != null)
      fullRepetition.setProfessor(professor);
    return fullRepetition;
  }
}
