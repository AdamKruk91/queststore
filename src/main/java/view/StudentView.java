package view;

import model.WalletModel;
import model.ItemModel;

import java.util.List;

public class StudentView {

    public void displayStudentMenu(){
        System.out.println("1 - See your wallet.\n"
                + "2 - Buy artifact.\n"
                + "3 - Buy artifact together with teammates.\n"
                + "4 - See your level of experience\n"
                + "5 - Exit");
    }

    public void displayCollectionOfItem(List<ItemModel> itemCollection) {
        System.out.println("\nAvailable artifacts: ");
        for (ItemModel item: itemCollection) {
            System.out.println(item.getID() + ". " + item.getName() + " value: " + item.getValue());
        }
        System.out.println("\n");
    }

    public void displayBoughtArtifacts(List<ItemModel>  itemCollection) {
        System.out.println("YOUR ARTIFACTS: ");
        for (ItemModel item: itemCollection) {
            System.out.println("==> " + item.getName() + item.getValue());
        }
        System.out.println("\n");
    }

    public void displayWallet(WalletModel wallet) {
        System.out.println("\nBALANCE: " + wallet.getBalance() +
                           "\nTOTAL COOLCOINS: " + wallet.getTotalCoolcoins());
    }
}