package Logic;

import Actors.Course;
import Actors.Student;

import java.util.ArrayList;
import java.util.List;

public class Enrollment {
    private final Student student;
    private final List<Course> courses;

    public Enrollment(Student student) {
        this.student = student;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Student getStudent() {
        return student;
    }
}
