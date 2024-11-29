package soasystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentFees {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void studentFeeConfig() {
        int option;
        do {
            System.out.println("\n--- Student Fee Management ---");
            System.out.println("1. Add Student Fee");
            System.out.println("2. View Student Fees");
            System.out.println("3. Edit Student Fee");
            System.out.println("4. Delete Student Fee");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 

                switch (option) {
                    case 1: addStudentFee(); break;
                    case 2: viewStudentFees(); break;
                    case 3: editStudentFee(); break;
                    case 4: deleteStudentFee(); break;
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

    private void addStudentFee() {
        System.out.println("Enter Student Fee Details:");

        int studentId;
        do {
            System.out.print("\nStudent ID: ");
            studentId = scan.nextInt();
            if (!conf.doesIDExist("student", studentId)) {
                System.out.println("Student ID doesn't exist.");
            }
        } while (!conf.doesIDExist("student", studentId));

        int feeId;
        do {
            System.out.print("Fee ID: ");
            feeId = scan.nextInt();
            if (!conf.doesIDExist("fee", feeId)) {
                System.out.println("Fee ID doesn't exist.");
            }
        } while (!conf.doesIDExist("fee", feeId));

        scan.nextLine();

        System.out.print("Payment Date (YYYY-MM-DD): ");
        String paymentDate = scan.nextLine();
        System.out.print("Status: ");
        String status = scan.nextLine();

        String sql = "INSERT INTO student_fee (student_id, fee_id, payment_date, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, studentId, feeId, paymentDate, status);
    }

    public void viewStudentFees() {
        String query = "SELECT student_fee.id, name, amount, payment_date, status FROM student_fee "
                + "INNER JOIN fee ON fee_id = fee.id "
                + "INNER JOIN student ON student_id = student.id";
        String[] headers = {"ID", "Student Name", "Amount", "Payment Date", "Status"};
        String[] columns = {"id", "name", "amount", "payment_date", "status"};

        conf.viewRecords(query, headers, columns);
    }

    private void editStudentFee() {
        int id;
        do {
            System.out.print("Enter Student Fee ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("student_fee", id)) {
                System.out.println("Student Fee ID doesn't exist.");
            }
        } while (!conf.doesIDExist("student_fee", id));

        scan.nextLine();

        System.out.println("Enter New Student Fee Details:");

        int studentId;
        do {
            System.out.print("New Student ID: ");
            studentId = scan.nextInt();
            if (!conf.doesIDExist("student", studentId)) {
                System.out.println("Student ID doesn't exist.");
            }
        } while (!conf.doesIDExist("student", studentId));

        int feeId;
        do {
            System.out.print("New Fee ID: ");
            feeId = scan.nextInt();
            if (!conf.doesIDExist("fee", feeId)) {
                System.out.println("Fee ID doesn't exist.");
            }
        } while (!conf.doesIDExist("fee", feeId));

        scan.nextLine();

        System.out.print("New Payment Date (YYYY-MM-DD): ");
        String paymentDate = scan.nextLine();
        System.out.print("New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE student_fee SET student_id = ?, fee_id = ?, payment_date = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, studentId, feeId, paymentDate, status, id);
    }

    private void deleteStudentFee() {
        System.out.print("Enter Student Fee ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM student_fee WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
