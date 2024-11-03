package soasystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Students {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void studentConfig() {
        int option;
        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 

                switch (option) {
                    case 1: addStudent(); break;
                    case 2: viewStudents(); break;
                    case 3: editStudent(); break;
                    case 4: deleteStudent(); break;
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

    private void addStudent() {
        System.out.println("\nEnter Student Details:");

        System.out.print("Full Name: ");
        String name = scan.nextLine();
        System.out.print("Email: ");
        String email = scan.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scan.nextLine();
        System.out.print("Course: ");
        String course = scan.nextLine();

        String sql = "INSERT INTO student (name, email, contact_info, course) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, email, contactInfo, course);
    }

    public void viewStudents() {
        String query = "SELECT * FROM student";
        String[] headers = {"ID", "Name", "Email", "Contact Info", "Course"};
        String[] columns = {"id", "name", "email", "contact_info", "course"};

        conf.viewRecords(query, headers, columns);
    }

    private void editStudent() {
        int id;
        do {
            System.out.print("Enter Student ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("student", id)) {
                System.out.println("Student ID doesn't exist.");
            }
        } while (!conf.doesIDExist("student", id));

        scan.nextLine(); 

        System.out.println("Enter New Student Details:");

        System.out.print("New Full Name: ");
        String name = scan.nextLine();
        System.out.print("New Email: ");
        String email = scan.nextLine();
        System.out.print("New Contact Info: ");
        String contactInfo = scan.nextLine();
        System.out.print("New Course: ");
        String course = scan.nextLine();

        String sql = "UPDATE student SET name = ?, email = ?, contact_info = ?, course = ? WHERE id = ?";
        conf.updateRecord(sql, name, email, contactInfo, course, id);
    }

    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM student WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}

