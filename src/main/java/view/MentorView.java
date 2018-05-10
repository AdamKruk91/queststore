package view;

import dao.GroupDAO;
import dao.GroupDAOSQL;
import exceptions.DataAccessException;
import model.Group;
import model.Mentor;
import model.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.ArrayList;
import java.util.List;

public class MentorView {

    public String getRequestScreen(Mentor mentor) throws DataAccessException {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor-request.twig");
            JtwigModel model = JtwigModel.newModel();
            GroupDAO groupDAO = new GroupDAOSQL();
            Group group = groupDAO.getByUser(mentor.getID());
            List<Student> students = new ArrayList<>();
            students.addAll(group.getStudents());
            model.with("student_list", students);
            return template.render(model);
        }
    }
