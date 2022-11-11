package app.handlers;

import app.models.professors.Professor;

import java.util.List;

public interface ITeachingHandler {

    public List<Professor> GetProfessorsByCourseIDS(List<String> IDs) throws Exception;

}
