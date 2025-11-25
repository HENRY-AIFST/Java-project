import java.util.*;

abstract class Person {
    String name;
    String email;

    Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    abstract void displayInfo();
}

class Student extends Person {
    int rollNo;
    String course;
    double marks;
    char grade;

    Student(int rollNo, String name, String email, String course, double marks) {
        super(name, email);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 50) grade = 'C';
        else grade = 'D';
    }

    @Override
    void displayInfo() {
        System.out.println("Student Info:");
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
    }

    void displayInfo(String researchArea) {
        System.out.println("Student Info:");
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
        System.out.println("Research Area: " + researchArea);
        System.out.println("[Note] Overloaded display method:");
    }
}

interface RecordActions {
    void addStudent(Student student);
    void deleteStudent(int rollNo);
    void updateStudent(int rollNo, Student updatedStudent);
    void searchStudent(int rollNo);
    void viewAllStudents();
}

class StudentManager implements RecordActions {
    private final Map<Integer, Student> studentMap = new HashMap<>();

    public void addStudent(Student student) {
        if (studentMap.containsKey(student.rollNo)) {
            System.out.println("Duplicate roll number not allowed.");
        } else {
            studentMap.put(student.rollNo, student);
            System.out.println("Student added successfully.\n");
        }
    }

    public void deleteStudent(int rollNo) {
        if (studentMap.remove(rollNo) != null) {
            System.out.println("Student deleted successfully.\n");
        } else {
            System.out.println("Student not found.\n");
        }
    }

    public void updateStudent(int rollNo, Student updatedStudent) {
        if (studentMap.containsKey(rollNo)) {
            studentMap.put(rollNo, updatedStudent);
            System.out.println("Student record updated.\n");
        } else {
            System.out.println("Student not found.\n");
        }
    }

    public void searchStudent(int rollNo) {
        Student s = studentMap.get(rollNo);
        if (s != null) s.displayInfo();
        else System.out.println("Student not found.\n");
    }

    public void viewAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No student records available.\n");
        } else {
            for (Student s : studentMap.values()) {
                s.displayInfo();
                System.out.println();
            }
        }
    }
}

public class StudentManagementSystem {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Student s1 = new Student(101, "Ankit", "ankit@mail.com", "B.Tech", 88.5);
        Student s2 = new Student(102, "Ayush", "ayush@mail.com", "M.Tech", 92.0);
        manager.addStudent(s1);
        manager.addStudent(s2);
        manager.viewAllStudents();
        s2.displayInfo("AI");
        Person p = s1;
        p.displayInfo();
        manager.searchStudent(101);
        manager.deleteStudent(101);
        manager.viewAllStudents();
    }
}
