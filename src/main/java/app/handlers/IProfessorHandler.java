package app.handlers;


import app.models.professors.AddProfessorRequest;
import app.models.professors.GetProfessorsByCourseIDsRequest;
import app.models.professors.Professor;

import java.util.List;

public interface IProfessorHandler {
	public Professor AddProfessor(AddProfessorRequest request) throws Exception;

	public List<Professor> GetAllProfessors() throws Exception;

	public void DeleteProfessor(String ID) throws Exception;
}
