import java.util.List;
import java.util.Scanner;

public class Main {

    private static String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AdminDAO adminDAO = new AdminDAO();
        DepartmentDAO deptDAO = new DepartmentDAO();
        StudentDAO studentDAO = new StudentDAO();
        ResultDAO resultDAO = new ResultDAO();

        System.out.println("=== Student Result Management System ===");
        System.out.print("Enter admin username: ");
        String username = sc.nextLine();
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        if (!adminDAO.validateAdmin(username, password)) {
            System.out.println("Invalid credentials. Exiting...");
            sc.close();
            return;
        }

        System.out.println("Login successful. Welcome, " + username + "!");

        int choice;
        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Add Department");
            System.out.println("2. View Departments");
            System.out.println("3. Add Student");
            System.out.println("4. View All Students");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Add Result for Student");
            System.out.println("8. View All Results");
            System.out.println("9. Show Merit List");
            System.out.println("10. Show Top N Students");
            System.out.println("11. Show Failed Students");
            System.out.println("12. Export Results to CSV");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter Department Name: ");
                    String dname = sc.nextLine();
                    if (deptDAO.addDepartment(dname)) {
                        System.out.println("Department added successfully!");
                    } else {
                        System.out.println("Failed to add department.");
                    }
                    break;
                }

                case 2: {
                    List<String> depts = deptDAO.getAllDepartments();
                    if (depts.isEmpty()) {
                        System.out.println("No departments found.");
                    } else {
                        System.out.println("\n--- Departments ---");
                        for (String d : depts) {
                            System.out.println("- " + d);
                        }
                    }
                    break;
                }

                case 3: {
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Roll No: ");
                    String roll = sc.nextLine();

                    List<String> depts = deptDAO.getAllDepartments();
                    if (depts.isEmpty()) {
                        System.out.println("No departments. Please add a department first (option 1).");
                        break;
                    }

                    System.out.println("Choose Department:");
                    for (int i = 0; i < depts.size(); i++) {
                        System.out.println((i + 1) + ". " + depts.get(i));
                    }
                    System.out.print("Enter choice: ");
                    int dChoice = sc.nextInt();
                    sc.nextLine();

                    if (dChoice < 1 || dChoice > depts.size()) {
                        System.out.println("Invalid department choice.");
                        break;
                    }

                    String deptName = depts.get(dChoice - 1);
                    int deptId = deptDAO.getDepartmentIdByName(deptName);

                    System.out.print("Enter Year (e.g., 1, 2, 3, 4): ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    Student s = new Student(name, roll, deptId, year);
                    if (studentDAO.addStudent(s)) {
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Failed to add student.");
                    }
                    break;
                }

                case 4: {
                    List<Student> students = studentDAO.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        System.out.println("\n--- Students ---");
                        for (Student st : students) {
                            String deptName = deptDAO.getDepartmentNameById(st.getDepartmentId());
                            System.out.println(
                                    "Name: " + st.getName() +
                                    ", Roll: " + st.getRollNo() +
                                    ", Dept: " + deptName +
                                    ", Year: " + st.getYear()
                            );
                        }
                    }
                    break;
                }

                case 5: {
                    System.out.print("Enter Roll No of student to update: ");
                    String rollToUpdate = sc.nextLine();
                    Student existing = studentDAO.getStudentByRollNo(rollToUpdate);
                    if (existing == null) {
                        System.out.println("No student found with that roll number.");
                        break;
                    }

                    System.out.println("Existing Name: " + existing.getName());
                    System.out.print("Enter New Name (leave blank to keep same): ");
                    String newName = sc.nextLine();
                    if (!newName.trim().isEmpty()) {
                        existing.setName(newName);
                    }

                    List<String> depts = deptDAO.getAllDepartments();
                    if (!depts.isEmpty()) {
                        System.out.print("Change Department? (y/n): ");
                        String ch = sc.nextLine();
                        if (ch.equalsIgnoreCase("y")) {
                            System.out.println("Choose Department:");
                            for (int i = 0; i < depts.size(); i++) {
                                System.out.println((i + 1) + ". " + depts.get(i));
                            }
                            System.out.print("Enter choice: ");
                            int dChoice = sc.nextInt();
                            sc.nextLine();
                            if (dChoice >= 1 && dChoice <= depts.size()) {
                                String deptName = depts.get(dChoice - 1);
                                int deptId = deptDAO.getDepartmentIdByName(deptName);
                                existing.setDepartmentId(deptId);
                            }
                        }
                    }

                    System.out.println("Existing Year: " + existing.getYear());
                    System.out.print("Enter New Year (or -1 to keep same): ");
                    int newYear = sc.nextInt();
                    sc.nextLine();
                    if (newYear > 0) {
                        existing.setYear(newYear);
                    }

                    if (studentDAO.updateStudent(existing)) {
                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Failed to update student.");
                    }
                    break;
                }

                case 6: {
                    System.out.print("Enter Roll No of student to delete: ");
                    String rollToDelete = sc.nextLine();
                    if (studentDAO.deleteStudent(rollToDelete)) {
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Failed to delete student.");
                    }
                    break;
                }

                case 7: {
                    System.out.print("Enter Roll No of student to add result: ");
                    String roll = sc.nextLine();
                    Student s = studentDAO.getStudentByRollNo(roll);
                    if (s == null) {
                        System.out.println("No student found with that roll number.");
                        break;
                    }

                    System.out.print("Enter Subject: ");
                    String subject = sc.nextLine();

                    System.out.print("Enter Marks (0-100): ");
                    int marks = sc.nextInt();
                    sc.nextLine();

                    String grade = calculateGrade(marks);

                    if (resultDAO.addResult(s.getId(), subject, marks, grade)) {
                        System.out.println("Result added successfully!");
                    } else {
                        System.out.println("Failed to add result.");
                    }
                    break;
                }

                case 8: {
                    resultDAO.printAllResultsWithDetails();
                    break;
                }

                case 9: {
                    resultDAO.printMeritList();
                    break;
                }

                case 10: {
                    System.out.print("Enter N (number of top students): ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    resultDAO.printTopNStudents(n);
                    break;
                }

                case 11: {
                    System.out.print("Enter pass marks (e.g., 40): ");
                    int passMarks = sc.nextInt();
                    sc.nextLine();
                    resultDAO.printFailedStudents(passMarks);
                    break;
                }

                case 12: {
                    System.out.print("Enter CSV file name (e.g., results.csv): ");
                    String fileName = sc.nextLine();
                    resultDAO.exportResultsToCSV(fileName);
                    break;
                }

                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
