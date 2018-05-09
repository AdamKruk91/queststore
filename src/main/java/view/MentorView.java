package view;

import model.UsableObject;
import model.Wallet;
import model.Group;
import model.Student;

import java.util.List;


public class MentorView {

    public void displayMentorMenu(){
        System.out.println( "\nMENU:\n"
                            + "1 - Create student's account.\n"
                            + "2 - Add a new quest.\n"
                            + "3 - Add new artifact to store.\n"
                            + "4 - Update existing quest.\n"
                            + "5 - Update existing artifact\n"
                            + "6 - Mark student's achieved quests\n"
                            + "7 - Mark student's bought artifacts\n"
                            + "8 - See student's wallet\n"
                            + "9 - Display All Students\n"
                            + "10 - Edit Student\n"
                            + "11 - Delete Student\n"
                            + "0 - Exit\n");
    }

    public void displayItemCollection(List<UsableObject> itemCollection) {
        for (UsableObject item: itemCollection)
            System.out.println("\nid: " + item.getID()
                            + "\ntype: " + item.getType()
                            + "\nname: " +item.getName()
                            + "\nvalue: " + item.getValue());
    }

    public void displayAllStudents(List<Student> studentsCollection) {
        for (Student student: studentsCollection) {
            System.out.println(student.getID() + ". " + student.getFullName());
        }
    }
    public void displayStudentWallet(Wallet wallet) {
        System.out.println("\nBALANCE: " + wallet.getBalance() +
                           "\nTOTAL COOLCOINS: " + wallet.getTotalCoolcoins());
    }

    public void displayStudentArtifacts(List<UsableObject> artifactsCollection) {
        System.out.println("Students artifacts: \n");
        for (UsableObject artifact : artifactsCollection) {
            System.out.println(artifact.getID() + ". " + artifact.getName());
        }
    }
    public void displayAllGroups(List<Group> groupsCollection) {
        for (Group group: groupsCollection) {
            System.out.println(group.getID() + ". "  +group.getGroupName());
        }
    }

    public void displayEditStudentMenu() {
        System.out.println("\n1. name\n"
                + "2. last name\n"
                + "3. email\n"
                + "4. password\n"
                + "5. select group\n"
                + "6. exit\n");
    }
}