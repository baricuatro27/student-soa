package soasystem;

import java.util.Scanner;
import java.util.InputMismatchException;

public class SOASystem {
    static Config conf = new Config();
    static Scanner scan = new Scanner(System.in);
    static Students students = new Students();
    static Fees fees = new Fees();
    static StudentFees studentFees = new StudentFees();

    public static void main(String[] args) {
        int option;
        do {
            System.out.println("\n--- Student Statement of Account (SOA) System ---");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Fees");
            System.out.println("3. Manage Student Fees");
            System.out.println("4. Generate Reports");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 

                switch (option) {
                    case 1: students.studentConfig(); break;
                    case 2: fees.feeConfig(); break;
                    case 3: studentFees.studentFeeConfig(); break;
                    case 4: generateReports(); break;
                    case 5: System.out.println("Exiting system. Goodbye!"); break;
                    default: System.out.println("Invalid option. Please choose a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                option = -1;
            }
        } while (option != 5);

        scan.close();
    }

    static void generateReports() {
        System.out.println("\n--- Generate Student Fee Report ---");
        students.viewStudents();

        int studentId;
        do {
            System.out.print("\nEnter Student ID to generate report: ");
            studentId = scan.nextInt();
            if (!conf.doesIDExist("student", studentId)) {
                System.out.println("Student ID doesn't exist.");
            }
        } while (!conf.doesIDExist("student", studentId));

        System.out.println("================================================================");
        System.out.println("\n--- Student Fee Report ---");
        System.out.println("Student ID    : " + studentId);
        System.out.println("Student Name  : " + conf.getDataFromID("student", studentId, "name"));
        System.out.println("Email         : " + conf.getDataFromID("student", studentId, "email"));
        System.out.println("Course        : " + conf.getDataFromID("student", studentId, "course"));
        System.out.println("");

        if (conf.isTableEmpty("student_fee WHERE student_id = " + studentId)) {
            System.out.println("No fee records found for this student.");
            System.out.println("================================================================");
        } else {
            System.out.println("Fee Records:");
            String sql = "SELECT sf.id, f.description, f.amount, sf.payment_date, sf.status " +
                         "FROM student_fee sf " +
                         "JOIN fee f ON sf.fee_id = f.id " +
                         "WHERE sf.student_id = " + studentId;

            String[] headers = {"ID", "Description", "Amount", "Payment Date", "Status"};
            String[] columns = {"id", "description", "amount", "payment_date", "status"};

            conf.viewRecords(sql, headers, columns);
            System.out.println("\n================================================================");
        }
    }
}


