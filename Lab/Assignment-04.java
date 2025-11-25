import java.io.*;
import java.util.*;

// ========================== Student Class ==============================
class Student {
    int rollNo;
    String name;
    String email;
    String course;
    double marks;

    public Student(int rollNo, String name, String email, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNo +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nCourse: " + course +
                "\nMarks: " + marks + "\n";
    }
}

// ========================== File Utility ==============================
class FileUtil {

    // Reads students from file
    public static ArrayList<Student> readStudents(String fileName) {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
                return list;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int roll = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];
                String course = data[3];
                double marks = Double.parseDouble(data[4]);
                list.add(new Student(roll, name, email, course, marks));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return list;
    }

    // Saves students into file
    public static void saveStudents(String fileName, ArrayList<Student> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            for (Student s : list) {
                bw.write(s.rollNo + "," + s.name + "," + s.email + "," + s.course + "," + s.marks);
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Demonstrates RandomAccessFile
    public static void randomAccessRead(String fileName) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            System.out.println("\nReading first 50 bytes using RandomAccessFile:");
            for (int i = 0; i < 50 && raf.getFilePointer() < raf.length(); i++) {
                System.out.print((char) raf.read());
            }
            System.out.println("\n");
            raf.close();
        } catch (Exception e) {
            System.out.println("RandomAccessFile Error: " + e.getMessage());
        }
    }
}
public class StudentRecordSystem {

    private static final String FILE_NAME = "students.txt";
    private ArrayList<Student> students;

    public StudentRecordSystem() {
        students = FileUtil.readStudents(FILE_NAME);

        if (students.size() > 0) {
            System.out.println("Loaded students from file:\n");
            for (Student s : students) System.out.println(s);
        } else {
            System.out.println("No existing records found. Starting fresh.\n");
        }

        File file = new File(FILE_NAME);
        System.out.println("File Name: " + file.getName());
        System.out.println("File Size: " + file.length() + " bytes\n");

        FileUtil.randomAccessRead(FILE_NAME);
    }


    void addStudent(Scanner sc) {
        try {
            System.out.print("Enter Roll No: ");
            int roll = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            System.out.print("Enter Marks: ");
            double marks = Double.parseDouble(sc.nextLine());

            students.add(new Student(roll, name, email, course, marks));
            System.out.println("Student Added.\n");

        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No records found.\n");
            return;
        }

        Iterator<Student> it = students.iterator();
        while (it.hasNext()) System.out.println(it.next());
    }

    void searchByName(Scanner sc) {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine();

        boolean found = false;
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.println("\n" + s);
                found = true;
            }
        }
        if (!found) System.out.println("No student found with name: " + name);
    }

    void deleteByName(Scanner sc) {
        System.out.print("Enter name to delete: ");
        String name = sc.nextLine();

        boolean removed = students.removeIf(s -> s.name.equalsIgnoreCase(name));

        if (removed) System.out.println("Student Deleted.\n");
        else System.out.println("No matching student found.\n");
    }

    void sortByMarks() {
        students.sort((a, b) -> Double.compare(b.marks, a.marks));
        System.out.println("Sorted by Marks (High → Low):\n");
        viewStudents();
    }

    // Main Menu
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice!");
                continue;
            }

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> searchByName(sc);
                case 4 -> deleteByName(sc);
                case 5 -> sortByMarks();
                case 6 -> {
                    FileUtil.saveStudents(FILE_NAME, students);
                    System.out.println("Records Saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // MAIN
    public static void main(String[] args) {
        StudentRecordSystem sms = new StudentRecordSystem();
        sms.start();
    }
}
