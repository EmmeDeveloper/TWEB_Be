package app.handlers;

import app.dao.DAOProfessor;
import app.exceptions.CourseNotFoundException;
import app.exceptions.ProfessorAlreadyExistsException;
import app.exceptions.ProfessorNotFoundException;
import app.models.professors.AddProfessorRequest;
import app.models.professors.GetProfessorsByCourseIDsRequest;
import app.models.professors.Professor;
import lombok.var;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;
import java.util.UUID;

interface IProfessorHandler {
	Professor AddProfessor(AddProfessorRequest request) throws Exception;

	List<Professor> GetAllProfessors() throws Exception;

	void DeleteProfessor(String ID) throws Exception;
}


public class ProfessorHandler implements IProfessorHandler {

	private static final String HANDLER_KEY = "#professorHandler";
	private static DAOProfessor _dao;
	private static ServletContext _context;

	private ProfessorHandler() {}

	public static void Init(ServletContext context) {
		_dao = new DAOProfessor();
		_context = context;
		_context.setAttribute(HANDLER_KEY, new ProfessorHandler());
	}

	public static ProfessorHandler getInstance() throws ServletException {
		if(_context == null)
			throw new ServletException("Context not provided, missing call to @Init method?");
		return (ProfessorHandler) _context.getAttribute(HANDLER_KEY);
	}

	public Professor AddProfessor(AddProfessorRequest request) throws Exception {

		var professor = _dao.GetProfessorByFullName(request.getName(), request.getSurname());

		if(professor != null) {
			throw new ProfessorAlreadyExistsException("Professor with the same name and surname already exists");
		}

		Professor p = new Professor(
				UUID.randomUUID().toString(),
				request.getName(),
				request.getSurname()
		);

		if(_dao.InsertProfessor(p.getID(), p.getName(), p.getSurname())) {
			return p;
		}
		throw new Exception("Cannot insert professor: Unhandled error");
	}

	public List<Professor> GetAllProfessors() throws Exception {
		var professors = _dao.GetProfessors();
		return professors;
	}

	public void DeleteProfessor(String ID) throws Exception {
		var professor = _dao.GetProfessorByID(ID);
		if (professor == null) {
			throw new ProfessorNotFoundException("Professor not found");
		}
		var res = _dao.DeleteProfessor(ID);
		if (!res) throw new Exception("Cannot delete professor: Unhandled error ");

		var teachHandler = TeachingHandler.getInstance();
		teachHandler.DeleteTeachingsOfProfessor(ID);

		// TODO: Set all repetitions of this professor to deleted with note
	}
}
