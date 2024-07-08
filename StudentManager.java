import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students;
    private static final String STUDENT_FILE = "students.dat";

    public StudentManager() {
        students = FileUtility.readFromFile(STUDENT_FILE);
    }

    public void addStudent(Student student) {
        students.add(student);
        saveData();
    }

    public void saveData() {
        FileUtility.saveToFile(students, STUDENT_FILE);
    }
}

