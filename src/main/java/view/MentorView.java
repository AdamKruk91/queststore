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

    public String getRequestScreen(Mentor mentor) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor-request.twig");
            JtwigModel model = JtwigModel.newModel();
            GroupDAO groupDAO = new GroupDAOSQL();
            Group group = null;
            try {
                group = groupDAO.getByUser(mentor.getID());
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        List<Student> students = new ArrayList<>();
            students.addAll(group.getStudents());
            model.with("student_list", students);
            return template.render(model);
        }
    }
