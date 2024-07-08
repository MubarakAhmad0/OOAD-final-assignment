package Components;
import Logic.BillingManager;
import Logic.EnrollmentManager;
import Logic.StackedDiscount;

import javax.swing.*;
import java.awt.*;

public class MainApplicationWindow extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final EnrollmentManager enrollmentManager;

    public MainApplicationWindow() {
        enrollmentManager = new EnrollmentManager(new StackedDiscount());
        BillingManager billingManager = new BillingManager(new StackedDiscount());
        setTitle("University Logic.Enrollment System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = new JPanel(new GridLayout(1, 5));
        JButton manageStudentsBtn = new JButton("Manage Students");
        JButton manageCoursesBtn = new JButton("Manage Courses");
        JButton manageSubjectsBtn = new JButton("Manage Subjects");
        JButton manageEnrollmentsBtn = new JButton("Manage Enrollments");
        JButton billingBtn = new JButton("Billing");

        manageStudentsBtn.addActionListener(e -> cardLayout.show(cardPanel, "Students"));
        manageCoursesBtn.addActionListener(e -> cardLayout.show(cardPanel, "Courses"));
        manageSubjectsBtn.addActionListener(e -> cardLayout.show(cardPanel, "Subjects"));
        manageEnrollmentsBtn.addActionListener(e -> cardLayout.show(cardPanel, "Enrollments"));
        billingBtn.addActionListener(e -> cardLayout.show(cardPanel, "Billing"));

        dashboardPanel.add(manageStudentsBtn);
        dashboardPanel.add(manageCoursesBtn);
        dashboardPanel.add(manageSubjectsBtn);
        dashboardPanel.add(manageEnrollmentsBtn);
        dashboardPanel.add(billingBtn);

        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(new StudentManagementPanel(cardLayout, cardPanel, enrollmentManager), "Students");
        cardPanel.add(new CourseManagementPanel(cardLayout, cardPanel, enrollmentManager), "Courses");
        cardPanel.add(new SubjectManagementPanel(cardLayout, cardPanel, enrollmentManager), "Subjects");
        cardPanel.add(new EnrollmentManagementPanel(cardLayout, cardPanel, enrollmentManager), "Enrollments");
        cardPanel.add(new BillingPanel(cardLayout, cardPanel, enrollmentManager), "Billing");

        add(cardPanel);
        cardLayout.show(cardPanel, "Dashboard");
    }


}
