package view;

import model.ItemModel;
import model.StudentModel;
import model.WalletModel;
import model.GroupModel;

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
                            + "9 - Exit\n");
    }

    public void displayItemCollection(List<ItemModel> itemCollection) {
        for (ItemModel item: itemCollection) 
            System.out.println("\nid: " + item.getID()
                            + "\ntype: " + item.getType()
                            + "\nname: " +item.getName()
                            + "\nvalue: " + item.getValue());
    }

    public void displayAllStudents(List<StudentModel> studentsCollection) {
        for (StudentModel student: studentsCollection) {
            System.out.println(student.getID() + ". " + student.getFullName());
        }
    }
    public void displayStudentWallet(WalletModel wallet) {
        System.out.println("\nBALANCE: " + wallet.getBalance() +
                           "\nTOTAL COOLCOINS: " + wallet.getTotalCoolcoins());
    }

    public void displayStudentArtifacts(List<ItemModel> artifactsCollection) {
        System.out.println("Students artifacts: \n");
        for (ItemModel artifact : artifactsCollection) {
            System.out.println(artifact.getID() + ". " + artifact.getName());
        }
    }
    public void displayAllGroups(List<GroupModel> groupsCollection) {
        for (GroupModel group: groupsCollection) {
            System.out.println(group.getId() + ". "  +group.getGroupName());
        }
    }
}