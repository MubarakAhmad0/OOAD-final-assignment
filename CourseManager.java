import java.util.ArrayList;

public class CourseManager {
    private ArrayList<Course> courses;
    private static final String COURSE_FILE = "courses.dat";

    public CourseManager() {
        courses = FileUtility.readFromFile(COURSE_FILE);
    }

    public void addcourse(Course course) {
        courses.add(course);
        saveData();
    }

    public Course getCourseById(int courseId) {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return course;
            }
        }

        return null;
    }

    public void saveData() {
        FileUtility.saveToFile(courses, COURSE_FILE);
    }

}