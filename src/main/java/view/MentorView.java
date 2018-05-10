package view;

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
            List<Group> groups = mentor.getGroup();
            List<Student> students = new ArrayList<>();
            for(Group group : groups) {
                students.addAll(group.getStudents());
            }
            model.with("student_list", students);
            return template.render(model);
        }
    }
