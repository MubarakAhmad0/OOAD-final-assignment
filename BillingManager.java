import java.util.ArrayList;
import java.util.List;

public class BillingManager {
    private DiscountStrategy discountStrategy;
    private List<Enrollment> enrollments;
    private List<Fee> fees;
    private List<Student> students;

    public BillingManager(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
        this.enrollments = new ArrayList<>();
        this.fees = new ArrayList<>();
        this.students = new ArrayList<>();

        // Adding mandatory fees
        fees.add(new Fee("IT/Network Fee", 200, true));
        fees.add(new Fee("Library Fee", 150, true));
        fees.add(new Fee("Club and Society Fee", 100, true));

        // Adding optional accommodation fee
        fees.add(new Fee("Accommodation Fee", 1000, false)); // Set accommodation fee to $1000
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public void addOptionalFee(Fee fee) {
        if (!fee.isMandatory()) {
            fees.add(fee);
        }
    }

    public String generateBill(int studentId, boolean hasAccommodation) {
        Student student = findStudentById(studentId);
        if (student == null) {
            return "No enrollment found for the student ID.";
        }
        Enrollment enrollment = findEnrollmentByStudent(student);
        if (enrollment == null) {
            return "No enrollments found for the student.";
        }

        List<Course> courses = enrollment.getCourses();
        double totalCourseFee = 0;

        for (Course course : courses) {
            totalCourseFee += course.getBaseFee();
        }

        double discount = discountStrategy.calculateDiscount(courses);

        double mandatoryFees = 0;
        double optionalFees = 0;
        for (Fee fee : fees) {
            if (fee.isMandatory()) {
                mandatoryFees += fee.getAmount();
            } else if (fee.getName().equals("Accommodation Fee") && hasAccommodation) {
                optionalFees += fee.getAmount();
            }
        }

        double totalFee = totalCourseFee + mandatoryFees;
        double netPayable = totalFee - discount + optionalFees;

        return String.format("Total Course Fees: $%.2f\nMandatory Fees: $%.2f\nOptional Fees: $%.2f\nDiscount: $%.2f\nNet Payable: $%.2f",
                totalCourseFee, mandatoryFees, optionalFees, discount, netPayable);
    }

    private Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    private Enrollment findEnrollmentByStudent(Student student) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == student.getId()) {
                return enrollment;
            }
        }
        return null;
    }
}



