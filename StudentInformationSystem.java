import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student {
    String name;
    String id;
    String course;
    double[] grades; 
    String[] subjects = {"HCI", "Programming 2", "IT Era"};

    public Student(String name, String id, String course, double[] grades) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.grades = grades;
    }

    public double calculateGWA() {
        double total = 0;
        for (double grade : grades) {
            total += grade;
        }
        return total / grades.length; 
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id)
          .append(", Name: ").append(name)
          .append(", Course: ").append(course)
          .append(", Grades: ");
        
        for (int i = 0; i < subjects.length; i++) {
            sb.append(subjects[i]).append(": ").append(grades[i]);
            if (i < subjects.length - 1) {
                sb.append(", ");
            }
        }
        
        sb.append(", GWA: ").append(String.format("%.2f", calculateGWA()));
        return sb.toString();
    }
}

public class StudentInformationSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            if (logIn()) {
                int choice = -1; 
                do {
                    System.out.println("\nStudent Information System");
                    System.out.println("1. Add Student");
                    System.out.println("2. Update Student");
                    System.out.println("3. Retrieve Students");
                    System.out.println("4. Log Out");
                    System.out.println("5. Exit");
                    System.out.print("Enter your choice: ");
                    
                    String input = scanner.nextLine(); 
                    if (input.isEmpty()) {
                        System.out.println("Error: Choice cannot be empty. Please enter a valid choice.");
                        continue; 
                    }
    
                    try {
                        choice = Integer.parseInt(input); 
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid number.");
                        continue; 
                    }
    
                    switch (choice) {
                        case 1:
                            addStudent();
                            break;
                        case 2:
                            updateStudent();
                            break;
                        case 3:
                            retrieveStudents();
                            break;
                        case 4:
                            System.out.println("Logging out...");
                            break;
                        case 5:
                            System.out.println("Exiting the system.");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (choice != 4);
            } else {
                System.out.println("Invalid login. Please try again.");
            }
        }
    }

    private static boolean logIn() {
        String username = "admin";
        String password = "Teacher1";
        System.out.print("Enter username: ");

        String inputUsername = scanner.nextLine();
        if (inputUsername.isEmpty()) {
            System.out.println("Error: Username cannot be empty.");
            return false;
        }

        if (!username.equals(inputUsername)) {
            System.out.println("Error: Incorrect username.");
            return false;
        }

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();
        if (inputPassword.isEmpty()) {
            System.out.println("Error: Password cannot be empty.");
            return false;
        }

        if (!password.equals(inputPassword)) {
            System.out.println("Error: Incorrect password.");
            return false;
        }
        System.out.println("Log in Successful");
        return true;
    }

    private static void addStudent() {
        while (true) { 
            System.out.print("Enter Student Lastname: ");
            String name = scanner.nextLine();
            
            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty.");
                continue; 
            }
    
            if (!name.matches("[a-zA-Z ]+") || name.length() < 2) {
                System.out.println("Error: Name must contain at least two letters and can only contain letters and spaces.");
                continue; 
            }
    
            boolean nameExists = false;
            for (Student student : students) {
                if (student.name.equalsIgnoreCase(name)) {
                    nameExists = true;
                    break;
                }
            }
            if (nameExists) {
                System.out.println("Error: Student name already exists. Please enter a different name.");
                continue;
            }
    
            String id;
            while (true) {
                System.out.print("Enter Student ID: ");
                id = scanner.nextLine();
                if (id.isEmpty()) {
                    System.out.println("Error: ID cannot be empty. Please enter a valid ID.");
                    continue;
                }
    
               
                if (!id.matches("[0-9-]+")) {
                    System.out.println("Error: ID must contain only numbers and dashes. Please enter a valid ID.");
                    continue;
                }
    
                boolean idExists = false;
                for (Student student : students) {
                    if (student.id.equals(id)) {
                        idExists = true;
                        break;
                    }
                }
    
                if (idExists) {
                    System.out.println("ID already taken. Please enter a different ID.");
                } else {
                    break;
                }
            }
            
            String course;
            while (true) {
                System.out.print("Enter Student Course: ");
                course = scanner.nextLine();
                if (course.isEmpty()) {
                    System.out.println("Error: Course cannot be empty.");
                    continue;
                }
    
               
                if (!course.equalsIgnoreCase("BSIT") && 
                    !course.equalsIgnoreCase("IT") && 
                    !course.equalsIgnoreCase("Bachelor of Science in Information Technology")) {
                    System.out.println("Error: Invalid course. Please enter either 'BSIT', 'IT', or 'Bachelor of Science in Information Technology'");
                    continue; 
                }
                break; 
            }
    
            double[] grades = new double[3]; 
            String[] subjects = {"HCI", "Programming 2", "IT Era"};
            for (int i = 0; i < 3; i++) {
                while (true) {
                    System.out.print("Enter grade for subject " + subjects[i] + ": ");
                    try {
                        String input = scanner.nextLine();
                        if (input.isEmpty()) {
                            System.out.println("Error: Grade cannot be empty. Please enter a valid grade.");
                            continue; 
                        }
                        grades[i] = Double.parseDouble(input);
                        if (grades[i] < 0 || grades[i] > 100) {
                            System.out.println("Error: Grade must be between 0 and 100.");
                        } else {
                            break; 
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid number.");
                    }
                }
            }
    
            students.add(new Student(name, id, course, grades));
            System.out.println("Student added successfully.");
            break; 
        }
    }

    private static void updateStudent() {
        if (students.isEmpty()) {
            System.out.println("No students available to update.");
            return;
        }
    
        String idToUpdate;
        boolean found;
    
        do {
            retrieveStudents();
            System.out.print("Enter the ID of the student to update: ");
            idToUpdate = scanner.nextLine();
            found = false;
    
            for (Student student : students) {
                if (student.id.equals(idToUpdate)) {
                    found = true;
    
                   
                    String newName;
                    while (true) {
                        System.out.print("Enter new name (current: " + student.name + "): ");
                        newName = scanner.nextLine();
                        if (newName.isEmpty()) {
                            System.out.println("Error: Name cannot be empty.");
                            continue;
                        }
                        if (!newName.matches("[a-zA-Z ]+") || newName.length() < 2) {
                            System.out.println("Error: Name must contain at least two or more letters and can only contain letters and spaces.");
                            continue; 
                        }
                        break; 
                    }
    
                    
                    String newCourse;
                    while (true) {
                        System.out.print("Enter new course (current: " + student.course + "): ");
                        newCourse = scanner.nextLine();
                        if (newCourse.isEmpty()) {
                            System.out.println("Error: Course cannot be empty.");
                            continue; 
                        }
                        
                        if (!newCourse.equalsIgnoreCase("BSIT") &&
                            !newCourse.equalsIgnoreCase("IT") &&
                            !newCourse.equalsIgnoreCase("Bachelor of Science in Information Technology")) {
                            System.out.println("Error: Invalid course. Please enter either 'BSIT', 'IT', or 'Bachelor of Science in Information Technology'");
                            continue; 
                        }
                        break; 
                    }
    
                    
                    double[] newGrades = new double[3];
                    String[] subjects = {"HCI", "Programming 2", "IT Era"};
                    for (int i = 0; i < 3; i++) {
                        while (true) {
                            System.out.print("Enter new grade for subject " + subjects[i] + " (current: " + student.grades[i] + "): ");
                            try {
                                String input = scanner.nextLine();
                                if (input.isEmpty()) {
                                    System.out.println("Error: Grade cannot be empty. Please enter a valid grade.");
                                    continue; 
                                }
                                newGrades[i] = Double.parseDouble(input);
                                if (newGrades[i] < 0 || newGrades[i] > 100) {
                                    System.out.println("Error: Grade must be between 0 and 100.");
                                    continue; 
                                }
                                break; 
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Please enter a valid number.");
                            }
                        }
                    }
    
                    
                    System.out.println("You are about to update the student details to:");
                    System.out.println("Name: " + newName);
                    System.out.println("Course: " + newCourse);
                    System.out.printf("Grades: %.2f, %.2f, %.2f%n", newGrades[0], newGrades[1], newGrades[2]);
    
                    String confirmation;
                    while (true) {
                        System.out.print("Do you want to proceed with the update? (yes/no): ");
                        confirmation = scanner.nextLine();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            student.name = newName;
                            student.course = newCourse;
                            student.grades = newGrades;
                            System.out.println("Student updated successfully.");
                            break; 
                        } else if (confirmation.equalsIgnoreCase("no")) {
                            System.out.println("Update canceled.");
                            break; 
                        } else {
                            System.out.println("Error: Please enter 'yes' or 'no'.");
                        }
                    }
                    break; 
                }
            }
    
            if (!found) {
                System.out.println("Student with ID " + idToUpdate + " not found. Please try again.");
            }
        } while (!found);
    }
    private static void retrieveStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            
            Collections.sort(students, Comparator.comparing(student -> student.name));
            
            System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s %-10s%n", "ID", "Name", "Course", "HCI", "Prog. 2", "IT Era", "GWA");
            System.out.println("--------------------------------------------------------------------------------------------");
            
            for (Student student : students) {
                System.out.printf("%-10s %-20s %-15s %-10.2f %-10.2f %-10.2f %-10.2f%n", 
                    student.id, 
                    student.name, 
                    student.course, 
                    student.grades[0],  
                    student.grades[1],  
                    student.grades[2],  
                    student.calculateGWA()); 
            }
        }
    }
}