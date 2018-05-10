package view;

import dao.GroupDAO;
import dao.GroupDAOSQL;
import exceptions.DataAccessException;
import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class StudentView {

    public String getProfileScreen (Student student, Level level) throws DataAccessException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-profile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("name", student.getFullName());
        model.with("email", student.getEmail());
        GroupDAO groupDAO = new GroupDAOSQL();
        Group group = null;
        group = groupDAO.getByUser(student.getID());
        model.with("group", group.getName());
        int totalExp = student.getWallet().getTotalCoinsEarned();
        String levelStr = String.format("%s (%d EXP)", level.getName(), totalExp);
        model.with("level", levelStr); // Todo add reading level
        return template.render(model);
    }

    public String getWalletScreen (Student student, List<Artifact> artifacts) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-wallet.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("fullname", student.getFullName());
        model.with("coins", student.getWallet().getAmount());
        model.with("artifact_list", artifacts);
        return template.render(model);
    }

    public String getWalletUsedScreen (Student student, List<Artifact> artifacts) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-wallet-used.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("fullname", student.getFullName());
        model.with("coins", student.getWallet().getAmount());
        model.with("artifact_list", artifacts);
        return template.render(model);
    }

    public String getWalletPendingScreen (Student student, List<Artifact> artifacts) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-wallet-pending.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("fullname", student.getFullName());
        model.with("coins", student.getWallet().getAmount());
        model.with("artifact_list", artifacts);
        return template.render(model);
    }
}
