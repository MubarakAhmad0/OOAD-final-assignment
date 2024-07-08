import java.util.ArrayList;

public class SubjectManager {
    private ArrayList<Subject> subjects;
    private static final String SUBJECT_FILE = "subjects.dat";

    public SubjectManager() {
        subjects = FileUtility.readFromFile(SUBJECT_FILE);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        saveData();
    }

    public void saveData() {
        FileUtility.saveToFile(subjects, SUBJECT_FILE);
    }
}

