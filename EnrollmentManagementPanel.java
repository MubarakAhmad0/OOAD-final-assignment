import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EnrollmentManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField studentIdField, courseIdField;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private EnrollmentManager enrollmentManager;

    public EnrollmentManagementPanel(CardLayout cardLayout, JPanel cardPanel, EnrollmentManager enrollmentManager) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.enrollmentManager = enrollmentManager;
        setLayout(new BorderLayout());
        add(createForm(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField(10);
        formPanel.add(studentIdField);

        formPanel.add(new JLabel("Course ID:"));
        courseIdField = new JTextField(10);
        formPanel.add(courseIdField);

        JButton addButton = new JButton("Add Enrollment");
        addButton.addActionListener(this::addEnrollment);
        formPanel.add(addButton);

        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(this::uploadFile);
        formPanel.add(uploadButton);

        return formPanel;
    }

    private JScrollPane createTable() {
        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Student ID", "Course ID"});
        table.setModel(model);
        return new JScrollPane(table);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton saveToFileButton = new JButton("Save to File");
        saveToFileButton.addActionListener(this::saveEnrollmentsToFile);
        bottomPanel.add(saveToFileButton, BorderLayout.WEST);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        bottomPanel.add(backButton, BorderLayout.EAST);

        return bottomPanel;
    }

    private void addEnrollment(ActionEvent e) {
        int studentId = Integer.parseInt(studentIdField.getText());
        int courseId = Integer.parseInt(courseIdField.getText());
        Student student = enrollmentManager.findStudentById(studentId);
        Course course = enrollmentManager.findCourseById(courseId);
        if (student != null && course != null) {
            enrollmentManager.addCourseToStudent(student, course);
            model.addRow(new Object[]{studentId, courseId});
        } else {
            JOptionPane.showMessageDialog(this, "Student ID or Course ID not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        studentIdField.setText("");
        courseIdField.setText("");
    }

    private void uploadFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 2) {
                        int studentId = Integer.parseInt(data[0].trim());
                        int courseId = Integer.parseInt(data[1].trim());
                        Student student = enrollmentManager.findStudentById(studentId);
                        Course course = enrollmentManager.findCourseById(courseId);
                        if (student != null && course != null) {
                            enrollmentManager.addCourseToStudent(student, course);
                            model.addRow(new Object[]{studentId, courseId});
                        } else {
                            JOptionPane.showMessageDialog(this, "Student ID " + studentId + " or Course ID " + courseId + " not found", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        System.out.println("Invalid line: " + line);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid data format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveEnrollmentsToFile(ActionEvent e) {
        try (FileWriter writer = new FileWriter("enrollments.csv")) {
            writer.append("Student ID,Course ID\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.append(model.getValueAt(i, 0).toString()).append(",")
                      .append(model.getValueAt(i, 1).toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, "Enrollments saved to enrollments.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving enrollments to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
