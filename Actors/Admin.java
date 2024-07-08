package Actors;

import Logic.Enrollment;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private final List<Student> students;
    private final List<Subject> subjects;
    private final List<Course> courses;
    private final List<Enrollment> enrollments;

    public Admin() {
        students = new ArrayList<>();
        subjects = new ArrayList<>();
        courses = new ArrayList<>();
        enrollments = new ArrayList<>();
    }

    
    public List<Student> getStudents() {
        return students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
}
