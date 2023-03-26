package app.dao;

import app.models.professors.Professor;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOProfessor extends DAOBase {

  private static final String INSERT_PROFESSOR_QUERY = "INSERT INTO professors (ID, name, surname) VALUES ('?','?','?')";
  private static final String GET_ALL_PROFESSORS_QUERY = "SELECT * FROM professors WHERE deleted = FALSE ORDER BY surname,name ASC";
  private static final String GET_PROFESSORS_BY_TEXT_QUERY = "SELECT * FROM professors WHERE (name LIKE '%?%' OR surname LIKE '%?%') AND deleted = FALSE";
  private static final String GET_PROFESSORS_BY_FULL_NAME_QUERY = "SELECT * FROM professors WHERE name = '?' AND surname = '?' AND deleted = FALSE";
  private static final String GET_PROFESSOR_BY_ID_QUERY = "SELECT * FROM professors WHERE ID = '?' AND deleted = FALSE LIMIT 1";

  private static final String GET_PROFESSORS_BY_IDS_QUERY = "SELECT * FROM professors WHERE ID IN (?) AND deleted = FALSE";
  private static final String SOFT_DELETE_PROFESSOR_QUERY = "UPDATE professors SET deleted = TRUE WHERE ID = '?'";

  public DAOProfessor() {
    super();
  }


  public Professor GetProfessorByText(String text) throws Exception {
    return GetProfessor(GET_PROFESSORS_BY_TEXT_QUERY, text, text);
  }

  public Professor GetProfessorByFullName(String name, String surname) throws Exception {
    return GetProfessor(GET_PROFESSORS_BY_FULL_NAME_QUERY, name, surname);
  }

  public Professor GetProfessorByID(String ID) throws Exception {
    return GetProfessor(GET_PROFESSOR_BY_ID_QUERY, ID);
  }

  public List<Professor> GetProfessorsByIDs(List<String> ids) throws Exception {
    return GetProfessors(GET_PROFESSORS_BY_IDS_QUERY, ids.toArray());
  }

  private Professor GetProfessor(String query, Object... args) throws Exception {
    Professor currentProfessor = null;
    var result = super.executeQuery(query, args);
    if (result != null) {
      while (result.next()) {
        currentProfessor = new Professor(
                result.getString("ID"),
                result.getString("name"),
                result.getString("surname")
        );
      }
    }
    return currentProfessor;
  }

  public List<Professor> GetProfessors() throws Exception {
    var list = new ArrayList<Professor>();
    var result = super.executeQuery(GET_ALL_PROFESSORS_QUERY);
    if (result != null) {
      while (result.next()) {
        list.add(new Professor(
                result.getString("ID"),
                result.getString("name"),
                result.getString("surname")
        ));
      }
    }
    return list;
  }

  public List<Professor> GetProfessors(String query, Object... args) throws Exception {
    var list = new ArrayList<Professor>();
    var result = super.executeQuery(query, args);
    if (result != null) {
      while (result.next()) {
        list.add(new Professor(
                result.getString("ID"),
                result.getString("name"),
                result.getString("surname")
        ));
      }
    }
    return list;
  }

  ///
  /// Returns if Professor was insert correctly
  ///
  public boolean InsertProfessor(String ID, String name, String surname) throws Exception {
    // MM-NOTA: Usiamo un guid al posto degli id e mettiamo sul db PK sul nome corso,
    // risparmiamo query e controlli facendo fare il tutto al db
    int addRows = super.executeUpdateQuery(
            INSERT_PROFESSOR_QUERY,
            ID,
            name,
            surname
    );

    return addRows > 0;
  }

  public boolean DeleteProfessor(String ID) throws Exception {
    int editedRows = super.executeUpdateQuery(
            SOFT_DELETE_PROFESSOR_QUERY, ID
    );
    return editedRows > 0;
  }
}
