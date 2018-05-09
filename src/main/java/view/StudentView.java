package view;

import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class StudentView {

    public void displayStudentMenu(){
        System.out.println("1 - See your wallet.\n"
                + "2 - Buy artifact.\n"
                + "3 - Buy artifact together with teammates.\n"
                + "4 - See your level of experience\n"
                + "5 - Exit");
    }

    public void displayCollectionOfItem(List<Artifact> itemCollection) {
        System.out.println("\nAvailable artifacts: ");
        for (UsableObject item: itemCollection) {
            System.out.println(item.getID() + ". " + item.getName() + " value: " + item.getValue());
        }
        System.out.println("\n");
    }

    public void displayBoughtArtifacts(List<Artifact>  itemCollection) {
        System.out.println("YOUR ARTIFACTS: ");
        for (UsableObject item: itemCollection) {
            System.out.println("==> " + item.getName() + item.getValue());
        }
        System.out.println("\n");
    }

    public void displayWallet(Wallet wallet) {
        System.out.println("\nBALANCE: " + wallet.getBalance() +
                           "\nTOTAL COOLCOINS: " + wallet.getTotalCoolcoins());
    }

    public void displayCurrentExperience(int totalExp, String levelName) {
        System.out.println(String.format("You gained: %d experience so far!" +
                                         "\nYour level: %s", totalExp, levelName));
    }

    public String getProfileScreen (Student student, Level level) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-profile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("name", student.getFullName());
        model.with("email", student.getEmail());
        model.with("group", student.getGroup().getGroupName());
        int totalExp = student.getMyWallet().getTotalCoolcoins();
        String levelStr = String.format("%s (%d EXP)", level.getName(), totalExp);
        model.with("level", levelStr); // Todo add reading level
        return template.render(model);
    }

    public String getWalletScreen (Student student, List<Artifact> artifacts) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-wallet.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("fullname", student.getFullName());
        model.with("coins", student.getMyWallet().getBalance());
        model.with("artifact_list", artifacts);
        return template.render(model);
    }

    public String getWalletUsedScreen (Student student, List<Artifact> artifacts) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-wallet-used.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("fullname", student.getFullName());
        model.with("coins", student.getMyWallet().getBalance());
        model.with("artifact_list", artifacts);
        return template.render(model);
    }

    public String getWalletPendingScreen (Student student, List<Artifact> artifacts) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student-wallet-pending.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("fullname", student.getFullName());
        model.with("coins", student.getMyWallet().getBalance());
        model.with("artifact_list", artifacts);
        return template.render(model);
    }
}
