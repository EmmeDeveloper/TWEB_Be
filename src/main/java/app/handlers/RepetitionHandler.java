package app.handlers;

import app.dao.DAORepetition;
import app.dao.DAOUser;
import app.models.professors.Professor;
import app.models.repetitions.GetRepetitionsResponse;
import app.models.repetitions.Repetition;
import app.models.users.User;
import lombok.var;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

interface IRepetitionHandler {
  GetRepetitionsResponse GetRepetitionsByCourseIds(User currentUser, List<String> courseIds, Date from, Date to) throws Exception;
  GetRepetitionsResponse GetAllRepetitionsByUser(String userId) throws Exception;

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

    if (courseIds == null || courseIds.isEmpty())
      return null;

    if (from == null || to == null)
      return null;

    if (from.after(to))
      return null;

    var response = new GetRepetitionsResponse();

    // Get all repetitions filtered by course ids
    var list = _dao.GetRepetitionsByCourseIds(courseIds, from, to);
    if (currentUser.IsAdmin()) {
      // If user is admin, all repetitions should have the user field set
      // Also set professor field, as the request could be made from a page where the professor is not set

      var userIds = list.stream().map(Repetition::getIDUser).collect(Collectors.toList());
      var users = UserHandler.getInstance().GetUsersByIDs(userIds);
      var userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));

      var professorIds = list.stream().map(Repetition::getIDProfessor).collect(Collectors.toList());
      var professors = ProfessorHandler.getInstance().GetProfessorsByIDs(professorIds);
      var professorMap = professors.stream().collect(Collectors.toMap(Professor::getID, p -> p));

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
    }


    response.setRepetitions(
            list.stream()
                    .map(r -> GetFullRepetition(r, null, null))
                    .collect(Collectors.toList())
    );

    return response;
  }

  @Override
  public GetRepetitionsResponse GetAllRepetitionsByUser(String userId) throws Exception {
    return null;
  }

  private GetRepetitionsResponse.FullRepetition GetFullRepetition(Repetition repetition, User user, Professor professor) {
    var fullRepetition = new GetRepetitionsResponse.FullRepetition();
    fullRepetition.setID(repetition.getID());
    fullRepetition.setIDCourse(repetition.getIDCourse());
    fullRepetition.setDate(repetition.getDate());
    fullRepetition.setTime(repetition.getTime());
    fullRepetition.setStatus(repetition.getStatus());
    fullRepetition.setNote(repetition.getNote());
    if (user != null)
      fullRepetition.setUser(user);
    if (professor != null)
      fullRepetition.setProfessor(professor);
    return fullRepetition;
  }
}
