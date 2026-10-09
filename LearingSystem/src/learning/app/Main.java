package learning.app;

import learning.model.Student;
import learning.service.StudentService;
import learning.util.DatabaseConnection;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("MySQL connection successful!");
            System.out.println("Database: " + connection.getCatalog());
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.out.println("Check DatabaseConnection.java and your MySQL password.");
            return;
        }

        StudentService service = new StudentService();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n===== LEARNING SYSTEM =====");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Find Student by ID");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1": {
                            System.out.print("Student ID: ");
                            int id = Integer.parseInt(scanner.nextLine());

                            System.out.print("Name: ");
                            String name = scanner.nextLine();

                            System.out.print("Email: ");
                            String email = scanner.nextLine();

                            System.out.print("Department: ");
                            String department = scanner.nextLine();

                            service.create(new Student(id, name, email, department));
                            System.out.println("Student saved successfully.");
                            break;
                        }

                        case "2":
                            for (Student s : service.getAll()) {
                                displayStudent(s);
                            }
                            break;

                        case "3": {
                            System.out.print("Student ID: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            displayStudent(service.getById(id));
                            break;
                        }

                        case "4": {
                            System.out.print("Student ID to update: ");
                            int id = Integer.parseInt(scanner.nextLine());

                            System.out.print("New name: ");
                            String name = scanner.nextLine();

                            System.out.print("New email: ");
                            String email = scanner.nextLine();

                            System.out.print("New department: ");
                            String department = scanner.nextLine();

                            service.update(new Student(id, name, email, department));
                            System.out.println("Student updated successfully.");
                            break;
                        }

                        case "5":
                            System.out.print("Student ID to delete: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            service.delete(id);
                            System.out.println("Student deleted successfully.");
                            break;

                        case "0":
                            System.out.println("Exiting Learning System.");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("Operation failed: " + e.getMessage());
                }
            }
        }
    }

    private static void displayStudent(Student s) {
        System.out.println(
            "ID: " + s.getStudentId()
            + " | Name: " + s.getName()
            + " | Email: " + s.getEmail()
            + " | Department: " + s.getDepartment()
        );
    }
}