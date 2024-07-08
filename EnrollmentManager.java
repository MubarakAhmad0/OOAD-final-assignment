import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentManager {
    private List<Student> students;
    private List<Course> courses;
    private List<Enrollment> enrollments;
    private List<Subject> subjects;
    private Map<Integer, Boolean> studentAccommodationMap;
    private BillingManager billingManager;

    public EnrollmentManager(DiscountStrategy discountStrategy) {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.studentAccommodationMap = new HashMap<>();
        this.billingManager = new BillingManager(discountStrategy);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addStudent(Student student) {
        students.add(student);
        billingManager.addStudent(student);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void setStudentAccommodationStatus(int studentId, boolean hasAccommodation) {
        studentAccommodationMap.put(studentId, hasAccommodation);
    }

    public boolean hasStudentOptedForAccommodation(int studentId) {
        return studentAccommodationMap.getOrDefault(studentId, false);
    }

    public Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    public Course findCourseById(int courseId) {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public void addCourseToStudent(Student student, Course course) {
        Enrollment enrollment = findEnrollmentByStudent(student);
        if (enrollment == null) {
            enrollment = new Enrollment(student);
            enrollments.add(enrollment);
            billingManager.addEnrollment(enrollment);
        }
        enrollment.addCourse(course);
    }

    private Enrollment findEnrollmentByStudent(Student student) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                return enrollment;
            }
        }
        return null;
    }

    public String generateBill(int studentId) {
        return billingManager.generateBill(studentId, hasStudentOptedForAccommodation(studentId));
    }
}
