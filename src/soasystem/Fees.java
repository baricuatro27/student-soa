package soasystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Fees {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void feeConfig() {
        int option;
        do {
            System.out.println("\n--- Fee Management ---");
            System.out.println("1. Add Fee");
            System.out.println("2. View Fees");
            System.out.println("3. Edit Fee");
            System.out.println("4. Delete Fee");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 

                switch (option) {
                    case 1: addFee(); break;
                    case 2: viewFees(); break;
                    case 3: editFee(); break;
                    case 4: deleteFee(); break;
                    case 5: System.out.println("Returning to main menu."); break;
                    default: System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                option = -1;
            }
        } while (option != 5);
    }

    private void addFee() {
        System.out.println("Enter Fee Details:");

        System.out.print("Description: ");
        String description = scan.nextLine();
        System.out.print("Amount: ");
        double amount = scan.nextDouble();

        String sql = "INSERT INTO fee (description, amount) VALUES (?, ?)";
        conf.addRecord(sql, description, amount);
    }

    public void viewFees() {
        String query = "SELECT * FROM fee";
        String[] headers = {"ID", "Description", "Amount"};
        String[] columns = {"id", "description", "amount"};

        conf.viewRecords(query, headers, columns);
    }

    private void editFee() {
        int id;
        do {
            System.out.print("Enter Fee ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("fee", id)) {
                System.out.println("Fee ID doesn't exist.");
            }
        } while (!conf.doesIDExist("fee", id));

        scan.nextLine(); 

        System.out.println("Enter New Fee Details:");

        System.out.print("New Description: ");
        String description = scan.nextLine();
        System.out.print("New Amount: ");
        double amount = scan.nextDouble();

        String sql = "UPDATE fee SET description = ?, amount = ? WHERE id = ?";
        conf.updateRecord(sql, description, amount, id);
    }

    private void deleteFee() {
        System.out.print("Enter Fee ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM fee WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
